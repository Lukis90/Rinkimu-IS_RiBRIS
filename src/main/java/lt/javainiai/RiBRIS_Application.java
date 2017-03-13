package lt.javainiai;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lt.javainiai.service.ConstituencyService;
import lt.javainiai.service.PartyService;

@SpringBootApplication
public class RiBRIS_Application {

    @Resource
    PartyService partyService;
    @Resource
    ConstituencyService constituencyService;

    public static void main(String[] args) {
        SpringApplication.run(RiBRIS_Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
