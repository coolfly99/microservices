1. call http://localhost:8080/api/message
=> val = 'value1'

2. update db
update PROPERTIES set val = 'value2' where application = 'demo-config-client-app' and profile = 'default' and label = 'master'

3.
curl -X POST http://localhost:8080/refresh

3. call http://localhost:8080/api/message
=> val='value2'