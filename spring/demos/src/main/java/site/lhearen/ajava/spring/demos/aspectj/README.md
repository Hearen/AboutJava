Maven project example with AspectJ
=====================

Project example with [AspectJ](https://eclipse.org/aspectj/) and [Maven](http://maven.apache.org).

Load-time weaving

### Run ###
```
mvn compile exec:exec
```

### Create executable jar with dependencies ###
```
mvn package assembly:single
```

### Expected Output

```
before execution(public void site.lhearen.aspectj.Foo.foo())
foo
within advice body
within advice body
testing now...
leaving advice body
leaving advice body
```

### Follow-up
There is a [discussion](https://stackoverflow.com/questions/49786603/java-advice-annotation-will-run-twice/49826419#49826419) about why there are two different calls for the same aspect advice.
