# Client - Management

This app was created with Spring.io - tips on working with the code [can be found here](https://start.spring.io/)).
Feel free to contact us for further questions.

## Development

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

After starting the application it is accessible under `localhost:8080`.

The application can be used with Postman and connect to a real Mysql database.

[//]: # (## MySQL)

[//]: # ()
[//]: # ([Link  MySQL export]&#40;genesis_user_info.sql&#41;)

[//]: # ()
[//]: # (<details>)

[//]: # (  <summary>Create Database "genesis"...</summary>)

[//]: # ()
[//]: # (  ```sql)

[//]: # (  CREATE)

[//]: # (DATABASE `genesis`)

[//]: # (  /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */)

[//]: # (  /*!80016 DEFAULT ENCRYPTION='N' */;)

[//]: # (  ```)

[//]: # ()
[//]: # (</details>)

[//]: # (<details>)

[//]: # (  <summary>Create Table "user_info"...</summary>)

[//]: # ()
[//]: # (```sql)

[//]: # (CREATE TABLE user_info)

[//]: # (&#40;)

[//]: # (    id        BIGINT PRIMARY KEY AUTO_INCREMENT,)

[//]: # (    name      VARCHAR&#40;255&#41;,)

[//]: # (    surname   VARCHAR&#40;255&#41;,)

[//]: # (    person_id VARCHAR&#40;12&#41; UNIQUE,)

[//]: # (    uuid      VARCHAR&#40;255&#41; UNIQUE)

[//]: # (&#41;;)

[//]: # (```)

[//]: # ()
[//]: # (</details>)

[//]: # (<details>)

[//]: # (  <summary>Add some "users"...</summary>)

[//]: # ()
[//]: # (```sql)

[//]: # (INSERT INTO user_info &#40;name, surname, person_id, uuid&#41;)

[//]: # (VALUES &#40;'Alain', 'Morisette', '123144789987', '4b72e0e6-ee1c-4494-8942-bfa47b444830'&#41;,)

[//]: # (       &#40;'James', 'Blant', '1237865f4321', '317544d2-30cc-4b39-832a-152b91085e10'&#41;,)

[//]: # (       &#40;'Bruce', 'Lee', '1235765f8721', 'edcd6bbc-eece-4a06-936f-0f331d7d715b'&#41;;)

[//]: # (```)

[//]: # ()
[//]: # (</details>)

[//]: # ()
[//]: # (## Postman)

[//]: # ()
[//]: # ([Link  Postman export]&#40;GenesisResources.postman_collection.json&#41;)

[//]: # ()
[//]: # (## Front-end web)

[//]: # ([link for web]&#40;http://localhost:8080/index.html&#41;)

[//]: # ()
[//]: # ()
[//]: # (-------)