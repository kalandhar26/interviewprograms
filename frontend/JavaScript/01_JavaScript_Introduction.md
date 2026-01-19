## Running JavaScript

- JavaScript executes via inline script tags, external files, browser consoles, or Node.js.
- Use script tags for simple pages, external files for organization, consoles for testing, and Node for servers.

## Script Tag:

- Embed code directly in HTML with <script>console.log('Hello');</script>.
- Quick for prototypes; avoid for large codebases to prevent blocking parses.

```html
<!DOCTYPE html>
<html>
<body>
<script>
    console.log("Hello from inline script!");
</script>
</body>
</html>
```

## External File:

- Link via <script src="app.js"></script>.
- Use for maintainable projects;
- defer with async or defer attributes to improve load times.
- **defer:** Script executes after DOM is parsed (order preserved)
- **async:** Script executes as soon as available (order not guaranteed)

```html

<script src="app.js" defer></script>
<!-- or -->
<script src="app.js" async></script>
```

## Browser Console:

- Open DevTools (F12) and type console.log('Test').
- Ideal for debugging; don't rely on it for production code.
- For debugging, testing snippets, API exploration.

```javascript
// Press F12 → Console tab
console.log("Debugging");
console.table([{name: "John", age: 25}]);
console.time("performance");
// Your code
console.timeEnd("performance");
```

## Node.js:

- Install Node, run node app.js.
- Perfect for servers/backends;
- use npm init for projects. Tip:
- Always use node --version to check ES2025 support.

```bash
# Create file app.js
console.log("Running with Node.js");

# Execute
node app.js
```

-------------
-------------

# 2.Variables

## 2.1 Declarations (var, let, const)

- Ways to declare variables with different behaviors.

```javascript
// ❌ AVOID var (legacy)
var oldWay = "deprecated";

// ✅ Use let for mutable variables
let counter = 0;
counter = 1; // OK

// ✅ Use const for constants
const PI = 3.14159;
// PI = 3; // ❌ Error: Assignment to constant

// const with objects (reference is constant)
const user = { name: "John" };
user.name = "Jane"; // ✅ OK
// user = {}; // ❌ Error
```

## 2.2 Scope

- Scope defines variable visibility.
- Global: accessible everywhere;
- Function: within function;
- Block: within {} (e.g., if/loops, only for let/const).

```javascript
// Global scope
const globalVar = "I'm everywhere";

function test() {
  // Function scope (var, let, const)
  let functionScoped = "Inside function";
  
  if (true) {
    // Block scope (only let/const)
    let blockScoped = "Inside block";
    var hoisted = "I'm hoisted to function";
  }
  
  // console.log(blockScoped); // ❌ Error
  console.log(hoisted); // ✅ Works (var limitation)
}
```

```javascript
let global = "Global";
function func() {
    let funcScoped = "Function scope";
    if (true) {
        let blockScoped = "Block scope";
        console.log(global);     // OK
        console.log(funcScoped); // OK
    }
    console.log(funcScoped);   // OK
    // console.log(blockScoped); // Error: not in scope
}
```

## 2.3 Hoisting

- JS moves declarations (not initializations) to top of scope before execution. var hoists fully;
- let/const hoist but in "temporal dead zone" (TDZ)—access before declaration throws error.

```javascript
// ✅ Function declarations are fully hoisted
sayHello(); // Works
function sayHello() {
  console.log("Hello!");
}

// ❌ Variables are partially hoisted
console.log(myVar); // undefined (not ReferenceError)
var myVar = 5;

// ❌ let/const are hoisted but not initialized
console.log(myLet); // ReferenceError: Cannot access before initialization
let myLet = 10;
```

- Always declare variables at the top of their scope
- l̥Use const by default, let when mutation needed, avoid var

```javascript
var oldStyle = 1;  // Function-scoped, hoisted
let modern = 2;    // Block-scoped
const fixed = 3;   // Block-scoped, non-reassignable
```

# 3.DataTypes and Data Structures

## 3.1 Primitive Types (undefined, Boolean, Number, BigInt, String, Symbol) :

- Immutables passed by value. undefined: uninitialized; Boolean: true/false; Number: floats/ints; BigInt: large
  integers (ES2020+); String: text; Symbol: unique keys (ES6).

```javascript
// 7 Primitive types
let undefinedVar; // undefined
let bool = true; // Boolean
let num = 42; // Number
let big = 9007199254740991n; // BigInt
let str = "Hello"; // String
let sym = Symbol("unique"); // Symbol
let nullVar = null; // null (special primitive)

// Check type
typeof 42; // "number"
typeof null; // "object" (historical bug)
console.log(typeof undef); // "undefined"
console.log(big + num);    // Error: types mismatch
```

## 3.2 Objects and Functions, null

- Reference types stored by reference.
- null: intentional absence;
- Object: key-value pairs (mutable);
- Function: callable objects, first-class citizens.

```javascript
// Object
const person = {
  name: "Alice",
  age: 30,
  greet() {
    return `Hello, I'm ${this.name}`;
  }
};

// Function (special object)
function add(a, b) {
  return a + b;
}

// Arrays are objects
const arr = [1, 2, 3];
typeof arr; // "object"
Array.isArray(arr); // true
```

```javascript
let nul = null;
let obj = { name: "JS" };
let func = function() { return "Called!"; };

console.log(typeof nul); // "object" (quirk)
console.log(obj.name);   // "JS"
console.log(func());     // "Called!"
```

## 3.3 Collections (Array, Map, WeakMap, Set, WeakSet, Date)

- **Array:** Ordered collections
- **Set:** Unique values, membership testing
- **Map:** Key-value pairs with non-string keys
- **WeakMap/WeakSet:** Private data, memoization
- **Date:** timestamps

```javascript
// Array (ordered, indexed)
const fruits = ["apple", "banana"];
fruits.push("orange");

// Set (unique values)
const uniqueNumbers = new Set([1, 2, 2, 3]); // {1, 2, 3}

// Map (key-value pairs, keys can be any type)
const userMap = new Map();
userMap.set({id: 1}, "John");
userMap.set("email", "john@example.com");

// WeakMap (keys are objects only, doesn't prevent garbage collection)
const weakMap = new WeakMap();
const objKey = {};
weakMap.set(objKey, "private data");

// WeakSet (stores objects only)
const weakSet = new WeakSet();
weakSet.add({});

// Date
const now = new Date();
const specific = new Date("2025-12-25");
```

# 4. Core Language Features

## 4.1 Type Conversion

### 1. Explicit Conversion

Definition: Manual type change using functions like String(), Number(), Boolean(), parseInt().

```javascript
let num = 42;
let str = String(num);   // "42"
let bool = Boolean(0);   // false
let int = parseInt("10px", 10); // 10
```

### 2 Implicit Conversion

Definition: JS auto-converts (coercion) in ops, like + for concat vs add.

```javascript
console.log(1 + "2");    // "12" (string concat)
console.log(1 + true);   // 2 (true → 1)
```l̥

```javascript
// Explicit (manual)
String(123); // "123"
Number("42"); // 42
Boolean(1); // true
parseInt("10px"); // 10
+"3.14"; // 3.14 (unary plus)

// Implicit (automatic)
"5" + 2; // "52" (string concatenation)
"5" - 2; // 3 (numeric subtraction)
if ("hello") { } // true (truthy)
```

## 4.2 Equality

- == loose (coerces types); === strict (no coercion).

```javascript
// Strict equality (✅ ALWAYS USE THIS)
1 === 1; // true
1 === "1"; // false

// Loose equality (❌ AVOID)
1 == 1; // true
1 == "1"; // true (type coercion)
0 == false; // true
"" == false; // true
null == undefined; // true

// Object comparison
{} === {}; // false (different references)

console.log(1 == "1");   // true (coerced)
console.log(1 === "1");  // false
```

## 4.3 Loops

- Repeat code. while/do-while: condition-based; for: counter; for...in: object keys; for...of: iterables; break/continue
  control flow.

```javascript
// for loop
for (let i = 0; i < 5; i++) {
  if (i === 2) continue; // Skip iteration
  if (i === 4) break; // Exit loop
  console.log(i);
}

// while
let count = 0;
while (count < 3) {
  console.log(count++);
}

// do...while (executes at least once)
let x = 0;
do {
  console.log(x++);
} while (x < 3);

// for...in (object properties)
const obj = {a: 1, b: 2};
for (let key in obj) {
  console.log(key, obj[key]);
}

// for...of (iterable values)
const arr = [10, 20, 30];
for (let value of arr) {
  console.log(value);
}

// Array methods (prefer over loops)
arr.forEach(value => console.log(value));
```

## 4.4 Control Flow

- Decision-making. if/else: conditions; switch: multi-case; try/catch: error handling; throw: custom errors.

```javascript
// if...else
const score = 85;
if (score >= 90) {
  grade = "A";
} else if (score >= 80) {
  grade = "B";
} else {
  grade = "C";
}

// switch (use for multiple exact matches)
const day = "Monday";
switch (day) {
  case "Monday":
    console.log("Start week");
    break;
  case "Friday":
    console.log("Weekend!");
    break;
  default:
    console.log("Midweek");
}

// try...catch
try {
  JSON.parse("invalid json");
} catch (error) {
  console.error("Parse failed:", error.message);
} finally {
  console.log("Always executes");
}

// throw
function validateAge(age) {
  if (age < 0) throw new Error("Age cannot be negative");
  return age;
}
```

## 4.5 Functions

```javascript
// Function declaration (hoisted)
function multiply(a, b) {
  return a * b;
}

// Function expression
const divide = function(a, b) {
  return a / b;
};

// Arrow function (no 'this' binding)
const add = (a, b) => a + b;
const square = x => x * x;
const greet = () => console.log("Hello");

// Parameters and arguments
function sum(...numbers) { // Rest parameters
  return numbers.reduce((acc, n) => acc + n, 0);
}
sum(1, 2, 3, 4); // 10

// Default parameters
function greet(name = "Guest") {
  return `Hello, ${name}`;
}
```

## 4.6 Expressions and Operators

- Values/computations. Operators: + - * / % arithmetic; == === != !== comparison; && || ! logical; = += assignment; ?.
  optional chaining (ES2020).

```javascript
let a = 5, b = 3;
console.log(a + b);      // 8
console.log(a > b && b); // true (short-circuit)
let c = a ?? "default";  // 5 (nullish)
console.log(obj?.prop);  // Safe access
```

## 4.7 Functions (Declarations, Expressions, Calling, Parameters/Arguments, Scope, Arrow Functions)

- Reusable code blocks.
- Declaration: function name(){} (hoisted);
- Expression: const fn = function(){};
- Arrow: () => {} (ES6, lexical this).
- Params: inputs; args: passed values.

```javascript
function decl(x) { return x * 2; }  // Hoisted

const expr = function(y) { return y + 1; };

const arrow = (z) => z - 1;  // Concise

console.log(decl(5));  // 10
```

# 5. Destructuring and Spread/Rest

- Unpack/expand iterables/objects.

```javascript
const {name, age} = {name: "Alice", age: 30};  // Destructure
const [first] = [1,2,3];  // First elem
const all = [...[1,2], 3];  // Spread
function sum(...nums) { return nums.reduce((a,b)=>a+b); }  // Rest
```

# What is Event Loop? What is the significance of EventLoops?

- The Event Loop is a mechanism in JavaScript runtime that allows JavaScript (single-threaded) to perform non-blocking
  asynchronous operations.
- It continuously checks:
    1. Call Stack (is it empty?)
    2. Task Queue / Microtask Queue (are callbacks waiting?)
    3. When the stack is empty, it pushes queued tasks into the stack for execution.
- This mechanism enables JavaScript to handle asynchronous operations efficiently without blocking the main thread.
- It is essential for building responsive and efficient JavaScript applications, especially those that involve
  asynchronous operations like API calls, file I/O, and user interactions.
- Execute synchronous code
- Handle async operations (setTimeout, promises, API calls)
- When stack is empty:
- Execute microtasks (Promises)
- Then macrotasks (setTimeout, setInterval)

# What are Promises? What is the significance of Promises?

- A Promise represents the eventual completion or failure of an asynchronous operation.
- It has 3 states: pending, fulfilled, rejected. (fulfilled and rejected are terminal states)
- Avoids callback hell
- Makes async code readable and maintainable
- Supports chaining with .then()
- Works seamlessly with async/await

# What is Currying? What is the significance of Currying?

- Currying is a technique where a function with multiple arguments is transformed into a sequence of functions, each
  taking one argument at a time.
- Improves reusability
- Helps in functional programming
- Enables partial application
- Makes code more composable

# What is Closure? Why do we need closures?

- A Closure is formed when a function remembers and accesses variables from its lexical scope, even after the outer
  function has finished execution.
- Why do we need Closures?
- Data encapsulation
- Maintain state
- Implement private variables
- **Used in:** Callbacks, Event handlers, Currying, Module pattern

# What is ProtoType ?

- In JavaScript, Prototype is a mechanism by which objects inherit properties and methods from other objects.
- Every JavaScript object has an internal property called [[Prototype]], which points to another object.
- This forms a prototype chain.
- JavaScript does not use classical inheritance like Java or C++.
- Instead, it uses prototype-based inheritance to: Share methods efficiently, Save memory, Enable dynamic behavior
- Prototype in JavaScript is a mechanism that allows objects to inherit properties and methods from another object
  through a prototype chain.