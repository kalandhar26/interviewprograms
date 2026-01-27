# useState

- Imagine you have a notebook. You write today’s score. Tomorrow the score changes. You erase and write a new score
  That notebook is memory.
- In React, useState is that memory notebook for a component. When the value in the notebook
  changes.
- React: Remembers the new value , Updates the screen automatically. No refresh. No reload. Just smooth update.

- useState is a React Hook that allows function components to store and manage stateful data. It returns an array
  containing the current state value and a setter function to update that state, triggering a re-render.
- useState is a React Hook that lets function components manage local state and trigger re-renders when the state
  changes.

```jsx
import { useState } from "react";

function Counter() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(count + 1)}>
        Increase
      </button>
    </div>
  );
}

export default Counter;
```

```js
const [count, setCount] = useState(0);
```

- count → current state
- setCount → update function
- 0 → initial value

### Important Rules

- Don’t update state directly ( count = count +1)
- Always use setter ( setCount(count+1))
- Functional Update : When new value depends on old value

```jsx
setCount(prevCount => prevCount + 1);
```

- Prevents bugs due to async updates.