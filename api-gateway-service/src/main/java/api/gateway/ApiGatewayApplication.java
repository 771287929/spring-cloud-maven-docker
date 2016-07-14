package api.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
public class ApiGatewayApplication {
    public static void main( String[] args ) {
        new SpringApplicationBuilder(ApiGatewayApplication.class).web(true).run(args);
    }
}
