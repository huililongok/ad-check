package ad.home.config;

import ad.home.web.intercepter.TokenInterceptor;
import ad.home.web.intercepter.UploadFileInterceptor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统MVC配置
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径，排除拦截路径，优先级
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UploadFileInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 保留空的字段
                SerializerFeature.WriteNullStringAsEmpty, // String null -> ""
                SerializerFeature.WriteNullNumberAsZero, // Number null -> 0
                SerializerFeature.WriteEnumUsingName
                // SerializerFeature.DisableCircularReferenceDetect // 禁止循环引用$ref，原样输出对象
        );
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> list = new ArrayList<>(1);
        list.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(list);
        converters.add(converter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry.addResourceHandler("/**").addResourceLocations("file:" + uploadPath)
                .addResourceLocations("classpath:/META-INF/resources/").addResourceLocations("classpath:/static/");*/
    }

   /* private void responseResult(HttpServletResponse response, JsonResult.Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }*/

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }


}
