package tk.mybatis.springboot.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnProperty(prefix = "spring.profiles", name = "api_enabled", matchIfMissing = true)
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * 要扫描的API(Controller)基础包
	 */
	private static final String scanBasePackage = "tk.mybatis.springboot.controller";

	private Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).forCodeGeneration(true).pathMapping("/")
				.useDefaultResponseMessages(false);
	}

	@Bean
	public Docket mxDataApi() {
		return apiDocket().groupName("Integral.activity").apiInfo(mxDataApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(scanBasePackage)).paths(PathSelectors.any()).build();
	}

	private Predicate<String> mxDataApiPaths() {
		return PathSelectors.regex("/api/.*");
	}

	private ApiInfo mxDataApiInfo() {
		Contact contact = new Contact("lang_hu", "", "");
		return new ApiInfoBuilder().title("app controller API").description("app系统 接口")
				// .license("Apache License Version 2.0")
				.contact(contact).version("1.0").build();
	}

}
