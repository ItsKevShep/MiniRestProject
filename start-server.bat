if not exist "csv-rest-endpoint/target/csv-rest-endpoint-1.0.0.jar" (call mvn clean install)

mvn -pl csv-rest-endpoint org.springframework.boot:spring-boot-maven-plugin:run
