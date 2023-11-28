# Object Fill With Random Value
## How to use
From maven central <br>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.objectsfill/objects-fill-processor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.objectsfill/objects-fill-processor)
![Build Status](https://github.com/runafterasun/objects-fill-processor/actions/workflows/gradle.yml/badge.svg)

```xml
<dependency>
  <groupId>ru.objectsfill</groupId>
  <artifactId>objects-fill-processor</artifactId>
  <version>x.x.x</version>
</dependency>
```

```gradle
testImplementation 'ru.objectsfill:objects-fill-processor:x.x.x'
testAnnotationProcessor 'ru.objectsfill:objects-fill-processor:x.x.x'
```
________________________________________________________________________
This project can help with generation some random information to object.

## Table of contents

[Project challenges](#project-challenges)
- [Create POJO](#create-pojo)
- [Deep recursion](#deep-recursion)
- [Collection](#collections)
- [Create own type](#create-own-type)

[Usage](#Usage)
- [Fill builder](#fill-builder)
- [Fill generic](#generic-fill)
- [Fill collection](#collection-fill)
- [Primitive array fill](#primitive-array-fill)
- [Fill raw stream](#fill-raw-stream)

[Extended Parameters](#extended-parameters)
- [Object mutation function](#object-mutation-function)
- [Another Parameters](#another-parameters)

[Annotation processor for own types](#annotation-processor)
- [Create box type](#create-box-type)
- [Create collection](#create-collection)

[What next?](#what-next)

[Support](#support)

## [Project challenges](#table-of-contents)

##### [Create POJO](#table-of-contents)
* Create POJO or Entity file that need to fill with random data.
```java
@Test
public void Test() {
    Test test = new Test();
    test.setData(...);
    test.setInteger(...);
    test.setString(...);
    test.set...(...);
}
```
##### [Deep recursion](#table-of-contents)
* Create deep recursion object that need to fill with random data.
```java
@Test
public void Test() {
    Test test = new Test();
    test.setData(...);
    test.setInteger(...);
    test.setString(...);
        
    SubTest subTest = new SubTest();
    subTest.setData(...);
    subTest.setInteger(...);
    subTest.setString(...);
        
    test.setSubTest(subTest);
}
```
##### [Collections](#table-of-contents)
* Create some collections that you need to fill.
```java
@Test
public void Test() {
    SubTest subTest = new SubTest();
    subTest.setData(...);
    subTest.setInteger(...);
    subTest.setString(...);

    List<SubSubTest> lst = new ArrayList<>();
        
    for(int i = 0;i < someSize; i++) {
        SubSubTest subsubTest = new SubSubTest();
        lst.add(subsubTest);
    }
        
    subTest.setSubSubTestLst(lst);
}
```
##### [Create own type](#table-of-contents)
* Create some different own types or collections that you need to fill.
```java
@Test
public void Test() {
    SubTest subTest = new SubTest();
    subTest.setSomeMongoIdGeneratid(new Mongo().generateId);
    subTest.setInteger(...);
    subTest.setString(...);
}

public void TestNext() {
    SubTest subTest = new SubTest();
    
    //Generate again this field type
    subTest.setSomeMongoIdGeneratid(new Mongo().generateId);
    
    subTest.setInteger(...);
    subTest.setString(...);
}
```
## [Usage](#table-of-contents)

##### [Fill builder](#table-of-contents)
* All parameters of Fill class end Extend parameters class
```java
    Fill
        .object(String.class) //class or object for fill
                .fieldParams( //parameters for some field or for all field in object
                        Extend.field("stringList") // change behavior of the field
                            //  .wrapByFunction() change behavior for all
                            .addMutationFunction(t -> "You Can do this") // you can add some new behavior without creating global annotation processor
                            .collectionSize(10) // change size for collections, stream, arrays
                            .valueLength(10)// change size of field
                        .gen(),// construct object
                        Extend
                            .wrapByFunction(t -> "You Can do this")
                        .gen()) // construct object
                .withGeneric("T", String.class) // if object with generic
                .collectionSize(10)// change global size for collections, stream, arrays
                .valueLength(10)change global size of field
                .setDeep(5) // set deep for recursion fill tree
                .excludeField("aBoolean", "aLong", "uuid") // don't fill specified filds
                .gen();// construct object
```
* For filling some object or class you can use Fill builder
```java
//For class
TestBoxClass testBoxClass = RandomValue.fill(Fill.object(TestBoxClass.class).gen());

//For object
TestBoxClass testBoxClass = RandomValue.fill(Fill.object(new TestBoxClass()).gen());
```
* Set deep for filling object. Default value equals three.
```java
First first =  RandomValue.fill(Fill.object(First.class).setDeep(2).gen());
```
* Or set size of value, array or collection size. Default value equals five.
```java
CollectionTypeTest collectionType = 
        RandomValue.fill(Fill.object(CollectionTypeTest.class)
                             .collectionSize(6)
                             .valueLength(7)
        .gen());
        
assert collectionType.collectionTypes.getStringList().size() == 6;
assert collectionType.collectionTypes.getStringList().get(0).length() == 7;
```
* If you need to exclude some fields from generator you just need to set list of excluded fields.
```java
 SimpleBoxTypeTestObj simpleBoxTypeTestObj = RandomValue
                .fill(Fill.object(SimpleBoxTypeTestObj.class)
                .excludeField(List.of("aBoolean", "aLong", "uuid"))
        .gen());

assert simpleBoxTypeTestObj.getaBoolean() == null;
assert simpleBoxTypeTestObj.getaDouble() != null;
assert simpleBoxTypeTestObj.getaLong() == null;
```

##### [Generic fill](#table-of-contents)
* Fill class or object with generic class
```java
GenericType<String, Integer> collectionType = new GenericType<>();

collectionType = RandomValue.fill(Fill.object(collectionType)
                                      .withGeneric("T", String.class)
                                      .withGeneric("K", Integer.class).gen());

assert collectionType.getGenericList() != null;
assert collectionType.getGeneric() != null;

```
##### [Collection fill](#table-of-contents)
* Create collections like List, Set and some reference type arrays. For filling you need add to Fill object type of generating object.
```java
//Set
Set<SimpleCollection> simpleCollection = new HashSet<>();
RandomValue.fillCollection(simpleCollection, Fill.object(SimpleCollection.class).gen());
//List
List<SimpleCollection> simpleCollection = new ArrayList<>();
RandomValue.fillCollection(simpleCollection, Fill.object(SimpleCollection.class).gen());
//Array
SimpleArray[] simpleArray = RandomValue.fillArray(Fill.object(SimpleArray.class).gen());

//Or with generic class
GenericType<String> collectionType = new GenericType<>();
Set<GenericType<String>> genericTypeHashSet = new HashSet<>();
RandomValue.fillCollection(genericTypeHashSet, Fill.object(GenericType.class)
                                                    .withGeneric("T", String.class).gen());
```

##### [Primitive array fill](#table-of-contents)
* Can create primitive array.  For filling you need add to Fill object type of generating object.
```java
Object[] fillInt = RandomValue.fillArray(Fill.object(int[].class).gen());
```

##### [Fill raw stream](#table-of-contents)
* You can create not closed stream with filled objects. For filling you need add to Fill object type of generating object.
```java
Stream<String> streamString = RandomValue.fillStream(Fill.object(String.class)
                                                         .gen());
List<String> list = collectionType.toList();
```

## [Extended Parameters](#table-of-contents)

##### [Object mutation function](#table-of-contents)
* You can add some UnaryFunction with wildcard that can include some actions to field. Mutation function can be added to all methods with fill object. 
```java
CollectionType collectionType = RandomValue.fill(Fill.object(CollectionType.class).collectionSize(6).valueLength(7)
                .fieldParams(
                        Extend.field("stringList")
                        .addMutationFunction(t -> "You Can do this")
                        .gen(),

                        Extend.field("intList")
                        .addMutationFunction(t -> 2)
                        .gen())
                .gen());
```
* If you don't set field, than mutation function will be used for all objects in tree.
```java
Stream<String> collectionType = RandomValue.fillStream(Fill.object(String.class)
                .fieldParams(
                        Extend.wrapByFunction(t -> "You Can do this")
                                .gen())
                .gen());
```

##### [Another Parameters](#table-of-contents)
* You can add few of parameters with delimeter. Extend class have next field
```java
  Fill
        .object(String.class) //class or object for fill
        .fieldParams( //parameters for some field or for all field in object
                Extend
                      .field("stringList") // change behavior of the field
                      .addMutationFunction(t -> "You Can do this") // you can add some new behavior without creating global annotation processor
                      .collectionSize(10) // change size for collections, stream, arrays
                      .valueLength(10)// change size of field
                      .gen(),// construct object
                Extend
                      .wrapByFunction(t -> "You Can do this") //change behavior for all
                      .gen())
   .gen()
```
## [Annotation processor](#table-of-contents)

##### [Create box type](#table-of-contents)
* Create and register own type generator
```java
@BoxType(clazz = Parent.class)
public class ParentProcessorCreateRandom implements BoxTypeFill {

    @Override
    public Object generate(Fill fillObjectParams) {
        Parent parent = new Parent();
        parent.setTen(10);
        return parent;
    }

    @Override
    public Stream<Object> fillStream(Fill fill) {
        return IntStream
                .range(0, fill.getCollectionSize())
                .mapToObj(i -> generate(fill));
        //or 
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }
}
```
##### [Create collection](#table-of-contents)
* Create and register own type collection
```java
@CollectionType(clazz = Set.class)
public class FillSetCollection implements CollectionTypeFill {

    @Override
    public Object generate(Field field, Fill fill) {
        return fillCollectionStream(field, fill)
                .filter(ss -> ss instanceof String)
                .map(dd -> ((String) dd).toUpperCase())
                .collect(Collectors.toSet());
    }

}
```

## [What next??](#table-of-contents)
* I think about split map and simple collections.
* Your issues.

## [Support](#table-of-contents)
GitHub Issues …​Create New Issue …​Pull Requests …​Create a Fork

The project is open-source; non-commercial; the license is Apache v2.0. A single person actively develops it at the moment. If you see that the latest release or commit was not many years ago, then it is worth a try to ask, open a ticket. I will react and help you as much as I can afford.

You are welcome to open tickets in GitHub if you have any question, but also for suggestions and only if you like the tool. Usually I struggle with lacking the information about how many are using my tools. Do not leave me in the dark.
