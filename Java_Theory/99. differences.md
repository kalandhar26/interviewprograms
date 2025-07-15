# Java Collections and Concurrency Comparisons (Updated for Java 21)

## HashMap vs ConcurrentHashMap

| HashMap                                           | ConcurrentHashMap                                             |
|---------------------------------------------------|---------------------------------------------------------------|
| Not Thread Safe                                   | Thread Safe                                                   |
| High Performance (no thread waiting)              | Lower Performance (some thread waiting)                       |
| ConcurrentModificationException during iteration  | No CME during iteration                                       |
| Fail-Fast Iterator                                | Fail-Safe Iterator                                            |
| Allows null keys/values                           | Doesn't allow null keys/values (NPE)                          |
| Since 1.2                                         | Since 1.5                                                     |
| **Java 21:** Enhanced with new collection methods | **Java 21:** Improved scalability with better striped locking |

## ConcurrentHashMap vs SynchronizedHashMap vs HashTable

| ConcurrentHashMap                         | SynchronizedHashMap          | HashTable                                |
|-------------------------------------------|------------------------------|------------------------------------------|
| Bucket-level locking                      | Full map locking             | Full map locking                         |
| Multiple threads can operate safely       | Only one thread at a time    | Only one thread at a time                |
| Read without lock, write with bucket lock | Full lock for all operations | Full lock for all operations             |
| No CME during iteration                   | CME during iteration         | CME during iteration                     |
| Fail-Safe Iterator                        | Fail-Fast Iterator           | Fail-Fast Iterator                       |
| No null keys/values                       | Allows null keys/values      | No null keys/values                      |
| Since 1.5                                 | Since 1.2                    | Legacy (1.0)                             |
| **Java 21:** Better parallel processing   | No significant changes       | Deprecated in favor of ConcurrentHashMap |

## ArrayList vs CopyOnWriteArrayList

| ArrayList                          | CopyOnWriteArrayList                                   |
|------------------------------------|--------------------------------------------------------|
| Not Thread Safe                    | Thread Safe (cloned copies)                            |
| CME during concurrent modification | No CME during concurrent modification                  |
| Fail-Fast Iterator                 | Fail-Safe Iterator                                     |
| Iterator supports remove()         | Iterator remove() throws UnsupportedOperationException |
| Since 1.2                          | Since 1.5                                              |
| **Java 21:** New sequence methods  | **Java 21:** Optimized memory usage                    |

## StringBuffer vs StringBuilder

| StringBuffer                        | StringBuilder                                              |
|-------------------------------------|------------------------------------------------------------|
| Synchronized methods                | Non-synchronized methods                                   |
| Thread-safe                         | Not thread-safe                                            |
| Lower performance                   | Higher performance                                         |
| Since 1.0                           | Since 1.5                                                  |
| **Java 21:** No significant changes | **Java 21:** Enhanced with new string manipulation methods |

## Java 21 Specific Updates

1. **Virtual Threads**: Both ConcurrentHashMap and CopyOnWriteArrayList benefit from Java 21's virtual threads for
   better scalability
2. **Pattern Matching**: Enhanced iteration capabilities using pattern matching
3. **Sequenced Collections**: New interfaces provide better collection access methods
4. **Deprecations**: HashTable and Vector are strongly discouraged in favor of concurrent alternatives
5. **Memory Optimization**: All concurrent collections have improved memory footprint

## Other Comparisons

### SOAP vs REST (Updated for Java 21)

| SOAP                                                | REST                                                       |
|-----------------------------------------------------|------------------------------------------------------------|
| Protocol                                            | Architectural style                                        |
| Can't use REST                                      | Can use SOAP                                               |
| Uses service interfaces                             | Uses URIs                                                  |
| JAX-WS API                                          | JAX-RS API                                                 |
| Strict standards                                    | Flexible standards                                         |
| XML only                                            | Multiple formats (JSON preferred)                          |
| **Java 21:** JAX-WS still supported but discouraged | **Java 21:** Enhanced JAX-RS with new HTTP client features |

### Intermediate vs Terminal Operations (Streams)

| Intermediate                      | Terminal                         |
|-----------------------------------|----------------------------------|
| Return Stream                     | Return non-Stream                |
| Can be chained                    | Cannot be chained                |
| Lazily loaded                     | Eagerly loaded                   |
| **Java 21:** New stream gatherers | **Java 21:** Enhanced collectors |

# Java Access Specifiers and Modifiers

| Category              | Class | Methods | Variables | Definition                                 |
|-----------------------|-------|---------|-----------|--------------------------------------------|
| **Access Specifiers** |       |         |           |                                            |
| `public`              | Yes   | No      | No        | Accessible from inside/outside the package |
| `private`             | No    | Yes     | Yes       | Accessible only within the same class      |
| `protected`           | No    | Yes     | Yes       | Accessible within package and subclasses   |
| `default`             | Yes   | Yes     | Yes       | Accessible only within the same package    |

| **Access Modifiers** | | | | |
| `instance`      | No | Yes | Yes | <ul><li>**Methods**: May/may not implement in child class (runtime
polymorphism)</li><li>**Variables**: Unique per object, accessible directly in instance areas</li></ul> |
| `static`        | No | Yes | Yes | <ul><li>**Methods**: Inherited but not overridden (compile-time
resolution)</li><li>**Variables**: Shared across all objects, accessible anywhere</li></ul> |
| `abstract`      | Yes | Yes | No | <ul><li>**Class**: Can have 0+ abstract methods</li><li>**Methods**: Must be
overridden in child class</li><li>Cannot instantiate abstract classes</li></ul> |
| `final`         | Yes | Yes | Yes | <ul><li>**Class**: Cannot be extended</li><li>**Method**: Cannot be
overridden</li><li>**Variable**: Value cannot change after initialization</li></ul> |
| `synchronized`  | No | Yes | No | Only one thread can execute the method at a time |
| `native`        | No | Yes | No | Implemented in non-Java code (C/C++) |
| `strictfp`      | Yes | Yes | No | Enforces IEEE 754 floating-point calculations |
| `transient`     | No | No | Yes | Excluded from serialization (default value saved) |
| `volatile`      | No | No | Yes | Value may change unexpectedly (thread-local copies) |

### Key Notes:

1. **`final volatile`** combination is illegal (contradictory behaviors)
2. **`abstract native`** or **`abstract strictfp`** methods are invalid
3. **`private abstract`** methods are allowed (Java 9+)
4. Java 21 enhances `strictfp` with modern floating-point standards

# Java Key Concepts Comparison

## final vs finally vs finalize()

| final                                               | finally                                    | finalize()                                   |
|-----------------------------------------------------|--------------------------------------------|----------------------------------------------|
| Modifier applicable for classes, methods, variables | Block associated with try-catch            | Method invoked by Garbage Collector          |
| **Class**: Cannot be extended                       | Performs cleanup for try block resources   | Performs object cleanup before destruction   |
| **Method**: Cannot be overridden                    | Executes always (regardless of exceptions) | Deallocates object resources                 |
| **Variable**: Cannot be reassigned                  | Guaranteed execution (unless JVM exits)    | Called just before object destruction        |
| Enforces immutability/inheritance control           | Typically used for resource closing        | Deprecated in Java 9+ (use Cleaners instead) |

## Runnable vs Callable

| Runnable                                     | Callable                                        |
|----------------------------------------------|-------------------------------------------------|
| For threads that don't return results        | For threads that return results                 |
| Single method: `void run()`                  | Single method: `Object call() throws Exception` |
| Cannot throw checked exceptions              | Can throw checked exceptions                    |
| Part of `java.lang` package                  | Part of `java.util.concurrent` package          |
| Legacy interface (since Java 1.0)            | Added in Java 5 (Concurrency API)               |
| Used with Thread class                       | Used with ExecutorService                       |
| **Java 21**: Compatible with virtual threads | **Java 21**: Enhanced Future support            |

## Serialization vs Externalization

| Serialization                              | Externalization                               |
|--------------------------------------------|-----------------------------------------------|
| Default serialization mechanism            | Customized serialization                      |
| JVM-controlled                             | Programmer-controlled                         |
| Saves all properties                       | Selective property saving                     |
| Lower performance                          | Higher performance                            |
| Marker interface (no methods)              | Requires `writeExternal`/`readExternal`       |
| Respects `transient` fields                | Ignores `transient` keyword                   |
| No-arg constructor optional                | Requires public no-arg constructor            |
| **Java 21**: Records support serialization | **Java 21**: Better performance optimizations |

### Key Notes:

1. `finalize()` is deprecated as of Java 9 (use `java.lang.ref.Cleaner`)
2. `Callable` integrates better with modern concurrency APIs
3. Records (Java 16+) have different serialization behavior
4. Externalization provides better performance for complex objects
5. Java 21 virtual threads work with both Runnable and Callable