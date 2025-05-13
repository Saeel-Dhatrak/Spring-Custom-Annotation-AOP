### What is Aspect Oriented Programming ?
- In OOP we deal with real life entities, real life classes, we make object out of it, we set up relationship between classes and interfaces, there are some good patterns and good design structures to ensure that we are able to implement the solid design principles in our application.
- In OOP if we have a eCommerce application then we will have classes like User, Product, Catalog, etc. Now what if we need a `login` functionality? Ofcourse we can make a class of Login but how will you actually bind your classes with your Login class, Example how will you bind your Product class, Catalog class, etc with your Login class. Because all other classes are representing real life entities and your Login class is just a helper utility class and it does not repesent any real life entity. Then we have to create interfaces and make the mapping with the other class. That is have to create a bridge to connect these class and still they don't have any core connectivity with each other
- For all of this kind of scenario we have AOP (Aspect Oriented Programming). In AOP, we actually make sure that we are building a re-usable utility and AOP helps you to build those clases which can have cross-cutting concerns. Cross-cutting concerns means that, for example, Login class might be used in your Product class as well as in your Catalog class as well but neither it is related to to Catalog nor it is related to Product. So there is no logical relationship that exits between Login mechanism and Product or Catalog class. So these kind of utilities which can have cross-cutting concerns which directly doesn't fit in your OOP can be handled using AOP.
- Example: If we go to Controller class we can see that there is an annotation and if we go inside the @RestController we can see that it is `public @interface RestController`. Now if we want to make a ProductController then how can I make ProductController related to RestController, we can make use of the features of the RestController, but RestController can be present in CatalogController, CategoryController, etc. So it has cross-cutting concerns and it is responsible for doing things in multiple aspects of our code. And it doesn't fir directly in your normal inheritance nor in normal composition kind of a structure. And that's where tings like AOP comes into the picture. So these implementation of cross-cutting concerns comes under AOP, that you define an aspect which is kind of like re-usable and we can say aspect is nothing but a special kind of a class that is going to be re-usable in the form of annotations.
- To create our own annotation we will be needing a dependency `AspectJ`.  AOP is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. It does so by adding additional behavior to existing code without modification of the code itself. Instead, we declare separately which code to modify. AspectJ implements both concerns and the weaving of crosscutting concerns using extensions of Java programming language.
- Again AspectJ has 2 dependencies `AspectJRuntime` and `AspectJWeaver`. AspectJRuntime is going to be responsible to make sure that during runtime your annotations and everything other is available. In addition to the AspectJ runtime dependency, we’ll also need to include the aspectjweaver.jar to introduce advice to the Java class at load time.AspectJWeaver is responsible to bind our annotations to the java classes
- AspectJRuntime will help you when you want to do something after your method run or before your method run or even when your method is running. AspectJWeaver like the name suggest weaving or binding the processes where the aspects are combined with our target code.
- ```xml
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.9.22</version>
        <scope>implementation</scope>
    </dependency>

    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.22</version>
        <scope>implementation</scope>
    </dependency>
  ```
- For java 17 and above we need to have the dependency above 1.9.8
- Now we will create our own custom annotation, and wherever we will apply our annotation it is going to tell us how much amount  of time that particular function used to get executed. Just like a time monitor annotation.
- To create an annotation there are certain steps to follow.
- Class Level Annotation Example : The first step toward creating a custom annotation is to declare it using the `@interface` keyword
- ```java
    package com.example.aop;

    public @interface TimeMonitor {
    }
  ```
- Next step is we have to define some custom properties about this annotation. Like this annotation is going to be applicable on what? like on class, on method, on a field,etc.
- ```java
    package com.example.aop;

    import java.lang.annotation.ElementType;
    import java.lang.annotation.Target;

    @Target(ElementType.METHOD)
    public @interface TimeMonitor {
    }
  ```
- This `ElementType` is an enum.
- ```java
    package java.lang.annotation;

    public enum ElementType {
        TYPE,// Class, interface (including annotation interface), enum, or record
        FIELD,  // Field declaration (includes enum constants)
        METHOD,// Method declaration
        PARAMETER,// Formal parameter declaration
        CONSTRUCTOR,// Constructor declaration */
        LOCAL_VARIABLE,// Local variable declaration */
        ANNOTATION_TYPE,// Annotation interface declaration (Formerly known as an annotation type.) */
        PACKAGE,// Package declaration
        TYPE_PARAMETER,// Type parameter declaration
        TYPE_USE,// Use of a type
        MODULE,//Module declaration.
        RECORD_COMPONENT;//Record component
    }
  ```
- This enum defines where exacyly you wat this annotation to get applicable
- Next step is to provide the scope of our annotation which defines when exactly our annotation is going to be avaiable.
- ```java
    package com.example.aop;

    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimeMonitor {
    }
  ```
- Again this RetentionPolicy is a enum.
- ```java
    public enum RetentionPolicy {
        SOURCE, CLASS, RUNTIME
    }
  ```
- Here Source is something that is going to be discarded by the compiler example is @Override and we don't want our annotation to be discarded by the compiler  and we want this annotation to be available on truntime and hence we have chosen the RUNTIME as the retentionpolicy.
- Next step is to create a class in which this annotation will be used. And annotate that class with @Aspect and @Component.
- ```java
    package com.example.aop;

    import org.aspectj.lang.annotation.Aspect;
    import org.springframework.stereotype.Component;

    @Aspect
    @Component
    public class TimeMonitorAspect {

        public void logtime(){
            System.out.println("logging the time");
        }
    }
  ```
- Now we have to make sure that the `logtime` method of the `TimeMonitorAspect` class should execute whenever we attach the `@TimeMonitor` annotation but currently this method is unaware of the annotation. For this we will make use of the `@Around` annotation and provid ethe name of our custom annotation.
- ```java
    package com.example.aop;

    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.springframework.stereotype.Component;

    @Aspect
    @Component
    public class TimeMonitorAspect {

        @Around("@annotation(TimeMonitor)")
        public void logtime(){
            System.out.println("logging the time");
        }
    }
  ```
- @Around annotation is our advice and around advice means we are adding extra code both before and after our method execution. Here spring brings something called as Advices and Point cut.
- Point cut is an expression that selects one or more join points. Join Points are places in your program where a behaviour can be attached. Example before the execution of a method or after the execution of a method and so on. Example of Join Point : ethpds, field access operations or some exceptions.
- Advice : Advice represent any action taken by an aspect (cross-cutting concern) at a particular join point. Advice helps us to identify when the logic should be executed. Like BEFORE the join point execution, AFTER the joint point execution, AFTER retruning from a method, AFTER throwing from a method, AFTER the finally block, AROUND, around is actually going to surround our join point from beginning to end.
- So we have used the Advice as Around in our annotation above which means that this logtime function is going to handle both before and after execution aspect of our method where we will attach the annotation.
- Let's build the logic of our method to calculate the amount of time taken by any particular method that is annotated with our annotation
- ```java
  package com.example.aop;

  import org.aspectj.lang.annotation.Around;
  import org.aspectj.lang.annotation.Aspect;
  import org.springframework.stereotype.Component;

  @Aspect
  @Component
  public class TimeMonitorAspect {

      @Around("@annotation(TimeMonitor)")
      public void logtime(){
          
          long start = System.currentTimeMillis(); // start time of the code
          
          // execute the join point
          // Logic will be written over here
          

          long end = System.currentTimeMillis(); // end time of the code
          
          long totalExecutionTime = end - start;

          System.out.println("Total time of execution of the method is:" + totalExecutionTime + " ms...");
      }
  }
  ```
- As this method is annotated with @Around advice, this method can expect an object and this object is of type `ProceedingJointPoint`. This ProceedingJointPoint joint point is a special type of joint point that we can use in the  around advice which can help us to control when to start the execution of the particular method. Inside the ProceedingJointPoint, there is a `proceed` method is actually going to start the execution of our code and it throws and exception and we have to handle that exception using try-catch blocks
- ```java
  package com.example.aop;

  import org.aspectj.lang.ProceedingJoinPoint;
  import org.aspectj.lang.annotation.Around;
  import org.aspectj.lang.annotation.Aspect;
  import org.springframework.stereotype.Component;

  @Aspect
  @Component
  public class TimeMonitorAspect {

      @Around("@annotation(TimeMonitor)")
      public void logtime(ProceedingJoinPoint joinPoint){

          long start = System.currentTimeMillis(); // start time of the code 
          try{
              // to execute the join point
              joinPoint.proceed();
          } 
          catch (Throwable e) {
              System.out.println("Something went wrong during the execution");
          }
          finally{
              long end = System.currentTimeMillis(); // end time of the code

              long totalExecutionTime = end - start;

              System.out.println("Total time of execution of the method is:" + totalExecutionTime + " ms...");   
          }
      }
  }
  ```
- To check this let's create some fake code controller and service.
- ```java
  package com.example.aop;

  import org.springframework.stereotype.Service;

  @Service public class AopService {

      @TimeMonitor
      public String doRandomThings(){
          for(long i=0; i< 100000000L; i++){

          }
          return "Something";
      }
  }


  ////////////////////////////////////////////////////////

  package com.example.aop;

  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;

  @RestController
  public class AopController {

        @Autowired
        private AopService aopService;

        @GetMapping("/get")
        public String doRandomThings(){
            aopService.doRandomThings();
            return "Hello From AOP";
        }
  }
  ```
- And now upon running and hitting the API we will get te result in the console as : `Total time of execution of the method is:65 ms...` and we will also get the output on the browser as Hello from AOP.
- In place of println we can make use of slf4j to see the logging mechanism in a more detailed way.