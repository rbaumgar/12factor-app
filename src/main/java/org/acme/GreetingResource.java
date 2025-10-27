package org.acme;

import java.util.HashMap;
import java.util.Map;

import org.acme.crud.Value;
import org.apache.commons.text.StringSubstitutor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Pool; 

@Path("/api")
public class GreetingResource {

    @ConfigProperty(name = "greeting", defaultValue = "Hello ${name} from ${hostname} @ ${namespace} with ${version}")
    String greeting;
    @ConfigProperty(name = "hostname", defaultValue = "unknown")
    String hostname;
    @ConfigProperty(name = "namespace",defaultValue = "unknown")
    String namespace;
    @ConfigProperty(name = "username",defaultValue = "myuser")
    String username;
    @ConfigProperty(name = "password",defaultValue = "mypassword")
    String password;
    @ConfigProperty(name = "host",defaultValue = "localhost")
    String host;
    @ConfigProperty(name = "post",defaultValue = "3306")
    String port;
    @ConfigProperty(name = "database",defaultValue = "mydatabase")
    String database;

    public static final String version = "2.0.1";

    @Inject
    GreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello/{name}")
    public String greeting(String name) {
        Log.info("New request: " + name);
        Map<String, String> values = new HashMap<String, String>();
        values.put("name", name);
        values.put("hostname", hostname);
        values.put("namespace", namespace);
        values.put("version", version);
        // Build StringSubstitutor
        StringSubstitutor sub = new StringSubstitutor(values);
        // Replace
        String resolvedString = sub.replace(greeting);
        return resolvedString;
    }

    private final Pool client;

    public GreetingResource(Pool client) {
        this.client = client;
    }

    @GET
    @Path("/db")
    public Multi<Value> get() {
        Log.info("Reding from database mysql://" + host + ":" + port + "/" + database + " user: " + username);
        return Value.findAll(client);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/health")
    public String health() {
        return "I'm ok";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello")
    public String hello() {
        Log.info("New request");
        return "Hello from Quarkus REST";
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            Log.error("Failed to handle request " + exception.getMessage());

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }
    }

}
