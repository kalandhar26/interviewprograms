# Closures

- A closure is a function that remembers and can access variables from its outer (lexical) scope even after the outer
  function has finished execution.
- Function + its lexical environment
- Variables are not copied, they are remembered
- Memory stays alive because inner function needs it

### Summary

- Inner function remembers outer variables
- Even after outer function ends
- Used for privacy, factories, async

```javascript
function counter() {
  let count = 0;

  return {
    inc() { count++; },
    get() { return count; }
  };
}
```

- Cannot access count directly
- Safe data

## What is Currying? What is the significance of Currying?

- Currying is a technique where a function with multiple arguments is transformed into a sequence of functions, each
  taking one argument at a time.
- Breaking a function that takes many arguments into many functions that take one argument each.
- Currying = one argument at a time

```javascript
// Normal Function
function add(a, b) {
  return a + b;
}

add(2, 3); // 5

// Curried Function
function add(a) {
  return function(b) {
    return a + b;
  };
}

add(2)(3); // 5

```

## this keyword

- In JS, this refers Who is calling me?. 'this' changes based on how a function is called.
- JavaScript decides this in this order :
    1. new binding
    2. explicit binding
    3. implicit binding
    4. default binding
    5. Lexical binding (arrow) overrides everything.

### Implicit Binding

- When a function is called with a dot notation, the object before the dot is the 'this' context.
- Implicit binding happens when a function is called as a method of an object. In this case, this refers to the object
  before the dot.

```javascript
const person = {
  name: "Ali",
  sayName() {
    console.log(this.name);
  }
};

person.sayName(); // Ali
```

- this = person

### Explicit Binding

- We force who this is. Explicit binding is when we manually set the value of this using call(), apply(), or bind().

```javascript
function greet() {
  console.log(this.name);
}

const user = { name: "Baba" };

greet.call(user);   // Baba
greet.apply(user);  // Baba
const bound = greet.bind(user);
bound();            // Baba
```

### new Binding

- When using constructor. When a function is called with the new keyword, this refers to the newly created object.

```javascript
function Person(name) {
  this.name = name;
}

const p1 = new Person("Ali");
console.log(p1.name); // Ali
```

- this = new object p1

### Lexical Binding

- Arrow functions do NOT have their own this. Arrow functions do not have their own this. They inherit this from their
  surrounding lexical scope.

```javascript
const obj = {
  name: "Ali",
  sayName: () => {
    console.log(this.name);
  }
};

obj.sayName(); // undefined
```

- Arrow takes this from outside (global), not obj.

### Default Binding

- When a function is called without any binding, this refers to the global object. In strict mode, this is undefined.
- Default binding occurs when a function is called normally. In non-strict mode, this refers to the global object; in
  strict mode, it is undefined.

- In JavaScript, the value of this is determined by how a function is called. It can be bound implicitly, explicitly,
  using the new keyword, lexically in arrow functions, or default to the global object/undefined.

===========================

## Prototype and Prototype Inheritance

- Prototype is a hidden object that JavaScript uses to share properties and methods. Every object in JavaScript has a
  prototype.
- A prototype is an object from which other objects can inherit properties and methods.

```javascript
const parent = {
  greet() {
    console.log("Hello!");
  }
};

const child = {};
child.__proto__ = parent;

child.greet(); // Hello!
```

- child does not have greet()
- JavaScript looks in prototype (parent)

### Prototype Chain

- When JS looks for a property:
  1️⃣ Object itself
  2️⃣ Its prototype
  3️⃣ Prototype’s prototype
  4️⃣ null → stop
- This is called Prototype Chain.

### Prototype Inheritance

- Prototype inheritance is the mechanism by which JavaScript objects inherit properties and methods from their prototype
  objects.
- Prototype inheritance allows objects to access properties and methods of another object via the prototype chain.

```javascript
function Person(name) {
  this.name = name;
}

Person.prototype.sayName = function () {
  console.log(this.name);
};

const p1 = new Person("Ali");
p1.sayName(); // Ali
```

=======================================

## ES6 Class

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }

  greet() {
    console.log("Hi " + this.name);
  }
}

const p = new Person("Baba");
p.greet();
```

- Behind the scenes → Prototype is used.

