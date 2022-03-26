package database;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Database database;
    private Map<String, DBCollection> collections;

    private Database() {
        this.collections = new HashMap<>();
        try {
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
        collections.get(collectionName).insert((DBObject) JSON.parse(object.toString()));
    }

    public Object get(String collectionName, String field, String value) {
        try {
            return collections.get(collectionName).findOne(JSON.parse("{" + field + ": " + value + "}"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public Object get(String collectionName) {
        try {
            return collections.get(collectionName).findOne();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public List<DBObject> getAll(final String collectionName) {
        List<DBObject> objs = new ArrayList<>();
        try {
            collections.get(collectionName).find().forEach(objs::add);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return objs;
    }

    public <T> void update(final String collectionName, T oldObj, T newObj) {
        collections.get(collectionName).update((DBObject) JSON.parse(oldObj.toString()), (DBObject) JSON.parse(newObj.toString()));
    }

    public <T> void delete(final String collectionName, T obj) {
        collections.get(collectionName).remove((DBObject) JSON.parse(obj.toString()));
    }
}
