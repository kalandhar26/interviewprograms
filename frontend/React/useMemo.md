# useMemo

- Doing hard math homework every time your friend asks "What's 5 x 7?" is tiring. useMemo is like writing the answer on
  a sticky note and only recalculating if the numbers change—like if it becomes 6 x 7. It saves your brain power for fun
  stuff!

## Professional definition:

useMemo is a React Hook that memoizes a value by recomputing it only when one of its dependencies has changed. It's used
to optimize performance by avoiding expensive calculations on every render.

## PseudoCode:

```text
textDefine dependencies (e.g., [num])
If any dependency changed since last render:
Recalculate the value
Else:
Reuse the cached value
Return the value
```

## Easy Code Example:

```jsx
jsximport React, { useState, useMemo } from 'react';

function ExpensiveCounter() {
const [num, setNum] = useState(5);
const [other, setOther] = useState(0);

const doubleNum = useMemo(() => {
console.log('Calculating double...'); // Logs only when num changes
return num * 2;
}, [num]); // Depends only on num

return (
<div>
<p>Double: {doubleNum}</p>
<button onClick={() => setNum(num + 1)}>Add to num</button>
<button onClick={() => setOther(other + 1)}>Add to other (no effect)</button>
</div>
);
}
```

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