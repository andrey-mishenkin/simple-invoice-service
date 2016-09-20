Simple Invoice Service Web Application
======================================
This a simple application that modeling an online taxation service. The exposes a RESTful HTTP API that accepts the invoice document, calculates taxes for it and returns the updated document back to the caller.

Build and Run
=============
To build 
> mvn clean package

and run 
> java -jar target/simple-invoice-service-1.0-SNAPSHOT.jar

or using spring-boot:ru
> mvn clean spring-boot:run

The base app dir (`BASE_DIR`) for `dev` envirinment is `./target` for `prod` - `./` (current dir).

Description
============
Exposed API can be exploried via http://localhost:8080/invoice-service/api/v1/application.wadl
Here is the list of services 

Path |  Method | Description
------------ | ----------------- | -------------
invoice/taxitems | POST | Tax rates per tax category are stored in the static configuration (JSON). The service takes each invoice item from the invoice, calculates tax amount according to the item's tax category and updates the item's totalAmount value. Finally, the same invoice after tax calculation (i.e. with updated totalAmount's) is returned to the client. Also error status and error details (if any) returned. See example of request/resposne data structure (JSON).
category/taxrates | GET | Return tax rate per category configuraion 
health | GET | Rerurn system health status in simple form as OK|WARNING|ERROR.
health/detail | GET | Rerurn detailed system health status by service.
health/detail/json | GET | Rerurn detailed system health status by service in JSON format.

invoice/taxitems Request example:
```
{
    "id" : 1,
    "timeStamp" : "2016-09-18T20:01:03.182+0000",
    "items" : [{
        "id" : 11,
        "description" : "Item 11",
        "preTaxAmount" : 100.00,
        "taxCategory" : "c21.1"
    },
    {
        "id" : 12,
        "description" : "Item 12",
        "preTaxAmount" : 10.00,
        "taxCategory" : "c101"
    }]
}
```
Response 
```
{
  "invoice": {
    "id": 1,
    "timeStamp": "2016-09-18T20:01:03.182+0000",
    "items": [
      {
        "id": 11,
        "description": "Item 11",
        "preTaxAmount": 100,
        "totalAmount": 121.1,
        "taxCategory": "c21.1"
      },
      {
        "id": 12,
        "description": "Item 12",
        "preTaxAmount": 10,
        "totalAmount": null,
        "taxCategory": "c101"
      }
    ]
  },
  "errors": [
    "item.id = 12: unknown tax value for taxCategory='c101'"
  ],
  "error": true
}
```
### Configuration
Tax rates by category configuration file located in `BASE_DIR/conf/category-rate-conf.json`.
Created if doesn't exists, pulled every 5 minutes for update. 
### Logs
Application system log: BASE_DIR/invoice-service-system.log
HTTP detail traffic log: BASE_DIR/logs/http-traffic.log
Invioce service processing stat log: BASE_DIR/logs/invoice-tax-stat.log

### Monitoring and Metrics 
Except of app own `api/v1/health` monitoring there are additional monitoring and metrics avalable out of the box. 
[Spring Boot Production Ready](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) endpoints exposed on `http://localhost:8080/invoice-service/system`.
For example http://localhost:8080/invoice-service/system/metrics

Known issues
============
* Output for `http-traffic.log` and `invoice-tax-stat.log` written to console and invoice-service-system.log - it should not.
* Jersy JMX monitoring doesn't exposed

Things TODO
============
* Fix issues.
* Add addtitional monitoring/statistic to main Invoice tax service - processing time, derive meteric for items processed/errors per second.
* Add more details for `http-traffic.log` - full content of HTTP request/response 
* Add service for Tax Rate configuration change via REST.
* Add service for application configuration read/change: traffic log on/off, invioce service stat details.
* Tune separation of `dev` and `prod` environments (app port, host & etc.).
* Tune JVM options and Tomcat options for prod.
* Develop agent for some monitoring tool (Zabix, Graphite) to provide monitoring charts: Server QPS, HTTP Errors stat, Request/Response latency percentilies, Invice Service processing stat, CPU, Memory, System Load & etc.
* Add [Swagger UI](http://swagger.io/) for REST API doc.
* Make app [Dockable](http://docker.com).