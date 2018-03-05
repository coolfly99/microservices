1. Install in Mac

brew install mongodb
brew services start mongodb

login http://localhost:15672/#/  guest/guest as administrator

2. create db and user
// Create Db
use config-db;
show dbs;

// Create User
db.createUser( { user: "config",
                 pwd: "config",
                 roles: [
                          { role: "readAnyDatabase", db: "admin" },
                          "readWrite"] },
               { w: "majority" , wtimeout: 5000 } )
// Create Collection
use config-db;
db.gateway.insert({
  "label": "master",
  "profile": "prod",
  "source": {
    "user": {
      "max-connections": 1,
      "timeout-ms": 3600
    }
  }
});

4.Verify:
Access:
http://localhost:8888/master/gateway-prod.propertie

5. Reference:

https://github.com/spring-cloud-incubator/spring-cloud-config-server-mongodb
