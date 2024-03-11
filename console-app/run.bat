if not exist target/console-app-1.0.0.jar (mvn clean install)

java -cp target/console-app-1.0.0.jar;target/libs/* com.kjs.Main %1

#java -cp target/console-app-1.0.0.jar;target/libs/* com.kjs.Main src/test/resources/validCustomerData.csv