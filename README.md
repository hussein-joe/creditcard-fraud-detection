# creditcard-fraud-detection
Credit card fraud detection challenge developed using SpringBoot and Java 8.

## Getting Started
This is a command line application which you can run using the following command
```
mvn -q spring-boot:run
```
You ned to provide the following parameters
- Fraud detection date, it is expected to type it in the format ```yyyy-mm-dd```.
- Fraud detection threshold in the format ```dollars.cents```

The application will keep trying to get correct values in the expected format. 
For simplicity, I chose to read the transaction lines from the file which can be configured using the property 
```
application.config.transaction-file-path
```
__If the application detects any frauds, it will print all of them and then exits.__

When you run the application, it will take some time without showing any logging messages, 
I disabled SLF4J logging(ERROR level), and maven as well.

### Prerequisites
- Java 8
- Maven

### Application design
In this challenge I tried to use different programming styles. For example I used the concept of
stream processing, message channel(to implement pub/sub). 
The code is divided into the following parts

- Transaction Reader: It reads transactions from a file, use mapper to convert lines to DTOs, and then hand it to a 
message channel. The reader in this case is a publisher of events transaction record is the message's payload'.
- Message channel: The channel for publishing events from supplier to one or many listeners. 
Using this message channel, the publisher does not need to know any thing about how the published messages
will be processed. Also, the consumers from the other side are not coupled to the publishers.
For simplicity, I implemented a simple direct synchronous channel where publishing and consumption share
the same thread. To support parallel-processing for example, it would be easy to change the channel without touching 
the publishers and the listeners.
- Channel listener: There is only one listener in this application which is responsible for detecting
the fraud credit card transactions. For simplicity, I use a simple in-memory repository to save fraud transactions.
Another examples of listeners can be easily plugged into the channel. Like for example
    - __Publish events to Kafka__
    - Send a command to __a CQRS event-sourcing service__.
    - Store them in database.
 

### Coding
I tried to follow best practices while implementing this code, for example
- SOLID: every class has a single responsibility, all dependencies are injected so it was easy to mock them in test classes, ...
- Design patterns: I tried to use different design patterns, for example Observer(I used standard java implementation of 
this pattern just for simplicity), builder, ...
- TDD, as I believe in automation and CI/CD, business classes are covered with unit test cases.

### Note
I am aware that I could implement this in simpler way according to **KISS**, but I loved to use
different programming styles as the purpose of this challenge is to know how I structure and design my code.
If I implemented this task in production I would definitely use one of the following options
- Use **spring integration** which supports fault-tolerance, back-pressure, parallel-processing, and many more features 
ready to be used with some custom coding.
- Use **Reactive/RXJava** to implement parallel-processing, streaming and reactive programming, back-pressure, ...
- Use Java 8 features like stream and few custom classes.
 
I added comments in some of the classes to describe why I added these classes.