package Finsight.MONEY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class MoneyApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(MoneyApplication.class, args);
	}
	// This bean configures the OpenAPI documentation for the Money application, providing details such as title, version, description, contact information, license, and terms of service.
//	@Bean
//	public OpenAPI customOpenAPI() 
//	{
//    return new OpenAPI()
//                    .info(new Info()
//                    .title("Money API")
//                    .version("1.0")
//	                .description("API documentation for Money application")
//	                .contact(new Contact()
//	                .name("Finsight")
//	                .email ("abc@gmail.com")
//	                .url("https://www.finsight.com/"))
//	                .license(new License()
//	                .name("MIT License")
//	                .url("https://opensource.org/licenses/MIT"))
//	                .termsOfService("https://www.finsight.com/terms"));
//   
//	}
	
}
