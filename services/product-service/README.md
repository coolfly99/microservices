1. Create MYSQL DB
create database services;
grant all privileges on services.* to 'services'@'localhost' identified by "services"; 
flush privileges;

2. 

using the swagger api is easier http://localhost:8080/swagger-ui.html
try service properties: http://localhost:8080/api/hello
Actuator: http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/httptrace

Trace Http:
https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/production-ready-http-tracing.html
Jdbc Store Trace: HttpTraceRepository



Reference:
https://reflectoring.io/documenting-spring-data-rest-api-with-springfox/
http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

Test Secure API:
curl -k https://localhost:8443/oauth/token -d "grant_type=client_credentials&scope=read" -u product:secret


curl -k -X GET -H "Authorization: Bearer {token}" https://localhost:6043/api/rest/greeting


Access Swagger:
https://localhost:6043/api/swagger-ui.html

curl  http://localhost:8081/oauth/token -d "grant_type=password&scope=read&username=user&password=password" -u product:secret

curl -k https://localhost:8443/oauth/token -d "grant_type=password&scope=read&username=user&password=password" -u trusted:secret
