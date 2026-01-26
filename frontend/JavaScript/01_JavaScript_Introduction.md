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