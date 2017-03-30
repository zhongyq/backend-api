package cn.edu.nju.backend.server.web.init;


import cn.edu.nju.backend.server.web.interceptor.AuthInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.Access;

/**
 * Created by zhongyq on 17/2/27.
 */

@Configuration
@ComponentScan(basePackages="cn.edu.nju.backend.server")
@ServletComponentScan(basePackages = "cn.edu.nju.backend.server")
@EnableAutoConfiguration
@EntityScan(basePackages="cn.edu.nju.backend.server.data.model")
@MapperScan(basePackages = "cn.edu.nju.backend.server.data.mapper")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


}
