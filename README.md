# Using multiple datasources with Spring running on Cloud Foundry

This project shows how to set up multiple datasources in a Spring Boot application,
and how to run this application on Cloud Foundry.

## How to use it?

You need a JDK 8 to compile this project:
```shell
$ ./mvnw clean package
```

### Run locally

When you run this project locally, a default embedded database is automatically
used. Two in-memory database instances are created for ease of use:
```shell
$ java -jar target/cf-spring-multiple-datasources.jar
```

Display the content of the two databases:
```shell
$ curl http://localhost:8080
Found no people in database
--------
Found no superheroes in database
```

The databases are empty when you start the application.
You can easily populate databases using this endpoint:
```shell
$ curl http://localhost:8080/init?count=10
Initialized 10 people and 10 superheroes%
$ curl http://localhost:8080
Found people:
  - Aliya Armstrong
  - Brendon Fahey
  - Cloyd Fay
  - Virginie Haley
  - Meghan Murray
  - Verner O'Conner
  - Heaven Ortiz
  - Creola Turner
  - Katlynn Von
  - Jaqueline Wisoky
--------
Found superheroes:
  - Agent Violet
  - Captain Predator Strange
  - Cerebra X
  - Colin Wagner IX
  - Electro
  - General Bantam
  - General Monarch Thirteen
  - Magnificent Proto-Goblin Brain
  - Polaris II
  - The Cerebra IX
```

### Run on Cloud Foundry

Prior to running this application on Cloud Foundry, you need to create two database
instances. This app is designed to be bound to two MySQL databases: `db1` and `db2`.
Please refer to your platform documentation to see how to create and bind these
database instances.

When the database instances are ready, then you can just push the application to
Cloud Foundry:
```shell
$ cf push
```

Don't forget to populate the databases with random data, as specified above.

## How does it work?

The implementation is heavily inspired by [this article](https://medium.com/@joeclever/using-multiple-datasources-with-spring-boot-and-spring-data-6430b00c02e7).
Two persistence units are created, each located in a dedicated package.
Each persistence context is bound to a datasource, with a dedicated transaction
manager.

Look at the source code to understand how to set up these datasources.
In particular, look at `PersonConfig` and `SuperheroConfig`, where dedicated
instances are created for each datasource.

This project is using [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
to create JPA repositories using a single interface. You can also create your own
JPA repository components by injecting an `EntityManager` instance: just use a
`@Qualifier` annotation to select the datasource you want to use.

## Contribute

Contributions are always welcome!

Feel free to open issues & send PR.

## License

Copyright &copy; 2018 Pivotal Software, Inc.

This project is licensed under the [Apache Software License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
