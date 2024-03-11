if not exist ./console-app/target/console-app-1.0.0.jar (call mvn clean install)

java -cp console-app/target/console-app-1.0.0.jar;./console-app/target/libs/* com.kjs.Main %1
