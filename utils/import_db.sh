mongo --eval 'db.getMongo().getDBNames().forEach(function(i){db.getSiblingDB(i).dropDatabase()})'
mongoimport --db Flighty --collection Flights --jsonArray --file database/flights.json
mongoimport --db Flighty --collection Hotels --jsonArray --file database/hotels.json
mongoimport --db Flighty --collection Users --jsonArray --file database/userdata/coleca.json
mongoimport --db Flighty --collection Users --jsonArray --file database/userdata/realHughMann.json