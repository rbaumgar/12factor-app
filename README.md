# Helloworld

The slides of this presentation are available at <https://docs.google.com/presentation/d/1P7Tn_N9FjAkC7oyU9kfo2pMzbaCaSjzB_5sAp1N3ysI/edit?usp=sharing>

To use the scripts, specify the OPENSHIFT_IP environment variable.
Specify DEMOTEXT environment varibale.

Example: export OPENSHIFT_IP=35.185.41.87; export DEMOTEXT="My12FactorApp";

For setup run: ./00_setup.sh
Whenever you see "Demo" on the slide start corespondig sh. 01 does not have a sh, just open the src directory.

Build and Deploy helloworld-service locally
------------------------------------------

1. Open a command prompt and navigate to the root directory of this microservice.
2. Type this command to build and execute the service:

        mvn clean compile exec:java

3. The application will be running at the following URL: <http://localhost:8080/api/hello/AnyName>