package database;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database database;
    private Map<String, DBCollection> collections;

    private Database() {
        this.collections = new HashMap<>();
        try {
            @SuppressWarnings("deprecation")
            DB mongoDatabase = new MongoClient().getDB("Flighty");
            collections.put("Flights", mongoDatabase.getCollection("Flights"));
            collections.put("Hotels", mongoDatabase.getCollection("Hotels"));
            collections.put("Users", mongoDatabase.getCollection("Users"));
            collections.put("Bookings", mongoDatabase.getCollection("Bookings"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Database getInstance() {
        if(database == null) {
            database = new Database();
        }
        return database;
    }

    public <T> void create(String collectionName, T object) {
        collections.get(collectionName).insert(BasicDBObject.parse(object.toString()));
    }

    public Object get(String collectionName, String field, String value) {
        try {
            return collections.get(collectionName).findOne(BasicDBObject.parse("{" + field + ": " + value + "}"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public DBObject get(String collectionName) {
        try {
            return collections.get(collectionName).findOne();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public DBCursor getAll(final String collectionName) {
        DBCursor cursor = null;
        try {
            cursor = collections.get(collectionName).find();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return cursor;
    }

    public <T> void update(final String collectionName, String id, T newObj) {
        DBObject old = collections.get(collectionName).findOne(BasicDBObject.parse("{id: \"" + id + "\"}"));
        if(old == null) {
            System.err.println("Doesn't exist");
            return;
        }
        collections.get(collectionName).update(old, BasicDBObject.parse(newObj.toString()));
    }

    @SuppressWarnings("deprecation")
    public <T> void delete(final String collectionName, T obj) {
        collections.get(collectionName).remove((DBObject) JSON.parse(obj.toString()));
    }
}
