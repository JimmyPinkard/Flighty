package database;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.Mapper;
import dev.morphia.query.experimental.filters.Filters;

import java.util.List;

public class Database {
    private final Datastore datastore;
    private static Database database;

    private Database() {
        datastore = Morphia.createDatastore(MongoClients.create(), "Flighty");
        mapPackages();
        datastore.ensureIndexes();
    }

    public static Database getInstance() {
        if(database == null) {
            database = new Database();
        }
        return database;
    }

    private void mapPackages() {
        Mapper mapper = datastore.getMapper();
        mapper.mapPackage("model");
        mapper.mapPackage("model.users");
        mapper.mapPackage("model.bookables.hotel");
        mapper.mapPackage("model.bookables.flight");
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

    public Object get(String entityType) {
        try {
            return datastore.find(entityType).toDocument();
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
            System.err.println(e.getMessage());
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
