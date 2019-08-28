package ad.home.config;

import ad.home.config.settings.MySQLParseSettings;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan("ad.home.dao.**")
public class MybatisPlusConfiguration {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setSqlParser(new MySQLParseSettings());
        return paginationInterceptor;
    }

    @Bean
    @ConditionalOnProperty(
            value = {"ad.showsql"},
            matchIfMissing = false
    ) //线上排查问题时可以重启通过设置这个参数--ad.showsql=true来打印实时sql数据
    public PerformanceInterceptor performanceInterceptor(){
        log.info("系统检测到ad.showsql值为true,即将开启性能检测、展示sql功能......");
        //启用性能分析插件
        return new PerformanceInterceptor();
    }

}
