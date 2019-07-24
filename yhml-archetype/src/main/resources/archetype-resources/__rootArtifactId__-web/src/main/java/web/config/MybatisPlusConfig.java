#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.web.config;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@MapperScan("${package}.core.mapper")
@AutoConfigureAfter(DataSourceConfig.class)
public class MybatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
