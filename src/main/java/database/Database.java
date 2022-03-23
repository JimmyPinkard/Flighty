package database;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.experimental.filters.Filters;

import java.util.List;

public class Database {
    private final Datastore datastore;
    private static Database database;

    private Database() {
        datastore = Morphia.createDatastore(MongoClients.create(), "Flighty");
        datastore.getMapper().mapPackage("model");
        datastore.ensureIndexes();
    }

    public static Database getInstance() {
        if(database == null) {
            database = new Database();
        }
        return database;
    }

    public <T> void create(T object) {
        datastore.insert(object);
    }

    public Object get(String entityType, String field, String value) {
        try {
            return datastore.find(entityType).filter(Filters.eq(field, value)).first();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public List<Object> getAll(String entityType) {
        List<Object> objs = null;
        try {
            objs = datastore.find(entityType).iterator().toList();
        }
        catch(Exception e) {
            System.err.println();
            System.exit(1);
        }
        return objs;
    }

    public <T> void update(T oldObj, T newObj) {
        datastore.delete(oldObj);
        datastore.insert(newObj);
    }

    public <T> void delete(T obj) {
        datastore.delete(obj);
    }
}
