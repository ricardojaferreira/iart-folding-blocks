### Starting Instructions

This project uses Maven as the dependency manager, make sure you have Maven installed: (https://maven.apache.org/).
Maven is the easiest way to compile and run this project. The project also has some unit tests and with maven the unit tests run before the
generation of the code to run.

After cloning the project run:

`mvn clean install -U`

This will download all the dependencies and create a target folders were the jars will be created.

#### Runnig on the command line

Use one  of the following options:

* `java -cp application/target/application-${version}-jar-with-dependencies.jar pt.up.fe.iart.application.Application`
* `java -jar application/target/application-${version}-jar-with-dependencies`
* `mvn exec:java -pl application`

#### Running on the IDE

This should run without any specific configuration


### Style Conventions

[https://checkstyle.sourceforge.io/](https://checkstyle.sourceforge.io/)
