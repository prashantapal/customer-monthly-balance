### rest-app-layer

This is the rest layer or business layer of the <b>customer-monthly-balance</b> application. This module calculates customer balances, transactions, cumulative balances etc and sends them to the UI layer. This module is a springboot microservice. This module talks with rest module to fetch customer balances and shows in UI.

### Setup Development Environment

- ##### Requirement
  - Java 1.8
  - Maven 3.7+

- ##### Compile and build it
  - In this project directory, use `mvn clean install`.
  - To skip test cases, use `mvn clean install -Dmaven.test.skip=true`.

- ##### IDE
  - Import the [rest-app-layer](https://github.com/prashantapal/customer-monthly-balance/tree/master/rest-app-layer) directory as Maven project using projects root 'pom.xml'.

- ##### Code style
  - No tab characters
  - Tab = 4 spaces
  - Use [EditorConfig](http://editorconfig.org/)

### Run it in local environment
  - One way is, open the project in IDE and run the class `CustomerMonthlyBalanceRestApplication`.
  - or, after maven build, run `java -jar target/customer-monthly-balance-rest-master-SNAPSHOT.jar`.
  - or, after maven build, run `mvn spring-boot:run`.
  - Data configuration can be changed from [here](https://github.com/prashantapal/customer-monthly-balance/blob/master/rest-app-layer/src/main/resources/application.properties).
  - The rest application runs on port 9357.
  - Health check url - http://localhost:9357/health-check
  - Account statements for last 6 months - GET http://localhost:9357/account-statement
  - All Account statements - GET http://localhost:9357/account-statement/all
  - <b>Please note</b>, account statements end points need extra header for authorization and based on user name given in
    authorization header, account statement is returned for that user.

    Example -

    `curl -X GET 'http://localhost:9357/account-statement' --user 'admin:admin'`

    `curl -X GET 'http://localhost:9357/account-statement' --user 'test_user:userpassword'`

  - ##### Inmemory H2 database

     - H2 console: `http://localhost:9357/console`
     - Jdbc Url: `jdbc:h2:mem:testdb`
     - User name: `sa`
     - Password: `<blank>`
     - Table name: [Transaction](https://github.com/prashantapal/customer-monthly-balance/blob/master/rest-app-layer/src/main/resources/schema.sql)
     - Initial configuration: [here](https://github.com/prashantapal/customer-monthly-balance/blob/master/rest-app-layer/src/main/resources/schema.sql)

### Run it in remote environment

  - Change the data configuration if you need, from [here](https://github.com/prashantapal/customer-monthly-balance/blob/master/rest-app-layer/src/main/resources/application.properties).
  - Logging configuration can be changed from [here](https://github.com/prashantapal/customer-monthly-balance/blob/master/rest-app-layer/src/main/resources/logback-spring.xml).
  - Build the project in local using `mvn clean install`.
  - The jar file will be created inside target folder with the name `customer-monthly-balance-rest-master-SNAPSHOT.jar`.
  - Transfer the jar file from local system to remote location using scp or sftp.
  - To deploy and run the application, go to the remote location and execute below command.

      ```nohup java -jar -DHCDL_REST customer-monthly-balance-rest-master-SNAPSHOT.jar >> <log_file_name>  2>&1 &```
  - During run time database configuration can be overridden using `-Dspring.datasource.url=<database url>` etc.
  - The rest application runs on port 9357.
  - Health check url - curl -X GET 'http://localhost:9357/health-check'
  - To stop the application, execute below command,

    ```ps aux | grep HCDL_REST | grep 'customer-monthly-balance-rest-master' | awk {'print$2'} | xargs kill -9```

