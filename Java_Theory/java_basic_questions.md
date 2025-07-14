# Java Interview Questions

## 1. What are the differences between "==" and ".equals()" operators in Java?

*sol:*

- Difference Between `==` and `.equals()` in Java

| Feature                  | `==` Operator                                      | `.equals()` Method                                                                 |
|--------------------------|----------------------------------------------------|------------------------------------------------------------------------------------|
| **Purpose**              | Compares references (memory addresses)             | Compares object content (based on class implementation)                            |
| **Default Behavior**     | Checks if both references point to the same object | In `Object` class, same as `==`; overridden in many classes for content comparison |
| **Applicable To**        | All objects and primitives                         | All objects (must handle null checks carefully)                                    |
| **Used For**             | Reference comparison                               | Content comparison                                                                 |
| **Example - String**     | `s1 == s2` → `false` if different objects          | `s1.equals(s2)` → `true` if content is same                                        |
| **Example - Object**     | `o1 == o2` → `false` if different objects          | `o1.equals(o2)` → `false` unless overridden                                        |
| **Override Requirement** | Cannot be overridden                               | Can (and should) be overridden to define content equality                          |
| **Null Safety**          | Safe (returns false if any side is null)           | Can throw `NullPointerException` if the calling object is null                     |

## Code Examples

```java
class String {

    public static void main(String[] args) {
        String s1 = new String("hello");
        String s2 = new String("hello");

        System.out.println(s1 == s2);       // false: different objects
        System.out.println(s1.equals(s2));  // true: same content

        Object o1 = new Object();
        Object o2 = new Object();

        System.out.println(o1 == o2);       // false: different objects
        System.out.println(o1.equals(o2));// false: same as == in Object class
    }
}
```

## 2. Why String is immutable in Java?

**Security**

- Immutability ensures that String objects are safe in sensitive contexts, such as file paths, database connection URLs,
  or user credentials.
- If String were mutable, a malicious change to a shared String could compromise security. (e.g., in a library or API)
- For example, if a String containing a file path is passed to a method, immutability guarantees that the path won’t be
  altered unexpectedly.

**Thread Safety**

- Immutable objects are inherently thread-safe because their state cannot be changed after creation.
- Multiple threads can safely use the same String object without synchronization, reducing the risk of concurrency
  issues and improving performance in multithreaded applications.

**String Pool Optimization**

- Java maintains a string pool (a special area in the heap) to store unique String literals. Immutability allows strings
  with the same content to be reused from the pool, reducing memory usage and improving performance.
- For example, String s1 = "hello"; String s2 = "hello"; results in s1 and s2 referencing the same object in the string
  pool. If String were mutable, changing one would affect the other, breaking this optimization.

**Caching Hash Codes**

- The String class caches its hash code (used by methods like hashCode()) when computed, as the content never changes.
- This improves performance in scenarios where strings are used as keys in hash-based collections like HashMap or
  HashSet.
- If String were mutable, the hash code would need to be recalculated every time, negating this optimization.

**Predictable behavior**

- Immutability ensures that operations on a String (e.g., toUpperCase(), substring()) return a new String object rather
  than modifying the original. This avoids side effects and makes code easier to reason about and debug.

## How do you write custom Immutable class?

- The class should be **final**, preventing subclasses from overriding its behavior.
- The class members should be **private & final**, private to prevent external access, final to ensure they are assigned
  only once.
- The class method should be ** only getters**,Return defensive copies for mutable fields. No setters are allowed.
- The class should have constructor for first time initialization. Make defensive copies of mutable objects.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies; // Mutable object

    // Constructor for initialization
    public ImmutablePerson(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        // Defensive copy of mutable input
        this.hobbies = new ArrayList<>(hobbies);
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for age
    public int getAge() {
        return age;
    }

    // Getter for hobbies (returns defensive copy)
    public List<String> getHobbies() {
        return Collections.unmodifiableList(new ArrayList<>(hobbies));
    }

    // No setters or methods that modify state
}
```

## 3. Explain Internal Working of HashMap?

- HashMap uses a hash table to store key-value pairs.
  **Array of Buckets**
- When we create an HashMap Object. JVM creates any array of size 16.
- Each bucket can store multiple entries (in case of hash collisions) using a linked list or balanced tree.
  **Hashing**
- When a key-value pair is added (put), the key’s hashCode() method is called to generate a hash code. sometimes,
  hashcode value is very big number, Technically it not possible to create such an big array.
- The hash code is mapped to a bucket index by using some modular function. This modular function brings down the
  value.

```aiignore
index = hash & (table.length - 1)
```

To reduce collisions, HashMap applies a supplemental hash function.

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
- This mixes the higher and lower bits of the hash code to improve distribution
  **Hash Collision**
- If the hashcode value is same for 2 keys then the bucket is turned to Linked List or Balanced Tree.
  **Java 8 Enhancement**
- If a bucket contains more than TREEIFY_THRESHOLD (8) entries, the linked list is converted to a red-black tree,
  improving worst-case performance from O(n) to O(log n).
- This is particularly effective when keys have poor hash functions or intentional hash collisions (e.g., in a
  denial-of-service attack).
- If the bucket size shrinks below UNTREEIFY_THRESHOLD (6), it reverts to a linked list to save memory.

# 4. When to Use String, StringBuffer, or StringBuilder in Java

| Type              | When to Use                                                                                                                                  |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| **String**        | - Use when the content is **fixed** and won't change frequently. <br> - Needed when **immutability** is required.                            |
| **StringBuffer**  | - Use when the content is **mutable** and changes frequently. <br> - **Thread safety** is required. <br> - **Performance is not critical**.  |
| **StringBuilder** | - Use when the content is **mutable** and changes frequently. <br> - **Thread safety is not required**. <br> - **Performance is important**. |

# 5. How to create a singleton custom class?

- The class members should be private static to hold single instance of class.
- The class constructor should be private to prevent external code from creating new instances using the new keyword.
- The class methods should be public static which returns current class object. (Typically a factory method)

```java
class Singleton {
  private static volatile Singleton instance;

  private Singleton() {
    if (instance != null) {
      throw new IllegalAccessException("Create Singleton Object from getInstance() method");
    }
  }
    
  public static getInstance() {
    if (instance == null) {
      synchronized (Singleton.class) {
        if (instance == null) {
          instance = new Singleton();
        }
      }
    }
  }
}
```
