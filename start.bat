@echo off

rem Change to the directory where your docker-compose.yml is located
cd /d D:\Apps\IdeaProjects\spring-boot-microservices

rem Start the services defined in the docker-compose.yml file
docker-compose up -d keycloak zookeeper broker zipkin