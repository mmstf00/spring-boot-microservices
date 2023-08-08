### Start the PostgresSQL, PgAdmin, Keycloak, Zipkin, Zookeeper and the Broker using docker 🐋

```
docker compose up -d
```

### Connect to PostgreSQL via IntelliJ

1. Properties
    - `Host`: **localhost**
    - `Port`: **5431**
    - `Password`: **1234**
2. When using PostgreSQL the databases has to be created manually at the start:
    - `inventory-service`
    - `order-service`
    - `product-service`

### Start all the services manually with `docker` profile

### Keycloak setup

1. Open `localhost:8181`
    - username: `admin`
    - password: `admin`
2. Create a new Realm
3. Create client secret
    - Client ID: `spring-cloud-client`
    - Client authentication: `on`
    - Authentication flow: `off`
    - Direct access grants: `off`
    - Service accounts roles: `on`
    - Click `Save`, this will create a client secret
4. Get the Client Secret from `Clients` -> `Credentials` tab
5. Go to `Realm Settings`
    - Click `OpenID Endpoint Configuration`
    - Copy `issuer` value: `http://localhost:8181/realms/master`

### Execute HTTP requests with Postman

1. Add the OAuth config
    - Open `Authorization`
    - Select Type `OAuth 2.0`
    - Configure New Token
        - Token Name: `token`
        - Grant Type: `Client Credentials`
        - Access Token URL: The `token_endpoint` value from OpenId Configuration
        - Client ID: `spring-cloud-client`
        - Client Secret: Get from 3rd of Keycloak setup.
        - Click `Get New Access Token` and `Use Token`
        - Send the Request

## Available endpoints

|     Name      |        Address        |
|:-------------:|:---------------------:|
| Eureka Server | http://localhost:8761 |
|   Keycloak    | http://localhost:8181 |
|    Zipkin     | http://localhost:9411 |

## Notes
- It takes around 30 seconds for eureka server to register the services after starting
- After restarting a service, new token should be created