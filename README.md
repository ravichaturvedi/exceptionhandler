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
import static io.github.ravichaturvedi.exceptionhandler.Wrapper.*;
import static io.github.ravichaturvedi.exceptionhandler.Fallbacker.*;
import static io.github.ravichaturvedi.exceptionhandler.Handler.*;

wrap(() -> {
    // do something
    throw new Exception("");
});

int value = fallback(() -> {
    throw new Exception("");
}, 2)

handle(() -> {
    throw new Exception("");
}, System.out::println)
```

## Background
To handle exception we generally wrap the code in try/catch block and do stuff accordingly, which leads to different flow of control both visually in the code and in programmer minds.
However, this library simplifies those execution paths using lamda expression without changing the developer code flow.


## Usages 
1. Wrapper: 
wraps the function/code block to throw runtime expression (generally requires so we need not to handle exception at every method.)

```java
import static io.github.ravichaturvedi.exceptionhandler.Wrapper.*;

wrap(() -> {
    // do something
    throw new Exception("");
});

// throws RuntimeException
wrap(() -> {
    // do something
    throw new RuntimeException("");
}, e -> new IllegalArgumentException(e));

// throws IllegalArgumentException
wrapAll(() -> {
    // do something
    throw new RuntimeException("");
}, e -> new IllegalArgumentException(e));
```

2. Fallbacker:
fallback to some other functionality to load data if the primary functionality didn't worked.

```java
import static io.github.ravichaturvedi.exceptionhandler.Fallbacker.*;

// Fallback to some value if the actual code throws exception
int value = fallback(2, () -> {
    // do something
    throw new Exception("");
});

// Fallback to a supplier if actual code throws Exception
int value = fallback(() -> {
    // do something
    throw new Exception("");
}, () -> 2);

// Fallback to Exception function if actual code throws Exception.
int value = fallback(() -> {
    // do something
    throw new Exception("");
}, e -> 2);
```

3. Handler:
handle the exception thrown by some piece of code.

```java
import static io.github.ravichaturvedi.exceptionhandler.Handler.*;

// Fallback to some value if the actual code throws exception
handle(() -> {
    // do something
    throw new Exception("");
}, e -> {});

```