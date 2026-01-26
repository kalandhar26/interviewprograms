# 2.Variables

## 2.1 Declarations (var, let, const)

- Ways to declare variables with different behaviors.
- *var* : function-scoped, hoisted to top of function, can be re-declared. 'var' is a old way to create variable.
- *let* : block-scoped, hoisted to top of block, cannot be re-declared. 'let' is a modern way to create variable whose
  value can change.
- *const* : block-scoped, hoisted to top of block, cannot be re-declared or re-assigned. 'const' is a modern way to
  create variable whose value cannot change.

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

- *Scope* defines variable visibility. Where a variable is visible and usable.
- **Global:** accessible everywhere; A variable declared outside all blocks can be used everywhere.
- **Function:** within function; A Variable declared within a function is only accessible within that function.
- **Block:** within {} (e.g., if/loops, Used with only let/const). A Variable exists only inside {} block.

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

- Javascript moves declarations (not initializations) to top of scope before execution. var hoists fully;
- let/const hoist but in "temporal dead zone" (TDZ)—access before declaration throws error.
- Let and const are hoisted but not usable before declaration.Variable is in TDZ (Javascript says wait till I created
  it.)

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