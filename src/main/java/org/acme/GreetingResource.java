package org.acme;

import java.util.HashMap;
import java.util.Map;

import org.acme.crud.Value;
import org.apache.commons.text.StringSubstitutor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Pool; 
import io.vertx.mutiny.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;

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

    public static final String version = "2.0";

    //private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @Inject
    GreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello/{name}")
    public String greeting(String name) {
        Log.info("New request Saying: " + name);
        //Log.info(greeting);
        Map<String, String> values = new HashMap<String, String>();
        values.put("name", name);
        values.put("hostname", hostname);
        values.put("namespace", namespace);
        values.put("version", version);
        // Build StringSubstitutor
        StringSubstitutor sub = new StringSubstitutor(values);
        // Replace
        String resolvedString = sub.replace(greeting);
        // Log.info(resolvedString);
        // return service.greeting(name);
        return resolvedString;
    }

    private final Pool client;

    public GreetingResource(Pool client) {
        /*
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
        .setPort(3306)
        .setHost("the-host")
        .setDatabase("mydatabase")
        .setUser("myuser")
        .setPassword("mypassword");

        // Create the client pool
        client = MySQLBuilder.pool()
        .connectingTo(connectOptions)
        .build();
        */
        this.client = client;
    }

    @GET
    @Path("/db")
    public Multi<Value> get() {
        Log.info("DB host: " + host + " port: " + port + " user: " + username + " database: " + database);
        Log.info("Reading records from the database");
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
}
