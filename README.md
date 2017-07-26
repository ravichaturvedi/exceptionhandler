# ExceptionHandler

![alt text](https://travis-ci.org/ravichaturvedi/exceptionhandler.svg?branch=master "Build Status")

Java library to simplify exception handling of any function or code block (piece of code wrapped in lambda expression) without 
changing the execution flow from developer perspective. They can rely on the return value of the function/code block in exception handler, etc..

## Getting Started

Add the following `maven` dependency to your project `pom.xml`:

```xml
<dependency>
    <groupId>io.github.ravichaturvedi</groupId>
    <artifactId>exceptionhandler</artifactId>
    <version>0.2</version>
</dependency>
```

```java
import static io.github.ravichaturvedi.exceptionhandler.Wrap.*;
import static io.github.ravichaturvedi.exceptionhandler.Fallback.*;
import static io.github.ravichaturvedi.exceptionhandler.Swallow.*;
import static io.github.ravichaturvedi.exceptionhandler.Cleanup.*;

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

cleanup(() -> {
       throw new Exception("");
   }, with(e -> {
    // do something to cleanup exception
))}

```

## Background
To handle exception we generally wrap the code in try/catch block and do stuff accordingly, which leads to different flow of control both visually in the code and in programmer minds.
However, this library simplifies those execution paths using lamda expression without changing the developer code flow.


## Usages
Specifying below some typical exception handling use cases covered by the ExceptionHandler.

Assuming following method is in place:

```java
<T> T foo(T) throws Exception {
    throw new Exception("foo exception");
}

void bar() throws Exception {
    throw new Exception("bar exception");
}
```

1. **Wrap**: 
wraps the function/code block to throw runtime expression (generally requires so we need not to handle exception at every method.)

```java
import static io.github.ravichaturvedi.exceptionhandler.Wrap.*;

// Wrapping a callable
wrap(this::foo);

// wrapping a runnable
wrap(this::bar);

// wrapping a callable using exception function.
wrap(this::foo, using(e -> new IllegalArgumentException(e)));
wrap(using(e -> new IllegalArgumentException(e)), this::bar);
```

2. **Fallback**:
fallback to some other functionality to load data if the primary functionality didn't worked.

```java
import static io.github.ravichaturvedi.exceptionhandler.Fallback.*;

// Fallback to some value if the actual code throws exception
int value = fallback(this::foo, to(2));

// Fallback to a supplier if actual code throws Exception
int value = fallback(this::foo, to(() -> 2));
int value = fallback(to(() -> 2), this::foo);

// Fallback to exception function if actual code throws Exception.
int value = fallback(this::foo, to(e -> 2));
int value = fallback(to(e -> 2), this::foo);
```

3. **Swallow**:
swallow the exception thrown by some piece of code.

```java
import static io.github.ravichaturvedi.exceptionhandler.Swallow.*;

// Swalling the exception without logging
swallow(this::foo);
swallow(this::bar);

// Swallowing the exception from some piece of code using the provided exception logger.
swallow(this::foo, usingLogger(System.out::println));
swallow(this::bar, usingLogger(System.out::println));

swallow(usingLogger(System.out::println), this::foo);
swallow(usingLogger(System.out::println), this::bar);

```

4. **Cleanup**:
Perform cleanup after some piece of code throws Exception.

```java
import static io.github.ravichaturvedi.exceptionhandler.Cleanup.*;
// Swallowing the exception from some piece of code using the provided exception logger.
cleanup(this::foo, with(e -> {
    // do something to cleanup exception
))}

cleanup(this::bar, with(e -> {
    // do something to cleanup exception
))}

cleanup(with(System.out::println), this::foo);
cleanup(with(System.out::println), this::bar);

```