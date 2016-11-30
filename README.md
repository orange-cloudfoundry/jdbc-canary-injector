# jdbc-canary-injector
jdbc-canary-injector is a sample spring boot application to load data (timestamp) with JDBC. After injecting a new row, the application also checks that the row inserted is consistent with previous row.

# Usage

First, generate the jar:
```
git clone https://github.com/orange-cloudfoundry/jdbc-canary-injector.git
cd jdbc-canary-injector
mvn install
```

Then, create on Cloud Foundry the database service:
```
cf create-service p-mysql 100mb jdbc-canary-injector-mysql
```

The manifest.yml used to deploy the application on Cloud Foundry also include a log service:
```
cf create-service o-logs splunk logs
```

Finaly, push the application:
```
cf push -f ./manifest.yml
cf app jdbc-canary-injector
cf logs jdbc-canary-injector
```
