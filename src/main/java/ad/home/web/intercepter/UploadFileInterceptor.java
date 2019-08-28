package ad.home.web.intercepter;

import ad.home.web.request.XssMultipartHttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * 拦截处理上传文件
 * @author lilon
 * 2016-05-17
 */
public class UploadFileInterceptor extends HandlerInterceptorAdapter {
    // private static Set<String> PLUGIN_URL = new HashSet<String>();
    private static Set<String> PLUGIN_FILETYPE = new HashSet<String>();
    /*
     * 允许上传的后缀
     */
    private static Set<String> ALLOW_FILE_SUFFIX = new HashSet<String>();
    static {
        PLUGIN_FILETYPE.add("image/png");
        PLUGIN_FILETYPE.add("video/webm");
        // xls,xlsx,docx,doc,jpg,gif,png,ico,bmp,jpeg,webm,txt,pdf
        ALLOW_FILE_SUFFIX.add("xls");
        ALLOW_FILE_SUFFIX.add("xlsx");
        ALLOW_FILE_SUFFIX.add("docx");
        ALLOW_FILE_SUFFIX.add("doc");
        ALLOW_FILE_SUFFIX.add("jpg");
        ALLOW_FILE_SUFFIX.add("png");
        ALLOW_FILE_SUFFIX.add("webp");
        ALLOW_FILE_SUFFIX.add("jpeg");
        ALLOW_FILE_SUFFIX.add("webm");
        ALLOW_FILE_SUFFIX.add("txt");
        ALLOW_FILE_SUFFIX.add("pdf");
        ALLOW_FILE_SUFFIX.add("csv");
        ALLOW_FILE_SUFFIX.add("txt");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MultipartResolver res = new StandardServletMultipartResolver();
        boolean retb = true;
        if(res.isMultipart(request)){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) XssMultipartHttpServletRequestWrapper.getOrgRequest(request);
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            Iterator<String> iterator = files.keySet().iterator();
            String currUrl = request.getServletPath();
            while (iterator.hasNext()) {
                String formKey = (String) iterator.next();
                List<MultipartFile> files1 = multipartRequest.getFiles(formKey);
                if (files1 != null && files1.size() > 0) {
                    for (int i=0; i<files1.size(); i++) {
                        MultipartFile multipartFile = files1.get(i);
                        if (multipartFile != null && multipartFile.getSize() != 0 && StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                            if (currUrl.toLowerCase().indexOf("/plugin") >= 0) {
                                // 针对插件部分
                                String contentType = multipartFile.getContentType();
                                if (!fileContentType(contentType)) {
                                    retb = false;
                                    break;
                                }
                            } else {
                                String filename = multipartFile.getOriginalFilename();
                                if (!checkFileSuffix(filename)) {
                                    retb = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!retb) {
                    break;
                }
            }
        }
        if (!retb) {
            toPage(request, response);
        }
        return retb;
    }

    /**
     * 验证失败跳转页面
     * @param request
     * @param response
     */
    private void toPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String basePath = request.getContextPath();
        //String rootp = request.getServletPath();
        String errorToPage = request.getParameter("errorToPage");
        String asyncUpload = request.getParameter("asyncUpload");
        if (StringUtils.isNotBlank(errorToPage) && (StringUtils.isBlank(asyncUpload) || !asyncUpload.equals("1"))) {
            String errorInfo = URLEncoder.encode("上传文件格式错误！", "utf-8");
            if (errorToPage.indexOf("?") > 0) {
                errorToPage = errorToPage + "&";
            } else {
                errorToPage = errorToPage + "?";
            }
            response.sendRedirect(basePath + "/" + errorToPage + "errorInfo=" + errorInfo);
        } else if (StringUtils.isNotBlank(asyncUpload) && asyncUpload.equals("1")) {
            // 异步上传
            response.getWriter().write("505");
            response.getWriter().flush();
        } else {
            String str = "<html><head><meta charset=\"UTF-8\">\r\n<script>alert('上传文件格式错误！');history.go(-1);reload();</script></head></html>";
            response.getWriter().write(str);
            response.getWriter().flush();
        }
    }

    @SuppressWarnings("unused")
    private  boolean checkFile(String fileName) {
        boolean flag = false;
        String suffixList="xls,xlsx,docx,doc,jpg,gif,png,ico,bmp,jpeg,webm,txt,pdf";
        //获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        if (suffixList.contains(suffix.trim().toLowerCase())) {
            flag = true;
        }
        return flag;
    }

    private  boolean checkFileSuffix(String fileName) {
        boolean flag = false;
        //String suffixList="xls,xlsx,docx,doc,jpg,gif,png,ico,bmp,jpeg,webm,txt,pdf";
        //获取文件后缀
        if (StringUtils.isBlank(fileName) || fileName.indexOf(".") <= 0) {
            return flag;
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (ALLOW_FILE_SUFFIX.contains(suffix.trim().toLowerCase())) {
            flag = true;
        }
        return flag;
    }

    private boolean fileContentType(String contentType) {
        return PLUGIN_FILETYPE.contains(contentType.toLowerCase());
    }


}
