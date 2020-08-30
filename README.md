# Cognizant
# Project Title

REST API Automation Assignment

## Getting Started

### Prerequisites

To build you require
 .Apache Maven
 .Java Developers Kit(e.g. OpenJDK 11)

For using the recommended IDE you require:
	•	Eclipse of Java /intellij

### Installation
 Clone the repo or download the repo from this link

```
https://github.com/suranjana101/Cognizant.git
```

Setup IDE
	1.	Start Eclipse/intellij
	2.	Select "File > Import". Then, select "Maven > Existing Maven Projects" and click "Next"
	3.	In the "Root Directory", browse to cognizant-api-content source code directory on your filesystem and select "Open"
	4.	Optionally, you can add it to a "Working set"
	5.	Click "Finish"

### Build

To compile go to the sources folder and execute the command:

```
$ mvn clean install
```

After successful compile the binary will be available at target/binary.jar.


## Running the tests

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the ServingWebContentApplication class from your IDE.
Alternatively you can use the Spring Boot Maven plugin like so:

```
mvn spring-boot:run
```
The web application is accessible via localhost:8080/rabo

To execute the tests before running the application use below command

```
  mvn -P test-then-run

```

## Built With

* [SpringBoot]
* [Maven]


## Authors

* **Suranjana Bora** 

