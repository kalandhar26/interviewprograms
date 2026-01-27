# useMemo

- Imagine you have a magic notebook.You solve a hard math problem.You write the answer in the notebook
- Next time you need the answer, you just read it instead of recalculating.
- In React, useMemo is like that notebook. It remembers the result of a calculation and reuses it, instead of doing it
  again on every render.
- This makes your app faster and smarter.

=====================

- useMemo is a React Hook that memorizes (caches) the result of a computation. It recomputes the value only when its
  dependencies change, helping to optimize performance by avoiding expensive calculations on every render.
- useMemo is a hook that caches the result of a computation and recomputes it only when dependencies change to improve
  performance.

```jsx
import { useState, useMemo } from "react";

function ExpensiveCalculation({ number }) {
  const [count, setCount] = useState(0);

  // Memoize heavy calculation
  const factorial = useMemo(() => {
    console.log("Calculating factorial...");
    const calculateFactorial = (n) => {
      return n <= 1 ? 1 : n * calculateFactorial(n - 1);
    };
    return calculateFactorial(number);
  }, [number]);

  return (
    <div>
      <h2>Factorial of {number} is {factorial}</h2>
      <button onClick={() => setCount(count + 1)}>Re-render {count}</button>
    </div>
  );
}

export default ExpensiveCalculation;
```

- useMemo only recalculates when number changes
- Clicking the button to increment count doesn’t redo the factorial calculation

## Important Rules

- Use useMemo for expensive calculations
- Don’t overuse; small computations don’t need memoization
- Dependencies array is mandatory
- Returns memoized value, not a function

## Complex Code Example:

```jsx
jsximport React, { useState, useMemo } from 'react';

function TodoList() {
const [todos, setTodos] = useState([]);
const [filter, setFilter] = useState('all');

const visibleTodos = useMemo(() => {
console.log('Filtering todos...'); // Only when todos or filter change
return todos.filter(todo => {
if (filter === 'completed') return todo.completed;
if (filter === 'active') return !todo.completed;
return true; // All
});
}, [todos, filter]); // Expensive filter only on changes

const totalCost = useMemo(() => {
return todos.reduce((sum, todo) => sum + (todo.cost || 0), 0);
}, [todos]); // Sum costs without recalculating unnecessarily

return (
<div>
<input placeholder="Add todo" onKeyPress={e => {
if (e.key === 'Enter') {
setTodos([...todos, { text: e.target.value, completed: false, cost: Math.random() * 10 }]);
e.target.value = '';
}
}} />
<select value={filter} onChange={e => setFilter(e.target.value)}>
<option value="all">All</option>
<option value="active">Active</option>
<option value="completed">Completed</option>
</select>
<ul>{visibleTodos.map((todo, i) => <li key={i}>{todo.text} - ${todo.cost?.toFixed(2)}</li>)}</ul>
<p>Total Cost: ${totalCost.toFixed(2)}</p>
</div>
);
}
```