package com.example.demo.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();



    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("leetcodeApi")//
                //创建人
                .contact(new Contact("xx", "http://www.baidu.com", "123456@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("")
                .build();
    }
    
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
	  return new Function<Class<?>, Boolean>() {
	    public Boolean apply(Class<?> input) {
	    	return (input.getPackage().getName().startsWith(basePackage));
	    }
	  };
	}

      public static Predicate<RequestHandler> basePackage(final String basePackage) {
    	  return new Predicate<RequestHandler>() {
    	      @Override
    	      public boolean apply(RequestHandler input) {
    	        return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    	      }
    	    };
      }
      
      private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
    	    return Optional.fromNullable(input.declaringClass());
    	  }
}
