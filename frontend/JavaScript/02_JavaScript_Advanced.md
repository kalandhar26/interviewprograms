# 5. Advanced Scope
## 5.1 Nested Functions & Lexical Scoping
- Functions inside functions, accessing parent's scope.
- Functions inside functions, inheriting outer scope.
- Inner functions access outer vars by lexical (code position) scope, not call site.

```javascript
function outer() {
  const outerVar = "I'm outside";
  
  function inner() {
    const innerVar = "I'm inside";
    console.log(outerVar); // ✅ Can access parent scope
    return innerVar;
  }
  
  // console.log(innerVar); // ❌ Cannot access child scope
  return inner();
}

// Lexical scoping = determined by where function is declared
let x = "global";
function outer() {
    let x = "outer";
    function inner() { console.log(x); }  // "outer"
    inner();
}

```

## 5.2 IIFE (Immediately Invoked Function Expression)
- Self-executing anon function for private scope (ES5 pattern).

- **When to use:**
- Creating private scope (pre-modules)
- Avoiding global pollution
- One-time initialization

```javascript
// Classic IIFE
(function() {
  const privateVar = "Secret";
  console.log("Runs immediately");
})();// Runs Immediately

// Arrow IIFE
(() => {
  console.log("Modern IIFE");
})();

// With parameters
((name) => {
  console.log(`Hello ${name}`);
})("Alice");
```

## 5.3 Revealing Module Pattern
- IIFE returning object with public methods, hiding privates (ES5 modules).

- **When to use:**
- Before ES6 modules
- Creating encapsulated components
- Library patterns
l̥
```javascript
const counterModule = (function() {
  // Private state
  let count = 0;
  
  // Private function
  function logChange() {
    console.log(`Count changed to: ${count}`);
  }
  
  // Public API
  return {
    increment() {
      count++;
      logChange();
      return count;
    },
    decrement() {
      count--;
      logChange();
      return count;
    },
    getCount() {
      return count;
    }
  };
})();

counterModule.increment(); // Count changed to: 1
// counterModule.count; // undefined (private)
```

-----------

# 6. Closures

- Function that remembers its lexical scope even when executed outside.
- Function + its lexical environment (outer vars persist post-call).

```javascript
function createCounter() {
  let count = 0; // Private variable
  
  return function() {
    count++; // Remembers 'count'
    return count;
  };
}

const counter = createCounter();
console.log(counter()); // 1
console.log(counter()); // 2
console.log(counter()); // 3
```
## Practical Examples

```javascript
// 1. Data privacy
function createUser(name) {
  let password = "secret123";
  
  return {
    getName: () => name,
    validate: (input) => input === password
  };
}

// 2. Function factories
function multiplier(factor) {
  return (number) => number * factor;
}

const double = multiplier(2);
const triple = multiplier(3);
console.log(double(5)); // 10
console.log(triple(5)); // 15

// 3. Event handlers with state
function setupButton(buttonId) {
  let clicks = 0;
  
  document.getElementById(buttonId).addEventListener('click', () => {
    clicks++;
    console.log(`Button clicked ${clicks} times`);
  });
}
```
- **When to use:**
- Data encapsulation
- Function factories
- Callbacks with state
- Currying
- **When NOT to use:**
- Simple functions that don't need state
- l̥Where performance is critical (closures have overhead)

# 7. Currying
- Transforming a function with multiple arguments into a sequence of functions.
- Transform multi-arg function to chain of single-arg (closure-based).

```javascript
// Regular function
function add(a, b, c) {
  return a + b + c;
}

// Curried version
function curriedAdd(a) {
  return function(b) {
    return function(c) {
      return a + b + c;
    };
  };
}

// Arrow version
const modernCurry = a => b => c => a + b + c;

// Usage
const add5 = curriedAdd(5); // Returns a function
const add5And3 = add5(3); // Returns another function
const result = add5And3(2); // 10

// Or all at once
curriedAdd(5)(3)(2); // 10
```
- Practical Example

```javascript
// Logger with configuration
const createLogger = (prefix) => (level) => (message) => {
  console.log(`[${prefix}] ${level}: ${message}`);
};

const appLogger = createLogger("MyApp");
const errorLog = appLogger("ERROR");
const infoLog = appLogger("INFO");

errorLog("Failed to connect"); // [MyApp] ERROR: Failed to connect
infoLog("User logged in");     // [MyApp] INFO: User logged in

// Reusable validation
const validate = (min) => (max) => (value) => 
  value >= min && value <= max;

const validateAge = validate(0)(150);
const validateScore = validate(0)(100);

console.log(validateAge(25)); // true
console.log(validateScore(105)); // false
```
- **When to use:**
- Partial application
- Function composition
- Creating specialized functions
- Configuration patterns

# 8. this Keyword
- Context object. 
- Implicit: call site's object; 
- Explicit: call/apply/bind; 
- Lexical: arrow funcs; 
- l̥Default: global/undefined (strict).

## There are 4 Binding Rules:

```javascript
// 1. Default Binding (global/window)
function showThis() {
  console.log(this); // window (browser) / global (Node)
}
showThis();

// 2. Implicit Binding (object method)
const person = {
  name: "Alice",
  greet() {
    console.log(`Hello, I'm ${this.name}`);
  }
};
person.greet(); // this = person

// 3. Explicit Binding (call, apply, bind)
function introduce(lang) {
  console.log(`I code in ${lang}. I'm ${this.name}`);
}

const dev = { name: "Bob" };
introduce.call(dev, "JavaScript"); // Immediate call
introduce.apply(dev, ["Python"]); // Array arguments

const boundIntro = introduce.bind(dev); // New function
boundIntro("Java");

// 4. new Binding (constructor)
function Car(model) {
  this.model = model;
  this.drive = function() {
    console.log(`Driving ${this.model}`);
  };
}
const myCar = new Car("Tesla");
myCar.drive();

// 5. Arrow functions (lexical this)
const team = {
  members: ["Alice", "Bob"],
  teamName: "Super Coders",
  listMembers() {
    // ❌ Regular function loses context
    this.members.forEach(function(member) {
      console.log(`${member} - ${this.teamName}`); // this.teamName undefined
    });
    
    // ✅ Arrow function preserves lexical this
    this.members.forEach(member => {
      console.log(`${member} - ${this.teamName}`); // Works!
    });
  }
};
```
- **Rules Summary:**
- new → Newly created object
- call/apply/bind → Specified object
- Object method → Calling object
- Default → Global/window (undefined in strict mode)
- Arrow function → Lexical parent's this

# 9. Prototype and Prototype Inheritance

- JavaScript's prototype-based inheritance system.
- Objects link via __proto__ to prototypes for shared methods (delegation).

```javascript
// Constructor function
function Animal(name) {
  this.name = name;
}

// Add method to prototype
Animal.prototype.speak = function() {
  console.log(`${this.name} makes a sound`);
};

// Inheritance
function Dog(name, breed) {
  Animal.call(this, name); // Call parent constructor
  this.breed = breed;
}

// Set up prototype chain
Dog.prototype = Object.create(Animal.prototype);
Dog.prototype.constructor = Dog;

// Add Dog-specific method
Dog.prototype.bark = function() {
  console.log(`${this.name} barks!`);
};

const fido = new Dog("Fido", "Labrador");
fido.speak(); // From Animal prototype
fido.bark();  // From Dog prototype

// Check prototype chain
console.log(fido instanceof Dog); // true
console.log(fido instanceof Animal); // true
console.log(fido.__proto__ === Dog.prototype); // true
console.log(Dog.prototype.__proto__ === Animal.prototype); // true
```
- Modern Alternative (Classes):

```javascript
class Animal {
  constructor(name) {
    this.name = name;
  }
  
  speak() {
    console.log(`${this.name} makes a sound`);
  }
}

class Dog extends Animal {
  constructor(name, breed) {
    super(name);
    this.breed = breed;
  }
  
  bark() {
    console.log(`${this.name} barks!`);
  }
}
```
- **When to use:** 
- Library/framework development
- Understanding JavaScript internals
- Legacy code maintenance

# 10. Classes

- Syntactic sugar over prototypes (ES6+).

```javascript
class Rectangle {
  // Private field (2020+)
  #secret = "hidden";
  
  // Static property
  static description = "A shape with four sides";
  
  constructor(height, width) {
    this.height = height;
    this.width = width;
  }
  
  // Getter
  get area() {
    return this.height * this.width;
  }
  
  // Setter
  set dimensions([h, w]) {
    this.height = h;
    this.width = w;
  }
  
  // Method
  calculatePerimeter() {
    return 2 * (this.height + this.width);
  }
  
  // Static method
  static createSquare(side) {
    return new Rectangle(side, side);
  }
  
  // Private method
  #validate() {
    return this.height > 0 && this.width > 0;
  }
}

// Inheritance
class ColoredRectangle extends Rectangle {
  constructor(height, width, color) {
    super(height, width); // Call parent constructor
    this.color = color;
  }
  
  describe() {
    return `A ${this.color} rectangle`;
  }
  
  // Override method
  calculatePerimeter() {
    const perimeter = super.calculatePerimeter();
    return `${perimeter} units around`;
  }
}

// Usage
const rect = new Rectangle(5, 10);
console.log(rect.area); // 50
console.log(Rectangle.description); // Static access

const square = Rectangle.createSquare(5);
const colored = new ColoredRectangle(3, 4, "blue");
```
- **Best Practices:**
- Use private fields for internal state
- Use getters/setters for computed properties
- Prefer composition over inheritance
- Keep classes focused (Single Responsibility)

# 11. Iterators
- Objects that implement the iterator protocol.
```javascript
// Custom iterator
const countdown = {
  from: 3,
  to: 1,
  
  [Symbol.iterator]() {
    let current = this.from;
    const to = this.to;
    
    return {
      next() {
        if (current >= to) {
          return { value: current--, done: false };
        } else {
          return { done: true };
        }
      }
    };
  }
};

// Usage with for...of
for (let num of countdown) {
  console.log(num); // 3, 2, 1
}

// Spread operator works
console.log([...countdown]); // [3, 2, 1]

// Built-in iterables
const str = "hello";
const arr = [1, 2, 3];
const map = new Map([["a", 1], ["b", 2]]);
```
- **When to use:**
- Custom data structures
- Lazy evaluation
- Integration with JavaScript features (for...of, spread)

# 12. Generators
- Functions that can be paused and resumed.
- Functions with function* yielding values via yield (pausable).

```javascript
function* numberGenerator() {
  yield 1;
  yield 2;
  yield 3;
  return "done";
}

const gen = numberGenerator();
console.log(gen.next()); // { value: 1, done: false }
console.log(gen.next()); // { value: 2, done: false }
console.log(gen.next()); // { value: 3, done: false }
console.log(gen.next()); // { value: "done", done: true }

// Practical examples
// 1. Infinite sequence
function* infiniteSequence() {
  let i = 0;
  while (true) {
    yield i++;
  }
}

// 2. Async-like sync code
function* fetchUserSaga() {
  try {
    const user = yield fetch('/api/user');
    const posts = yield fetch(`/api/posts/${user.id}`);
    return { user, posts };
  } catch (error) {
    console.error("Failed:", error);
  }
}

// 3. Traversal
function* traverseTree(node) {
  yield node.value;
  if (node.left) yield* traverseTree(node.left);
  if (node.right) yield* traverseTree(node.right);
}
```
- **When to use:**
- Lazy evaluation
- Async operations (with redux-saga)
- Complex state machines
- Data stream processing

# 13. Event Loop
- JavaScript's concurrency model.
- JS runtime model: single-threaded, handles async via queue (call stack → task/microtask queue → render).

```text
Call Stack → Web APIs → Callback Queue → Event Loop → Call Stack
```

```javascript
console.log("1: Start");

setTimeout(() => {
  console.log("4: Timeout");
}, 0);

Promise.resolve()
  .then(() => {
    console.log("3: Promise");
  });

console.log("2: End");

// Output order:
// 1: Start
// 2: End
// 3: Promise (microtask)
// 4: Timeout (macrotask)
```
- **Priority:**
- Synchronous code (call stack)
- Microtasks (Promises, queueMicrotask, MutationObserver)
- Macrotasks (setTimeout, setInterval, I/O, UI rendering)

- Example with blocking:

```javascript
// ❌ DON'T block the event loop
function blockingTask() {
  // Simulating heavy computation
  let sum = 0;
  for (let i = 0; i < 1e9; i++) {
    sum += i;
  }
  return sum;
}

// ✅ Use Web Workers or chunking
async function nonBlockingTask() {
  // Break into chunks
  const chunkSize = 1000000;
  let sum = 0;
  
  for (let i = 0; i < 1e9; i += chunkSize) {
    sum += processChunk(i, Math.min(i + chunkSize, 1e9));
    await yieldToEventLoop(); // Let other tasks run
  }
  
  return sum;
}

function yieldToEventLoop() {
  return new Promise(resolve => setTimeout(resolve, 0));
}
```

# 14. Asynchronous JavaScript

- Callback → Promise → Async/Await Evolution

```javascript
// 1. Callback Hell (avoid)
getUser(id, function(user) {
  getPosts(user.id, function(posts) {
    getComments(posts[0].id, function(comments) {
      console.log(comments);
    });
  });
});

// 2. Promises
getUser(id)
  .then(user => getPosts(user.id))
  .then(posts => getComments(posts[0].id))
  .then(comments => console.log(comments))
  .catch(error => console.error(error))
  .finally(() => console.log("Done"));

// 3. Async/Await (✅ RECOMMENDED)
async function fetchData() {
  try {
    const user = await getUser(id);
    const posts = await getPosts(user.id);
    const comments = await getComments(posts[0].id);
    console.log(comments);
  } catch (error) {
    console.error(error);
  } finally {
    console.log("Done");
  }
}
```
- Promise Patterns

```javascript
// Promise.all (parallel, fails fast)
const [users, posts] = await Promise.all([
  fetchUsers(),
  fetchPosts()
]);

// Promise.allSettled (parallel, never fails)
const results = await Promise.allSettled([
  fetchFromSourceA(),
  fetchFromSourceB()
]);
const successful = results.filter(r => r.status === "fulfilled");

// Promise.race (first to finish)
const timeout = new Promise((_, reject) => 
  setTimeout(() => reject("Timeout"), 5000)
);
const data = await Promise.race([fetchData(), timeout]);

// Promise.any (first successful)
const fastest = await Promise.any([
  fetchFromCDN1(),
  fetchFromCDN2(),
  fetchFromCDN3()
]);
```
- **Error Handling Best Practices**

```javascript
// ❌ DON'T forget catch
fetchData().then(data => process(data)); // Unhandled rejection!

// ✅ DO handle errors
async function safeFetch() {
  try {
    const response = await fetch(url);
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    return await response.json();
  } catch (error) {
    // Log, retry, or fallback
    console.error("Fetch failed:", error);
    return getCachedData();
  }
}

// ✅ Cancelation with AbortController
const controller = new AbortController();
setTimeout(() => controller.abort(), 5000);

try {
  const response = await fetch(url, {
    signal: controller.signal
  });
} catch (error) {
  if (error.name === 'AbortError') {
    console.log('Request aborted');
  }
}
```

# 15. Module System
## 15.1 CJS (CommonJS - Node.js)
```javascript
// math.js
const add = (a, b) => a + b;
const multiply = (a, b) => a * b;

module.exports = { add, multiply };
// or exports.add = add;

// app.js
const math = require('./math.js');
console.log(math.add(2, 3));
```

## 15.2 ESM (ES Modules - Browser & Node.js)

```javascript
// math.js
export const add = (a, b) => a + b;
export const multiply = (a, b) => a * b;

// or default export
const calculator = { add, multiply };
export default calculator;

// app.js
import { add, multiply } from './math.js';
// or
import calc from './math.js';
// or dynamic import
const math = await import('./math.js');
```

## 15.3 Best Practices

```javascript
// 1. Named exports for utilities
export function validateEmail(email) { /* ... */ }
export function formatDate(date) { /* ... */ }

// 2. Default export for main component
export default function App() { /* ... */ }

// 3. Barrel files (index.js)
// components/index.js
export { Button } from './Button';
export { Input } from './Input';
export { Modal } from './Modal';

// 4. Tree shaking friendly
// ✅ Exports are statically analyzable
export const config = Object.freeze({
  apiUrl: 'https://api.example.com',
  timeout: 5000
});

// ❌ Can't tree shake
export default {
  method1() { /* ... */ },
  method2() { /* ... */ }
};
```

## 15.4 Modern Module Patterns

```javascript
// 1. Module with initialization
let initialized = false;

export async function init(config) {
  if (initialized) return;
  // Setup code
  initialized = true;
}

// 2. Singleton pattern
let instance;
export function getDatabase() {
  if (!instance) {
    instance = createDatabase();
  }
  return instance;
}

// 3. Private module state
const privateState = new WeakMap();

export class SecureData {
  constructor(secret) {
    privateState.set(this, { secret });
  }
  
  getSecret() {
    return privateState.get(this).secret;
  }
}
```

## 15.5 Error Handling Strategies
```javascript
// Custom error types
class ValidationError extends Error {
  constructor(message, field) {
    super(message);
    this.name = 'ValidationError';
    this.field = field;
    this.timestamp = new Date();
  }
}

// Error boundaries (React-like)
async function withErrorBoundary(operation, fallback) {
  try {
    return await operation();
  } catch (error) {
    console.error(`Operation failed:`, error);
    Sentry.captureException(error);
    return fallback?.(error) ?? null;
  }
}
```
## 15.6 Performance Optimization

```javascript
// Debounce and Throttle
function debounce(func, delay) {
  let timeout;
  return (...args) => {
    clearTimeout(timeout);
    timeout = setTimeout(() => func(...args), delay);
  };
}

// Memoization
function memoize(fn) {
  const cache = new Map();
  return (...args) => {
    const key = JSON.stringify(args);
    if (cache.has(key)) return cache.get(key);
    const result = fn(...args);
    cache.set(key, result);
    return result;
  };
}

// Virtual scrolling (concept)
function renderVisibleItems(items, containerHeight, scrollTop) {
  const itemHeight = 50;
  const start = Math.floor(scrollTop / itemHeight);
  const end = Math.min(items.length, start + Math.ceil(containerHeight / itemHeight));
  
  return items.slice(start, end).map((item, index) => ({
    ...item,
    position: (start + index) * itemHeight
  }));
}
```

## 15.7 Security Best Practices

```javascript
// 1. Sanitize user input
function sanitizeHTML(str) {
  const div = document.createElement('div');
  div.textContent = str;
  return div.innerHTML;
}

// 2. Avoid eval()
// ❌ NEVER DO THIS
eval(userInput);

// ✅ Use safer alternatives
const operations = {
  add: (a, b) => a + b,
  subtract: (a, b) => a - b
};
operations[userOperation]?.(a, b);

// 3. CSP headers
// Content-Security-Policy: default-src 'self'
```

## 15.8 Modern JavaScript Features (2023-2025)

```javascript
// Top-level await (ES2022)
const config = await fetchConfig();
console.log("Config loaded before any code runs");

// Array.at() method
const arr = [1, 2, 3];
arr.at(-1); // 3 (last element)

// Error cause (ES2022)
try {
  throw new Error("Primary error");
} catch (error) {
  throw new Error("Secondary error", { cause: error });
}

// Record and Tuple (Stage 2 proposal)
// #{} for tuples, #[] for records (immutable)
```

# 16 Testing & Debugging

```javascript
// 1. Console utilities
console.assert(value, "Value should be truthy");
console.trace("Show call stack");
console.groupCollapsed("Details");
console.groupEnd();

// 2. Performance measurement
performance.mark("start");
// Your code
performance.mark("end");
performance.measure("My Operation", "start", "end");

// 3. Debugger statements
function complexOperation() {
  debugger; // Pauses here in dev tools
  // Step through code
}
```
