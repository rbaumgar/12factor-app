# Helloworld

The slides of this presentation are available at <https://docs.google.com/presentation/d/1LR7PVLiW8kxr8HABvCbvEATL52-SYp1eiZ8M1MSGH8E/edit?usp=sharing>

To use the scripts, specify the OPENSHIFT_IP environment variable.

Example: export OPENSHIFT_IP=35.185.41.87;

Build and Deploy helloworld-service locally
------------------------------------------

1. Open a command prompt and navigate to the root directory of this microservice.
2. Type this command to build and execute the service:

        mvn clean compile exec:java

3. The application will be running at the following URL: <http://localhost:8080/api/hello/AnyName>
