v1: In Memory OAuth Server

Work with JWT RSA Private/Public Key:

1. Generate self-signed jks
keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048

1. Generate PEM:
openssl genrsa -out keystore.pem 2048
2. Generate Private Key:
openssl rsa -in keystore.pem
3. Generate Publish Key:
openssl rsa -in keystore.pem -pubout
4. Check public key and cert
keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey

Bcrypt Online Tool:
http://www.devglan.com/online-tools/bcrypt-hash-generator


Sample password workflow:

curl -k https://localhost:8443/oauth/token -d "grant_type=password&scope=read&username=user&password=password" -u trusted:secret
???not work
curl --cacert cert.pem https://www.octopus.com:8443/oauth/token -d "grant_type=password&scope=read&username=user&password=password" -u trusted:secret

crlient_credential workflow:
curl -k https://localhost:8443/oauth/token -d "grant_type=client_credentials&scope=read" -u trusted:secret

Mutiple scope:
curl localhost:8443/oauth/token -d "grant_type=password&scope=read+write&username=user&password=password" -u trusted:secret
Invalid scope:
curl localhost:8443/oauth/token -d "grant_type=password&scope=global&username=user&password=password" -u trusted:secret

