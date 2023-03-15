# Connected: Is a JAVA VM implementation for a *Directed* graph network. 

## Assumption & Design:
======================
1. Connected  supports directed & undirected graph and that is driven if edge is uno or bidirectional. 
2. After graph is build ,there is no add/delete or update to underlying Graph network, so it is "thread safe" in this context
   as Connected.isConnected() only exposed to calling API.
3. If future requirements is to support mutation on Graph network, then com.bgc.poc.model.graph.Graph.java needs to:
   a) Make Adjacent Map & TransitiveClosure Map thread safe .
   b) Lock is required when TransitiveClosure is being re-constructed. 
   c) Re-construction of Adjacent Map & TransitiveClosure  map operation should be atomic, this is important so that another 
      request of data mutation shouldn't trigger race condition. so *same* lock needs to be acquired and release.
   d) ReentrantReadWriteLocks will be a good choice here.
4. Business Object (City.java) have the delegation of implementing the uniqueness if it's object by 
   overriding hashCase,equals and toString().
      
## Build

To build:
```
$ cd Connected/
Connected$ mvn clean compile package


## How to execute

cd Connected
java -jar bin/Connected-1.0.0.jar cities.txt "New York" Istanbul

## Binaries

Binaries and dependency information for Maven

```xml
<dependency>
    <groupId>com.bgc.poc</groupId>
    <artifactId>Connected</artifactId>
    <version>1.0.0</version>
</dependency>
```
