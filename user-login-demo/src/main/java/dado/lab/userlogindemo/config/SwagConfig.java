package dado.lab.userlogindemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwagConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Universe Top Group - APIs Summarization")
                        .version("v1.0.0")
                        .description("User-login-demo full-stack project by Harry."));
    }
}
