# Why equals() and hashCode() Must Be Overridden for Custom Classes as Map Keys?

- When using a custom class as keys in a Map (e.g., HashMap), it’s mandatory to override equals() and hashCode(). If not
  overridden, objects with the same content may be treated as different because their hash codes differ, or data may be
  lost if hash codes are coincidentally the same but contents differ.
- If hashCode() isn’t overridden, objects with identical content may land in different buckets, causing duplicate keys
  or failed lookups.
- If hashCode() is the same but equals() isn’t overridden, different objects may be treated as the same key, leading to
  data loss (e.g., overwriting an existing key-value pair).

# Steps to Override equals() Method

- **Check Reference Equality:** If the passed object is the same as the current object (this == obj), return true (same
  instance, no need to compare content).
- **Check for Null:** If the passed object is null (obj == null), return false (a non-null object can’t equal null).
- **Check Class Compatibility:** Ensure the passed object is an instance of the same class (using getClass() ==
  obj.getClass() or instanceof). Return false if not.
- **Type Cast:** Cast the passed object to the current class type to access its fields.
- **Compare Fields:** Compare all relevant fields (those defining equality). Start with numeric fields (faster) and use
  short-circuit operators (&&) to stop early if a mismatch is found. For object fields, check for null before
  calling equals() to avoid NullPointerException.
- **Return Result:** Return true if all fields match; otherwise, return false.

- Step 3 can use instanceof for flexibility (allowing subclasses) or getClass() for strict equality (same class only).
  Use getClass() if subclasses shouldn’t be considered equal.
- Step 5’s use of short-circuit operators and null checks is critical to avoid errors and improve performance.

# Steps to Override hashCode() Method

- **Initialize a Result Variable:** Start with a prime number (e.g., 17) to reduce the chance of collisions.
- **Use Another Prime Number for Combining:** For each field, multiply the current result by a prime number (e.g., 31)
  and add the field’s hash code.
- **Compute Field Hash Codes:** For each field used in equals(), compute its hash code (e.g., field.hashCode() for
  objects, or custom logic for primitives like int).
- **Return the Final Hash:** Combine all field hash codes into a single integer.

- All fields used in equals() must be included in hashCode() to maintain the contract.
- Prime numbers (e.g., 31) are used because they reduce hash collisions and are efficient for multiplication.

# equals() and hashCode() Contract

- **Equal Objects, Same Hash Code:** If x.equals(y) returns true, then x.hashCode() must equal y.hashCode().
- **Unequal Objects, Different Hash Codes Preferred:** If x.equals(y) returns false, x.hashCode() and y.hashCode() may
  be
  the same but should ideally be different to minimize collisions in hash-based collections like HashMap.
- *Consistent Hash Code:* During a single program execution, calling hashCode() on the same object must return the same
  integer, provided no fields used in equals() or hashCode() are modified.
- **No Consistency Across Executions:** The hashCode() value may differ between different runs of the program.

- Point 2: While unequal objects can have the same hash code (due to the limited range of int), a good hashCode()
  implementation minimizes collisions for performance.
- Point 4: The lack of requirement for consistency across executions applies to the default Object implementation but is
  irrelevant for custom classes, as you define the logic based on fields.

```java
public class Person {
    private final String name;
    private final int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // Override equals
    @Override
    public boolean equals(Object obj) {
        // Step 1: Reference equality
        if (this == obj) return true;
        // Step 2: Null check
        if (obj == null) return false;
        // Step 3: Class check
        if (getClass() != obj.getClass()) return false;
        // Step 4: Type cast
        Person other = (Person) obj;
        // Step 5: Compare fields (numeric first, with short-circuit)
        if (id != other.id) return false;
        // Step 6: Null check for object fields
        return name != null ? name.equals(other.name) : other.name == null;
    }

    // Override hashCode
    @Override
    public int hashCode() {
        // Step 1: Initialize with prime
        int result = 17;
        // Step 2: Use another prime for combining
        int prime = 31;
        // Step 3: Compute hash for each field
        result = prime * result + id; // Numeric field
        result = prime * result + (name != null ? name.hashCode() : 0); // Object field
        return result;
    }

    // Getters (for testing)
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
```

# Test Program

```java
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Person, String> map = new HashMap<>();
        Person p1 = new Person("Alice", 1);
        Person p2 = new Person("Alice", 1); // Same content
        Person p3 = new Person("Bob", 2);   // Different content

        map.put(p1, "Engineer");
        map.put(p2, "Developer"); // Should overwrite p1's value
        map.put(p3, "Manager");

        System.out.println(map.get(p1)); // Output: Developer
        System.out.println(map.get(p2)); // Output: Developer
        System.out.println(map.get(p3)); // Output: Manager
        System.out.println(map.size());  // Output: 2
    }
}
```