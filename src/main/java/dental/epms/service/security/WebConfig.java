package dental.epms.service.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public FilterRegistrationBean corsFilter2(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.ACCEPT,
                HttpHeaders.CONTENT_TYPE
        ));
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("https://185.177.59.64");
        configuration.addAllowedOrigin("http://185.177.59.64");
        configuration.addAllowedOrigin("https://185.177.59.64:5173");
        configuration.addAllowedOrigin("http://185.177.59.64:5173");
        configuration.addAllowedOrigin("https://www.3dclinic.uz");
        configuration.addAllowedOrigin("http://www.3dclinic.uz");
        configuration.addAllowedOrigin("https://3dclinic.uz");
        configuration.addAllowedOrigin("http://3dclinic.uz");
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        configuration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", configuration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }
}
