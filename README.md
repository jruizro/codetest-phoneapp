# Phone Catalog App Microservice

Microservice to get a Phone Catalog and create a Costumer Oder of phones in catalog.

### API Information

Service have 2 end point:
(See API Swagger2 Documentation [here](/swagger-ui.html#).):

- Get all Catalog Phones
> GET v1/phones

- Create a Costumer order to buy some phones by name
> POST v1/order


### Design Notes

DDD (Domain Driven Design) approach.

- ```Application Service Layer```: 
  - get Collections filtered
  - get Phone Catalog
- ```Model Layer```: Aggregate Root Entity -> `CostumerOrder`
- ```Infrastructure/Framework Layer```

### Technical Information

Frameworks and libs used:

- JAVA 11
- Spring Boot 2.4.2
- JUnit 5
- Swagger2 API Documentation

### Repository Links

- [GitHub Repository](https://github.com/jruizro/codetest-phoneapp)
