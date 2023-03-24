# Object Fill With Random Value
![Build Status](https://github.com/runafterasun/objects-fill-processor/actions/workflows/gradle.yml/badge.svg)

## The problem

* You've an POJO or Entity file that need to fill with random data.
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