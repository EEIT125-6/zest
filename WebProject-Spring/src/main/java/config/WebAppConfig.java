package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
/* 掃描的package暫時留空，有需要時請填入 */
@ComponentScan({
	"config",
	"xun"
})
public class WebAppConfig implements WebMvcConfigurer {
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver  resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	// 為了處理靜態檔案必須加入下列敘述：只要是 /css/開頭的任何請求，都轉到/WEB-INF/views/css/去尋找
	// 為了處理靜態檔案必須加入下列敘述：只要是 /js/開頭的任何請求，都轉到/WEB-INF/views/js/去尋找
	// 為了處理靜態檔案必須加入下列敘述：只要是 /image/開頭的任何請求，都轉到/WEB-INF/views/images/去尋找
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**")
				.addResourceLocations("/WEB-INF/views/css/");
		registry.addResourceHandler("/js/**")
				.addResourceLocations("/WEB-INF/views/js/");
		registry.addResourceHandler("/image/**")
				.addResourceLocations("/WEB-INF/views/Images/"); //12-13 將images 改成Images
	}
}
