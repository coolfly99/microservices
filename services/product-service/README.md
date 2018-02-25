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