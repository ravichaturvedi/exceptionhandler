# Retrier

Java library to simplify exception handling of any function or code block (piece of code wrapped in lambda expression) without 
changing the execution flow from developer perspective. They can rely on the return value of the function/code block in exception handler, etc..

## Getting Started

Add the following `maven` dependency to your project `pom.xml`:

```xml
<dependency>
    <groupId>io.github.ravichaturvedi.exceptionhandler</groupId>
    <artifactId>exceptionhandler</artifactId>
    <version>0.1</version>
</dependency>
```

```java
import static io.github.ravichaturvedi.exceptionhandler.Wrap.*;
import static io.github.ravichaturvedi.exceptionhandler.Fallback.*;
import static io.github.ravichaturvedi.exceptionhandler.Swallow.*;

wrap(() -> {
    // do something
    throw new Exception("");
});

int value = fallback(() -> {
    throw new Exception("");
}, to(2))

swallow(() -> {
    throw new Exception("");
}, System.out::println)
```

## Background
To handle exception we generally wrap the code in try/catch block and do stuff accordingly, which leads to different flow of control both visually in the code and in programmer minds.
However, this library simplifies those execution paths using lamda expression without changing the developer code flow.


## Usages 
1. Wrap: 
wraps the function/code block to throw runtime expression (generally requires so we need not to handle exception at every method.)

```java
import static io.github.ravichaturvedi.exceptionhandler.Wrap.*;

wrap(() -> {
    // do something
    throw new Exception("");
});

// throws RuntimeException
wrap(() -> {
    // do something
    throw new RuntimeException("");
}, using(e -> new IllegalArgumentException(e)));

// throws IllegalArgumentException
wrapAll(() -> {
    // do something
    throw new RuntimeException("");
}, e -> new IllegalArgumentException(e));
```

2. Fallback:
fallback to some other functionality to load data if the primary functionality didn't worked.

```java
import static io.github.ravichaturvedi.exceptionhandler.Fallback.*;

// Fallback to some value if the actual code throws exception
int value = fallback(() -> {
    // do something
    throw new Exception("");
}, to(2));

// Fallback to a supplier if actual code throws Exception
int value = fallback(() -> {
    // do something
    throw new Exception("");
}, to(() -> 2));

// Fallback to Exception function if actual code throws Exception.
int value = fallback(() -> {
    // do something
    throw new Exception("");
}, to(e -> 2));
```

3. Swallow:
swallow the exception thrown by some piece of code.

```java
import static io.github.ravichaturvedi.exceptionhandler.Swallow.*;

// Fallback to some value if the actual code throws exception
swallow(() -> {
    // do something
    throw new Exception("");
}, with(System.out::println));

```