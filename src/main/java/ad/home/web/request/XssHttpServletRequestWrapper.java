package ad.home.web.request;

import ad.home.common.util.web.XssUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(XssUtil.xssEncode(name));
        if(null == value || "".equals(value)) {
            value = orgRequest.getParameter(XssUtil.xssEncode(name));
        }
        if (value != null) {
            value = XssUtil.xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if(null == values) {
            values = orgRequest.getParameterValues(parameter);
        }
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = XssUtil.xssEncode(values[i]);
        }
        return encodedValues;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {

        String value = super.getHeader(XssUtil.xssEncode(name));
        if(null == value || "".equals(value)) {
            value = orgRequest.getHeader(XssUtil.xssEncode(name));
        }
        if (value != null) {
            value = XssUtil.xssEncode(value);
        }
        return value;
    }

    /*
    public ServletInputStream getInputStream() throws IOException {
    	ServletInputStream strm = super.getInputStream();

    	String str = "";
    	if (strm != null) {
    		BufferedReader bf = new BufferedReader(new InputStreamReader(strm));

    		StringBuffer buffer = new StringBuffer();
    	    String line = "";
    	    while ((line = bf.readLine()) != null){
    	      buffer.append(line);
    	    }
    	    str = buffer.toString();
    	}
    	System.out.println(str);
    	return strm;
    }
	*/
    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }

        return req;
    }

    public static void main(String[] args) {
        try {
            String s= "on'>\';</script>>\"><script>alert(1)</script>'";
            System.out.println(s);
            String x = XssUtil.xssEncode(s);
            System.out.println(x);
            System.out.println("11111111");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
