# Product App

Simple apllication to manage products.

Features:
 - Manage categories
 - Manage products
 - Define product prices in many currencies

## Build

```console
$ mvn clean install
```

## Database configuration
It is neeeded to have a local postgres database configured with users `postgres`, password `postgres` and database name `productapp`. You can also define a different database configuring the [property file](https://github.com/Marcos/productApp/blob/master/src/main/resources/application.properties#L12).


## Running

```console
$ mvn spring-boot:run
```

## Using

After running, you can log in with user `admin` and password `admin`
