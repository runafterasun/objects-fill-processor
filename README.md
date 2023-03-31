# Object Fill With Random Value
![Build Status](https://github.com/runafterasun/objects-fill-processor/actions/workflows/gradle.yml/badge.svg)

This project can help with generation some random information to object.

[How to use](#how-to-use) 

[The problems](#the-problem)
- [Create POJO](#create-pojo)
- [Deep recursion](#deep-recursion)
- [Fill collection](#fill-collection)
- [Create own type](#create-own-type)

[Usage](#Usage)
- [Fill builder](#fill-builder)
- [Fill generic](#generic-fill)
- [Fill collection](#collection-fill)

[Annotation processor for own types](#annotation-processor)
- [Create box type](#create-box-type)
- [Create collection](#create-collection)

[What next?](#what-next)

[Support](#support)

## How to use
(Maven central will be later)
1. Download to IDE
2. Use gradle command gradle publishMavenJavaPublicationToMavenLocal
3. Add to dependency. Will work without annotation processor, but you can't create own types.
```gradle
testImplementation 'objects.fill:objects-fill-processor:0.0.1'
testAnnotationProcessor 'objects.fill:objects-fill-processor:0.0.1'
```
4. Read doc

## The problems

##### Create POJO 
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
##### Deep recursion
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
##### Fill collection
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
##### Create own type
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
## Usage

##### Fill builder
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
                             .valueLength(7).gen());
        
assert collectionType.collectionTypes.getStringList().size() == 6;
assert collectionType.collectionTypes.getStringList().get(0).length() == 7;
```
* If you need to exclude some fields from generator you just need to set list of excluded fields.
```java
 SimpleBoxTypeTestObj simpleBoxTypeTestObj = RandomValue
                .fill(Fill.object(SimpleBoxTypeTestObj.class)
                .excludeField(List.of("aBoolean", "aLong", "uuid")).gen());

assert simpleBoxTypeTestObj.getaBoolean() == null;
assert simpleBoxTypeTestObj.getaDouble() != null;
assert simpleBoxTypeTestObj.getaLong() == null;
```

##### Generic fill
* Fill class or object with one generic class
```java
GenericType<String> collectionType = new GenericType<>();

collectionType = RandomValue.fill(Fill.object(collectionType)
                                      .withGeneric(String.class).gen());

assert collectionType.getGenericList() != null;
assert collectionType.getGeneric() != null;

//or you can set as an object for generator root test class

public class GenericTypeTest {

    GenericType<String> collectionTypes = new GenericType<>();

    @Test
    public void fillGenericObject() {
        GenericTypeTest collectionType = RandomValue.fill(Fill.object(this).gen());

        assert collectionType.collectionTypes.getGeneric() != null;
        assert collectionType.collectionTypes.getGenericList().size() == 5;
    }
}
```
##### Collection fill
* Create collections like List, Set and some Arrays
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
                                                    .withGeneric(String.class).gen());
```

## Annotation processor

##### Create box type
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
##### Create collection
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

## What next?? 
* Collection annotation processor is difficult for overriding.
* I think about split map and simple collections.
* Your issues.

## Support
GitHub Issues …​Create New Issue …​Pull Requests …​Create a Fork

The project is open-source; non-commercial; the license is Apache v2.0. A single person actively develops it at the moment. If you see that the latest release or commit was not many years ago, then it is worth a try to ask, open a ticket. I will react and help you as much as I can afford.

You are welcome to open tickets in GitHub if you have any question, but also for suggestions and only if you like the tool. Usually I struggle with lacking the information about how many are using my tools. Do not leave me in the dark.