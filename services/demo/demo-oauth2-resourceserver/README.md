1. Generate self-signed jks
keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048

jks to p12:
keytool -importkeystore -srckeystore mykeystore.jks -destkeystore mykeystore.p12 -deststoretype PKCS12

2. Tomcat mutiple connectors sample:
https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors

3. Get Token
curl -k https://localhost:8443/oauth/token -d "grant_type=password&scope=read&username=user&password=password" -u trusted:secret


curl -k https://localhost:9043/

curl -k https://localhost:9043/api/hello -H "Authorization: Bearer {token}"

curl -k -X GET -H "Authorization: Bearer {token}" https://localhost:9043/api/hello

curl -k -X GET -H "Authorization: Bearer {token}" https://localhost:9043/api/

curl -k -X GET -H "Authorization: Bearer {token}" https://localhost:9043/api/

curl -k -X GET -H "Authorization: Bearer {token}" https://localhost:9043/api/hello

curl -k -X GET -H "Authorization: Bearer {token}" https://localhost:9043/api/greeting


HTTPClient access with https:
http://www.baeldung.com/httpclient-ssl