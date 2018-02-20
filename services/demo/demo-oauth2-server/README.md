v1: In Memory OAuth Server

Work with JWT RSA Private/Public Key:
1. Generate PEM:
openssl genrsa -out jwt.pem 2048
2. Generate Private Key:
openssl rsa -in jwt.pem
3. Generate Publish Key:
openssl rsa -in jwt.pem -pubout

Bcrypt Online Tool:
http://www.devglan.com/online-tools/bcrypt-hash-generator


Sample password workflow:
curl localhost:8081/oauth/token -d "grant_type=password&scope=read&username=user&password=password" -u trusted:secret
Mutiple scope:
curl localhost:8081/oauth/token -d "grant_type=password&scope=read+write&username=user&password=password" -u trusted:secret
Invalid scope:
curl localhost:8081/oauth/token -d "grant_type=password&scope=global&username=user&password=password" -u trusted:secret