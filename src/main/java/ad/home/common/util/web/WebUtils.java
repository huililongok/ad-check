package ad.home.common.util.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class WebUtils {
    public static final String SYS_CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 输入内容
     * @param response
     * @param content
     * @param contentType
     * @throws IOException
     */
    public static void writeData(HttpServletResponse response, String content, String contentType) throws IOException {
        response.setContentType(contentType);
        PrintWriter out = response.getWriter();
        out.write(content);
        out.flush();
        out.close();
    }

    /**
     * 解析请求内容
     * @param request
     * @param isZip 是否压缩
     * @return
     * @throws IOException
     */
    public static byte[] analysisStream(HttpServletRequest request, boolean isZip) throws IOException {
        // 获取请求体中的内容
        byte[] content = new byte[0];
        int contentLength = request.getContentLength() == -1 ? 0 : request.getContentLength();
        if (contentLength > 0) {
            content = getData(request.getInputStream(), request.getContentLength());
            if (isZip) {
                GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(content));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bbuf = new byte[256];
                while (true) {
                    int r = gzipInputStream.read(bbuf);
                    if (r < 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bbuf, 0, r);
                }
                content = byteArrayOutputStream.toByteArray();
            }
        }
        return content;
    }

    /**
     * 获取请求的字符串参数
     * @param request
     * @return
     */
    public static String analysisRequestParameters(HttpServletRequest request) {
        StringBuilder sbf = new StringBuilder();
        sbf.append("{");
        sbf.append("'queryString':'").append(request.getQueryString() == null ? "" : request.getQueryString()).append("'");

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null && parameterMap.size() > 0) {
            sbf.append(",");
            int i = 0;
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                sbf.append("'").append(key).append("':").append("'").append(values != null && values.length > 0 ? StringUtils.join(values, ",") : "").append("'");
                if (i != parameterMap.size() - 1) {
                    sbf.append(",");
                }
                i++;
            }
        }
        sbf.append("}");
        return sbf.toString();
    }

    public static byte[] getData(InputStream sm, int length) throws IOException {
        ByteArrayOutputStream baos;
        if (length != 0) {
            baos = new ByteArrayOutputStream(length);
        } else {
            baos = new ByteArrayOutputStream();
        }
        try {
            byte[] tempCatch = new byte[1024];
            int readCount = 0;
            while ((readCount = sm.read(tempCatch, 0, tempCatch.length)) != -1) {
                baos.write(tempCatch, 0, readCount);
            }
        } catch (Exception e) {
            return null;
        }
        return baos.toByteArray();
    }

    public static final String getRequestUri(HttpServletRequest request){
        String requestUri = request.getHeader("X-Forwarded-Uri");
        if ( requestUri != null )
        {
            requestUri = requestUri.replace(request.getContextPath(), "");
        }
        else
        {
            requestUri = request.getRequestURI();
            requestUri = requestUri.replace(request.getContextPath(), "");
        }
        return requestUri;
    }

    /**
     * 获取Spring 的数据业务逻辑对象
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getWebApplicationBean(String beanName) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        T bean = (T) context.getBean(beanName);
        return bean;
    }



    /**
     * 获取远程IP地址
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 正则验证URL是否正确有效
     * @param url
     * @return
     */
    public static boolean urlCheck(String url) {
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }

    /**
     * 根据URL获取网络信息
     * @param url
     * @return
     */
    public static Map<String, String> getNetInfo(String url) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            URL u = new URL(url);
            String domain = u.getHost();//获取域名
            int port = u.getPort();//端口
            int defaultPort = u.getDefaultPort();//默认端口
            String protocol = u.getProtocol();//协议
            String ip = getIp(domain);
            System.out.println(String.format("URL：%s | IP：%s | port：%s", url, ip, port == -1 ? (defaultPort + "") : (port + "")));
            map.put("domain", domain);
            map.put("port", port == -1 ? (defaultPort + "") : (port + ""));
            map.put("protocol", protocol);
            map.put("ip", ip);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    /**
     * 根据域名获取IP
     * @param domain
     * @return
     * @throws UnknownHostException
     */
    public static String getIp(String domain) throws UnknownHostException {
        String ip = null;
        if (StringUtils.isNotBlank(domain)) {
            InetAddress ia2 = InetAddress.getByName(domain);
            //System.out.println(ia2.toString());
            //System.out.println(ia2.getHostName());//域名               127
            ip = ia2.getHostAddress();//ip地址
        }
        return ip;
    }

    /**
     * 获取系统路径
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/");
    }

    /**
     * 获取系统Request
     * @return
     */
    public static HttpServletRequest getServletRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

}
