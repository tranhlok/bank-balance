**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

Today, you will be building a small but critical component of Current's core banking enging: real-time balance calculation through [event-sourcing](https://martinfowler.com/eaaDev/EventSourcing.html).

## Schema
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host. 

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.

You may use any technologies to support the service. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

# Candidate README

## Bootstrap instructions

This section covers the steps required to get the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before proceeding, ensure you have the following installed:
- MySQL.
- **Java JDK 17** or higher: Necessary to run Java applications. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use OpenJDK.
- **Maven 4.0** or higher: Required for managing the project's build. Download and installation instructions can be found on the [Apache Maven Project website](https://maven.apache.org/).

### Clone the Repository / Open Terminal in the Project Folder
To start, clone the project repository from GitHub to your local machine. 
Alternatively, navigate to the project folder and open a terminal.

```bash
git clone https://github.com/codescreen/CodeScreen_khqojgsd.git
cd CodeScreen_khqojgsd
```

### Configure the MySQL Server

```bash
sudo service mysql start
```

Run the mysql_secure_installation script from the command line. Pick your username and password for the server.
```bash
sudo mysql_secure_installation
```

Open the MySQL shell or MySQL Workbench and create a new database for your project:
```bash
CREATE DATABASE your_database_name;
USE your_database_name;
```
Update your application’s configuration files (application.properties) to point to your MySQL server. 
Replace your_database_name, your_username, and your_password with the previously configured values.
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Build the Application
This command also run the unit tests.
```bash
mvn clean install
```

### Run the server
```bash
mvn spring-boot:run
```


## Design considerations
*Replace this: I decided to build X for Y reasons.*

## Bonus: Deployment considerations
*Replace this: If I were to deploy this, I would host it in this way with these technologies.*

## ASCII art
*Optional but suggested, replace this:*
```
                                                                                
                   @@@@@@@@@@@@@@                                               
               @@@@@@@@@@@@@@@@@@@@@                                            
             @@@@@@@@@@@@@@@@@@@@@@@@@@                                         
          @@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@        
        @@@@@@@@@@@@@@@@@@@@@      @@@@@@                        @@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@                 .@@@@@@@@@@@@@@   
   @@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@ 
 @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@@@@@@ 
    @@@@@@@@@@@@@@               @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@    
      @@@@@@@@@@                     @@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       
         @@@@                          @@@@@@@@@@@@@@@@@@@@                     
                                          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@         
                                            @@@@@@@@@@@@@@@@@@@@@@@@            
                                               @@@@@@@@@@@@@@@@@@               
                                                    @@@@@@@@                    
```
## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/32c7bcd3-58c7-4d60-824f-3b70a1ae1de3" target="_blank">this screen</a>.