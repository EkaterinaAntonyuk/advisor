# SpendAdvisor Backend
## About
Backend for [Junction Connected 2020 Project](https://app.hackjunction.com/projects/junction-2020-connected/view/5fa5b735fe2a9500432f9962)
for OP Bank case.
SpendAdvisor is a mobile application intended to help with monthly spending planning.

Technologies: Java, Spring Boot, JDBC + PostgreSQL, Swagger, Lombok

## Project structure
Code is located in `fi.op.junction.advisor`:
* `controller` - REST API controllers
* `model` - domain model
* `repository` - persistence layer (actual db queries)
* `service` - core business logic
* `configuration` - application configuration classes