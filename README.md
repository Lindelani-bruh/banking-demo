# Getting Started

This is a proof-of-concept for a banking application that exposes functionalities
such as deposit, transfer, and report generation via a RESTful API interface to customers.


### Prerequisites
The following prerequisites are required to build and run the solution:
* *OpenJDK 20.0.0* ^

### Running application
The easiest way to get started is to run the application is to run it in a docker container using the docker file in the application.
```
# Build image
docker build -t demo .
```

```
# Run image
docker run -p  8080:8080 demo
```

Else you run the application using Graddle to build and run the application in your development environment:

```
# Build
./gradlew build 
```

```
# Run application
./gradlew bootRun 
```

### What's next
The application is a pretty basic banking restful API service with 5 API endpoints. 
* ```POST``` api/auth
* ```GET``` api/account
* ```POST``` api/deposit
* ```POST``` api/transfer
* ```GET``` api/reports

It comes with two pre-configured users that you can find in the directory ```demo\src\main\kotlin\demo\config``` with their credentials.
I use an in-memory DB therefore no need to worry about DB setup. To start testing the application go ahead and import the postman collections contained within this project ```Banking-demo.pastman_collection```.
Authenticate yourself using one of the users you found in the config file. Use the returned token to set up you bearer token auth in postman for all your subsequent calls. To view the user's account call ```api/account/{email}```. 
To deposit an amount to a user's account. You can use ```api/deposit/{accountId}``` (account id can be retried  from previous call). You specify the amount and currency in the body of the request. Finally, 
to transfer money to another account you can use the endpoint ```api/transfer/{accountId}``` (accountId here, just like with deposit, it is the account ID of the account were doing the transfer from). The destination account, 
currency and amount to transfer is specified in the API body.

