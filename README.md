### Start the Keycloak, Zipkin, Zookeeper and the Broker using docker 🐋

```
docker compose up -d
```

### Start all the services manually

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