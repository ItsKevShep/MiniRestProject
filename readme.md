This Maven project has been created with 3 modules, each of which handle a specific part of the requirements. It was designed this way so a single
repo can be provided but with the logic for the various requirements handled in their own project, and meant that one project could be used to 
create the executable jar and another as a Spring Boot application.

# Modules

## csv-rest-endpoint-api
This module was created to facilitate objects for communication from client to server, providing the object that can be marshalled to Json on the 
client end and read back on the server. 

When requesting data from the server, this object type is returned instead of the database entity so "internal only" data, such as IDs are not sent
to a client.


## csv-rest-endpoint
This module provides a Spring Boot server exposing three endpoints that can be used to communicate with it.
	- / GET : This can be used to retrieve all customer data
	- /{reference} GET : This can be used to retrieve data for a specified customer (Returning a 400 if no customer exists with that record)
	- / POST : This can be used to add a new customer to the database.
	
H2 is used as the internal memory database, so if the application is stopped and restarted, all data will be lost.

For testing DBunit is used both to bootstrap the database with some data to verify the controller can retrieve it, and to verify the state of
the database after using the controller to save data.


## console-app
This module creates the executable jar that can be used to send a csv file of data to the server.

For testing, Wiremock is used to provide a stub of the server endpoint and verify that the application has sent the expected data to it.


# Helper Files
2 helper files are included for interacting with this project. Both of which will build the application if they detect that the required 
project has not been built.
	- start-server.bat : This will launch the Spring Boot application
	- send-data.bat : This requires a path to a csv file of customer data to be passed through as a parameter
		ex : send-data.bat C:/customer-data.csv
