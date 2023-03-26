# Object Fill With Random Value
![Build Status](https://github.com/runafterasun/objects-fill-processor/actions/workflows/gradle.yml/badge.svg)

[The problem](#the-problem)
- [Create POJO][1]
- [Deep recursion][2]
- [Fill collection][3]

[Usage](#Usage)

## The problem


[1]: #first
You've an POJO or Entity file that need to fill with random data.
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
[2]: #second
* You've got deep recursion object that need to fill with random data.
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
[3]: #third
* You've got some collections that you need to fill.
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
* You've got some different types or collections that you need to fill.
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

* Fill some object or class you can use Fill builder
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

* Create and register own type generator
```java
@BoxType
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
    }

    @Override
    public Class<?> getClazz() {
        return Parent.class;
    }
}
```

* Create and register own type collection
```java
@CollectionType
public class FillSetCollection implements CollectionTypeFill {

    @Override
    public Object generate(Field field, Fill fill) {
        return fillCollectionStream(field, fill)
                .filter(ss -> ss instanceof String)
                .map(dd -> ((String) dd).toUpperCase())
                .collect(Collectors.toSet());
    }

    @Override
    public Class<?> getClazz() {
        return Set.class;
    }

}
```