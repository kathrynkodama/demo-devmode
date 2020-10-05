# liberty-arquillian servlet demo

Jakarta EE 8: As-is scenario

Simple servlet returning "Hello Jakarta EE 8!" with liberty-arquillian configured integration test.

1. `mvn liberty:dev`, ensure `/hello` endpoint is accessible
2. Stop dev mode
3. `mvn liberty:configure-arquillian`
4. `mvn failsafe:integration-test`, remote liberty server should start, test should pass