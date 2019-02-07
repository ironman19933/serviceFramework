ServiceFramework is a library which defines the architecture for our service implementation.

In order to run the service, following are the pre-requisites:
1/ RabbitMq 
2/ Redis
3/ MySql

It has pre-defined format of how our Entities, Entries should be modelled across all the systems.
This framework provides basic CRUD operations, Caching(Redis), Async-Messaging(Rabbit), and restTemplate out of the box.

It has following major classes:
1/ BaseService
2/ BaseController
3/ BaseEntity
4/ BaseEntry
5/ BaseResponse
6/ RestTemplate - bean
7/ RabbitService

Please go through the AppConfigController and AppConfigService to understand the integration of the serviceFramework
with any springBootApplications.
