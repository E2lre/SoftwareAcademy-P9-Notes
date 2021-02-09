# SoftwareAcademy-P9-Notes
Micro-service NOTES manage the praticiens notes for patients

## Installation

### Database installation
* execute ???????? file

### DATA BASE STARTER
Go to dectory  C:\"Program Files"\MongoDB\Server\4.4\bin\
Execute mongod.exe

### Docker image construction in project directory :

docker build --build-arg JAR_FILE=target/*.jar -t p9-notes .

### Docker execution :

docker run -p 9105:9105 --name Notes p9-notes

execute command line to start all components: docker-compose up -d

### divers

### lancement de zipkin 
* depuis le répertoire de zipkin : java -jar zipkin-server-2.6.1-exec.jar
* lancer : http://localhost:9411 


C:\"Program Files"\MongoDB\Server\4.4\bin\mongod