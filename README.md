# Barcode Validator
This project was generated using spring boot 3.2.1 and Java Version 17
This project provides the functionality to validate the S10 Barcode based on the predefined business conditions

# Setting up the project
  - Download the project from github to your local or clone using the git clone method
  - Import the project into your IDE as maven project.
  - You need to have atleast Java 17 installed on your system to run this application
  - Once all the dependencies are resolved, Run the project as Spring Boot Project and application will be deployed as http://localhost:8080

## Assumptions Made
  - S10 Barcode will follow the below structure
      - First 2 Alphabets in Capital only as Service Indicators
      - Next 8 Digits will be in range 00000000 to 99999999 as Serial Number
      - Next will be a single digit from 0 to 9 as Check Digit
      - Last 2 Aplhabets will always be GB as ISO Country Code
  - An assumption has been made here that if the barcode does not met the above conditions and also length 13, then we are throwing an exception
    and api will repond back with message "Barcode Format is Incorrect" with status code of 400 as bad request.
  - This has been done deliberatley instead of saying as Invalid Barcode or sending false flag, since we can accordingly manage custom exceptions in our project
  - For other scenarios, api will give below response as a JSON Format with status code of 200
    
      {
        "barcode": "string",
        "isValid": true
      }
     
## Running the application
  - Run the application as Spring Boot Project from any IDE
  - Alternatively, once the source code is cloned into local machine, below commands can be executed in command prompt to package and run the application
      - mvn clean package
      - java -jar target/s10-barcode-validator-0.0.1-SNAPSHOT.jar
  - Go to any Rest API Client like Postman and follow the below steps to test the api
      - Endpoint URL - http://localhost:8080/api/validate
      - This is a GET Endpoint and accept a required Request Parameter by name "barcode". The value for the parameter is the barcode to be validated.
      - The api is protected using basic spring security and hence will need to pass authorization as Type Basic Auth
        with credentials as Username : admin and Passwrod : admin in Postman. Without the basic auth the api will not work.

        ![image](https://github.com/rsinghaniya/BarcodeValidator/assets/43132719/5bd84b76-931e-43a4-a80d-c1f0b1177700)

  - Alternatively, you can use Swagger to test the api with below details
      - Swagger URL - http://localhost:8080/swagger-ui/index.html
      - Once you hit the url, it will redirect to the spring security login page where the credentails as Username : admin and Passwrod : admin
        needs to be entered to access the api
      - Once logged in, you can test the api by inputting the barcode into endpoint /api/validate
        
       ![image](https://github.com/rsinghaniya/BarcodeValidator/assets/43132719/4879e45a-5365-43a6-9496-52300c4832f1)


## Running unit tests
  - JUnit unit test cases and integration test cases are provided within the project and can be executed locally.
  - Alternatively with mvn clean package command it will run all the test
  - This test cases can be configured to be executed in CI/CD pipeline to be executed so that code coverage can also be checked on later.
