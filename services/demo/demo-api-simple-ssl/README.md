Reference:
https://www.thomasvitale.com/https-spring-boot-ssl-certificate/
http://www.willrey.com/support/ssl_Weblogic.html

For jks:
keytool -genkey -alias server -keyalg RSA -keysize 2048 -keystore keystore.jks -storetype jks -storepass secret -keypass password
What is your first and last name?
  [Unknown]:  www.octopus.com
What is the name of your organizational unit?
  [Unknown]:  dev
What is the name of your organization?
  [Unknown]:  corp
What is the name of your City or Locality?
  [Unknown]:  shanghai
What is the name of your State or Province?
  [Unknown]:  shanghai
What is the two-letter country code for this unit?
  [Unknown]:  CN
Is CN=www.octopus.com, OU=dev, O=corp, L=shanghai, ST=shanghai, C=CN correct?
  [no]:  yes


Warning:
The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.jks -deststoretype pkcs12"

Spring Boot Setting:
server.port = 8443
server.ssl.key-store = classpath:keystore.jks
server.ssl.key-store-password = secret
server.ssl.key-password = password


Import jks:

keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.jks -deststoretype pkcs12

For P12:

keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650
Enter keystore password:  
Re-enter new password: 
What is your first and last name?
  [Unknown]:  www.octopus.com
What is the name of your organizational unit?
  [Unknown]:  dev
What is the name of your organization?
  [Unknown]:  corp
What is the name of your City or Locality?
  [Unknown]:  shanghai
What is the name of your State or Province?
  [Unknown]:  shanghai
What is the two-letter country code for this unit?
  [Unknown]:  CN
Is CN=www.octopus.com, OU=dev, O=corp, L=shanghai, ST=shanghai, C=CN correct?
  [no]:  yes
  
server.port=8443
security.require-ssl=true
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.key-alias=tomcat

