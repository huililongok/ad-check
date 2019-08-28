package ad.home.config;

import ad.home.service.SecurityUserService;
import ad.home.web.filter.LoginValidateCodeFilter;
import ad.home.web.handler.*;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserService securityUserService;

    // 登录成功后返回AJAX方式内容
    @Autowired
    AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Autowired
    AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    // 权限错误处理
    @Autowired
    AjaxAccessDeniedHandler ajaxAccessDeniedHandler;

    // 推出登录成功
    AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Autowired
    AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginProcessingUrl = "/user/sign/in";
        String loginPage = "/index.html";
        // 先匹配验证码
        LoginValidateCodeFilter loginValidateCodeFilter = new LoginValidateCodeFilter(loginProcessingUrl);
        loginValidateCodeFilter.setAuthenticationManager(authenticationManager());
        loginValidateCodeFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        loginValidateCodeFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
        // 在UsernamePasswordAuthenticationFilter之前注册loginValidateCodeFilter
        http.addFilterBefore(loginValidateCodeFilter, UsernamePasswordAuthenticationFilter.class);

        // 再配置其他信息
        // 任意角色配置
        // .antMatchers("/user/**").hasAnyRole("consumer")//.hasAnyAuthority("1","2")//拥有任一权限即可访问
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/dataImport/**").hasAnyRole("sysuser","admin") // 数据导入功能需要用户有管理员权限
                .antMatchers("/**").hasAnyRole("clientuser") //.hasAnyAuthority("1","2")//拥有任一权限即可访问 //.anyRequest().authenticated() // 任何请求都拦截
                .and()
                .formLogin().loginPage(loginPage).loginProcessingUrl(loginProcessingUrl)
                .usernameParameter("username").passwordParameter("password")
                .successHandler(ajaxAuthenticationSuccessHandler)
                .failureHandler(ajaxAuthenticationFailureHandler)
                .permitAll()
                .and()
                .logout().logoutUrl("/user/sign/out")
                .logoutSuccessUrl("/user/sign/out-succ")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .csrf()
                .disable();
        // 无权访问回馈
        http.exceptionHandling()
                .authenticationEntryPoint(ajaxAuthenticationEntryPoint) // 未登录则返回失败json串
                .accessDeniedHandler(ajaxAccessDeniedHandler);

        // 如果出现乱码
        //CharacterEncodingFilter filter = new CharacterEncodingFilter();
        //filter.setEncoding("UTF-8");
        //filter.setForceEncoding(true);
        // http.addFilterBefore(filter, CsrfFilter.class);
    }

    /**
     * 不登录忽略的地址
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers(
       "/",
                   "/druid/**",
                   "/index.html",
                   "/main.html",
                   "/favicon.ico",
                   "/js/**",
                   "/lib/**",
                   "/user/sign/**"

           );
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库
        auth.authenticationProvider(authenticationProvider());
        auth.eraseCredentials(false);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setHideUserNotFoundExceptions(false); // 用户名不存在异常暴漏
//        provider.setUserDetailsService(securityUserService);
//        provider.setPasswordEncoder(new MD5PasswordEncoder());
//        return provider;
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false); // 用户名不存在异常暴漏
        provider.setUserDetailsService(securityUserService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    ///解决 无法直接注入 AuthenticationManager
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public static void main(String[] args) {
//        MD5PasswordEncoder b = new MD5PasswordEncoder();
//        String p = "1111@aaaa";
//        String password = b.encode(p);
//        System.out.println("************************");
//        System.out.println(password);
//        System.out.println("************************");

        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        String p = "yanshi";
        String password = b.encode(p);
        System.out.println("************************");
        System.out.println(password);
        System.out.println("************************");
    }

}
