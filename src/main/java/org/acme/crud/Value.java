package org.acme.crud;

import io.smallrye.mutiny.Multi;
//import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Row;
//import io.vertx.mutiny.sqlclient.RowSet;
//import io.vertx.mutiny.sqlclient.Tuple;

public class Value {

    public static Multi<Value> findAll(Pool client) {
        return client.query("SELECT name FROM mytable").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set)) 
                .onItem().transform(Value::from); 
    }

    private static Value from(Row row) {
        return new Value(row.getString("name"));
    }

    public String name;

    public Value() {
        // default constructor.
    }

    public Value(String name) {
        this.name = name;
    }

}