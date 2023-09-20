package si.fri.rso.samples.orders.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Orders API", version = "v1",
        contact = @Contact(email = "nv7834@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing orders."),
        servers = @Server(url = "http://localhost:8080/"),
        externalDocs = @ExternalDocumentation(description = "Order application repository", url = "https://github.com/rso-2022-2023/RSO_S47_Order"))
@ApplicationPath("/v1")
public class OrderApplication extends Application {

}
