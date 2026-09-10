This is a simple directory API as a REST service. It manages users that belong to environments: you can create a user, read a user, and delete a user. It is designed to serve multiple customers, each scoped by an environment ID in the request path.

The API rate limits incoming requests. A request counter is cached in Redis so that the limit is shared across all instances of the application. If Redis is unavailable the rate limiter fails open and requests are allowed through. Redis is not required to build, test, or run the application; start it only if you want to see rate limiting take effect locally:

```shell
docker run --name directory-redis -p 6379:6379 redis
```

The web service accepts HTTP requests at http://localhost:8080. Request and response bodies are JSON.

An example create request:
```shell
curl --location -v --request POST 'http://localhost:8080/environments/11111111-1111-1111-1111-111111111111/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "jdoe",
    "email": "jdoe@example.com",
    "givenName": "John",
    "familyName": "Doe",
    "streetAddress": "123 Edinburgh Drive",
    "locality": "Edinburgh",
    "populationId": "service-population-1",
    "enabled": true
}'
```

This returns HTTP 201 with the created user in the response body, including its generated `id`. Use that id to read and delete the user:

```shell
curl -v 'http://localhost:8080/environments/11111111-1111-1111-1111-111111111111/users/22222222-2222-2222-2222-222222222222'
curl -v -X DELETE 'http://localhost:8080/environments/11111111-1111-1111-1111-111111111111/users/22222222-2222-2222-2222-222222222222'
```

## Building and running

Run the application using: `./mvnw clean spring-boot:run`

Run the tests using `./mvnw test` (no Redis required)

The server po   rt is set to `8080`, in the case of a conflict, this can be modified by adjusting `server.port` in `application.properties`
