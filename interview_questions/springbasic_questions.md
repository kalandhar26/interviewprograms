1. Why Constructor Injection is preferred over Setter Injection?

- Construction Injection is enables Immutability and ThreadSafe as it enables final fields. objects become immutable after construction. prevents accidental modification after initilization.
- Constructor guarantees all dependencies are provided. Constructor injection fails fast if circular dependencies exist