- Let's have a base class named `Car`
- ```java
    public class Car {
        private String carName;
        private int numberOfAirbags;
    }
  ```
- And add 2 interfaces named `PetrolEngine` and `ElectricEngine`
- ```java
    public interface PetrolEngine {
        public String getPetrol();
    }
    ///////////////////////---------------------/////////////////////////
    public interface ElectricEngine {
        public String getBattery();
    }
  ```
- Now we just need three claases which will implement these interfaces.
- ```java
    public class XUV700 extends Car implements PetrolEngine {
        @Override
        public String getPetrol(){
            return "Some Petrol";
        }
    }

    ///////////////////////---------------------/////////////////////////

    public class XUV300 extends Car implements PetrolEngine{
        @Override
        public String getPetrol(){
            return "Some Petrol";
        }
    }

    ///////////////////////---------------------/////////////////////////

    public class XUV400 extends Car implements ElectricEngine{
        @Override
        public String getBattery(){
            return "Some Battery";
        }
    }
  ```
- Here we can see that XUV700 and XUV300 class is repeating the same code that is repeating the same method `getPetrol()` in both the classes which leads to code repeatition and code repeatition means invitation to bugs.
- In this case we have to refactor the code. What we can do is we can have another interface named `EngineStrategy` in which we will have a new abstract method `getEngineType()`
- Let's now create a concrete class `PetrolEngineStrategy` which will override the `getEngineType` method and return the "petrol" as shown
- ```java
    public class PetrolEngineStrategy implements EngineStrategy {
        @Override
        public String getEngineType() {
            return "petrol";
        }
    }
  ```
- Another concrete class `BatteryEngineStrategy` which will override the `getEngineType` method and return the "battery" as shown
- ```java
    public class BatteryEngineStrategy implements EngineStrategy {
        @Override
        public String getEngineType() {
            return "battery";
        }
    }
  ```
- And now in XUV700 & XUV300 class instead if implementing the `PetrolEngine` interface, we can keep a public property of type `EngineStratergy`
- ```java
    // Old version of class XUV700
    public class XUV700 extends Car implements PetrolEngine {
        @Override
        public String getPetrol(){
            return "Some Petrol";
        }
    }

    ///////////////////////---------------------/////////////////////////

    // new version of class XUV700
    public class XUV700 extends Car {
        public EngineStrategy engine;
        
        public XUV700(PetrolEngineStrategy strategy){
            this.engine = strategy;
        }
    }
  ```
- Again same thing can be done in the `XUV300` class
- ```java
    // Old version of class XUV300
    public class XUV300 extends Car implements PetrolEngine {
        @Override
        public String getPetrol(){
            return "Some Petrol";
        }
    }

    ///////////////////////---------------------/////////////////////////

    // new version of class XUV300
    public class XUV300 extends Car {
        public EngineStrategy engine;
        
        public XUV300(EngineStrategy strategy){
            this.engine = strategy;
        }
    }
  ```
- Now our `EngineStrategy` can expose one more method `getFuel()`
- ```java
    public interface EngineStrategy {
        public String getEngineType();
        public String getFuel();
    }
  ```
- And we can overide this method in PetrolEngineStrategy and BattertEngineStrategy classes
- ```java
    public class BatteryEngineStrategy implements EngineStrategy {
        @Override
        public String getEngineType() {
            return "battery";
        }

        @Override
        public String getFuel() {
            return "30%";
        }
    }
    ///////////////////////---------------------/////////////////////////
    public class PetrolEngineStrategy implements EngineStrategy{
        @Override
        public String getEngineType() {
            return "petrol";
        }

        @Override
        public String getFuel() {
            return "30L";
        }
    }
  ```
- Now in XUV700, XUV300 class we can call this getFuel method and XUV400 can call respective methods as
- ```java
    public class XUV700 extends Car {
        public EngineStrategy engine;

        public XUV700(EngineStrategy strategy){
            this.engine = strategy;
        }

        public String getPetrol(){
            return this.engine.getFuel();
        }
    }
    ///////////////////////---------------------/////////////////////////
    public class XUV300 extends Car implements PetrolEngine{
        public EngineStrategy engine;

        public XUV300(EngineStrategy strategy){
            this.engine = strategy;
        }

        public String getPetrol(){
            return this.engine.getFuel();
        }
    }
    ///////////////////////---------------------/////////////////////////
    public class XUV400 extends Car implements ElectricEngine{
        public EngineStrategy engine;

        public XUV400(EngineStrategy strategy){
            this.engine = strategy;
        }

        public String getBattery(){
            return this.engine.getFuel();
        }
    }
  ```
