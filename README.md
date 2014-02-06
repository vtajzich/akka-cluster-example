# Akka Cluster example in groovy / java

## Overwiev

It shows how to create an *Akka* cluster in groovy / java. You can start unlimited number of server nodes and clients. However you need at least 2 seed server nodes (see below how to start them).

It’s configured to run on *localhost* by default. If you want to test it over network just setup IP addresses / names correctly in *application.conf* and *MainClusterClient* (there is config string ;) ). Do not forget to turn off firewalls or open needed ports (e.g. 2551 and 2552 for seed nodes).

## Modules

1. cluster-common - contains actors and messages
2. cluster-master - server provides services
3. cluster-client - connects to server

# How to build

You need gradle in version 1.10 and Java 7
Run
> gradle build

# How to run

Each server and client part can be run multiple times to simulate multiple server nodes in cluster and clients.

## How to run server
Goto folder cluster-master and run
> gradle run -Pmaster

It’ll start node as master seed node. To start second seed node run:
> gradle run -Pslave

To run more work nodes just type
> gradle run

### App arguments
***
* Start node as first seed node (you need at least one instance)
>-Pmaster

* Start node as second seed node (you need at least one instance)
>-Pslave

* Set the node to greeter type. It means it will react only to *Greating* messages. Without *nodeType* parameter it will be set to calculator type (*Calculate* messages).
> -PnodeType=greeter


## How to run client

Go to folder cluster-client and run
> gradle run

It will start client node as *calculator* type and start sending *Calculate* messages to cluster.

To run client as *greeter* type just run

> gradle run -PclientType=greeter

