# docker-B_Java-DB_MySql-F_Next
Proyecto en docker que contiene una aplaición backend en java, persistencia en MySql y Frontednd en React-Next


# Backend
## tecnologias
- [x] java 17
- [x] spring boot 3
- [x] spring data jpa
- [x] spring security
- [x] spring web
- [x] lombok
- [x] jwt
- [x] maven

## docker
- [x] openjdk:17-jdk-alpine
- [x] redis
- [x] mysql

## crear target
```bash
./mvnw clean package
## limpiar el target
# ./mvnw clean
## crear el jar
./mvnw clean package -DskipTests
```

## crear imagen docker
```bash
## compilar imagen
docker build -t mibedelia:1.0 .
## correr imagen
docker-compose up
# docker-compose up -d
```

## reconstruir imagen docker
```bash
./mvnw clean
sleep 10

./mvnw clean package -DskipTests
sleep 20

docker build -t mibedelia:1.0 .
sleep 20
docker-compose up
```
