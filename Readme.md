# SoftwareAcademy-P9-Notes
Micro-service NOTES manage the praticiens notes for patients

## Installation

### Database installation
* execute ???????? file

### DATA BASE STARTER
Go to dectory  C:\"Program Files"\MongoDB\Server\4.4\bin\
Execute mongod.exe

# Manual application start
## Environement start
* Start Zipkin in zipkin directory : java -jar zipkin-server-2.23.2-exec.jar
* Start MongDb in mongodb directory : C:\"Program Files"\MongoDB\Server\4.4\bin\mongod
* Start Eureka microservice
* Start Config server microservice
* Start spring admin  microservice
* Start zuul microservice
## Application start
* Start user microservice
* Start patientV2 microservice
* Start note microservice
* Start Mediscreen IHM : ng serve

# Docker application start
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