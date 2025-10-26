package org.acme.crud;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.sqlclient.Pool;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class DBInit {

    private final Pool client;
    private final boolean schemaCreate;

    public DBInit(Pool client, @ConfigProperty(name = "myapp.schema.create", defaultValue = "false") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    void onStart(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        // TODO
        client.query("DROP TABLE IF EXISTS fruits").execute()
            .flatMap(r -> client.query("CREATE TABLE fruits (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
            .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Kiwi')").execute())
            .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Durian')").execute())
            .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Pomelo')").execute())
            .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Lychee')").execute())
            .await().indefinitely();
    }
}