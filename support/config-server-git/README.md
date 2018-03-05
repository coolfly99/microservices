1. RabbitMq in Mac
http://www.rabbitmq.com/install-standalone-mac.html

brew install rabbitmq
brew services start rabbitmq

login http://localhost:15672/#/  guest/guest as administrator

2. Kafka in Mac
brew install kafka

brew services start zookeeper
brew services start kafka

3. redis
brew install redis
brew services start redis

4.Version Matching Spring boot 1.5 with Spring cloud Edgware.SR2
http://projects.spring.io/spring-cloud/

5. Reference:
https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html

https://localhost:8888/info.foo/application


6. Usage by Git Repo:
cd $HOME
# see spring.cloud.config.server.git.uri: file:///${user.home}/config-repo
mkdir config-repo  
cd config-repo/
git init .
echo info.foo: bar > application.properties
git add .
git commit -m "Add Application.properties"
echo info.foo: bar.dev > application-dev.properties
git add .
git commit -m "add dev profile"

# default 
https://localhost:8888/info.foo/default
# dev profile
https://localhost:8888/info.foo/dev

[/{name}/{profile}/{label}
http://localhost:8888/demo-config-client-app/default/master