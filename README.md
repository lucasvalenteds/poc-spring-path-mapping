# POC: Spring Path Matching

It demonstrates how to define paths for endpoints using variables, regex and some caveats to those approaches.

Besides being a playground to experiment with [PathPattern](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html) implementation, we also want to verify we can implement a REST API to provide entities that contains a slash in their primary keys and how we should handle encoding in scenarios like that.

So, for example, our API should allow us to find a customer with id `abc/123` via GET request to endpoint `/customers/abc/123`.

## How to run

| Command | Description |
| :--- | :--- |
| Run tests | `./gradlew test` |
| Run application | `./gradlew run` |

