# useCallback

- Imagine you have a toy robot that can perform tasks.You tell it, “Do this task.”
- If you repeat the same instructions, the robot wastes time thinking again.
- Instead, you give it a pre-written instruction card. Every time, it just reads the card and performs the task
  instantly.
- In React, useCallback is like that pre-written instruction card.
- It remembers a function and only recreates it when needed, so the app doesn’t waste time on unnecessary re-renders.
- useCallback is a React Hook that memorizes a function. It returns a memoized version of the callback function that
  only changes if its dependencies change.
- It is useful to prevent unnecessary re-renders of child components that rely on function props.
- useCallback is a hook that memorizes a function to prevent unnecessary re-creations and re-renders of child components.

##

- increment function is memoized
- Button won’t re-render unnecessarily if parent re-renders, because increment reference stays the same

```jsx
import { useState, useCallback } from "react";

function Button({ handleClick }) {
  console.log("Button rendered");
  return <button onClick={handleClick}>Click Me</button>;
}

function App() {
  const [count, setCount] = useState(0);

  const increment = useCallback(() => {
    setCount((prev) => prev + 1);
  }, []); // function stays same unless dependencies change

  return (
    <div>
      <h2>Count: {count}</h2>
      <Button handleClick={increment} />
    </div>
  );
}

export default App;
```

## Important Rules (Interview Must-Know)

- useCallback(fn, deps) → memoizes fn
- Use when passing functions to child components
- Avoid using it for functions that don’t affect performance
- Dependencies array is mandatory

## Complex Code Example:

```jsx
jsximport React, { useState, useCallback } from 'react';

const ChildComponent = React.memo(({ onAddTodo, onDeleteTodo, todos }) => {
console.log('Child rendered');
return (
<div>
{todos.map((todo, i) => (
<div key={i}>
{todo.text}
<button onClick={() => onDeleteTodo(i)}>Delete</button>
</div>
))}
<button onClick={onAddTodo}>Add Todo</button>
</div>
);
});

function TodoApp() {
const [todos, setTodos] = useState([]);
const [filter, setFilter] = useState('');

const addTodo = useCallback((text = 'New Todo') => {
setTodos(prev => [...prev, { text, id: Date.now() }]);
}, []); // Stable: no deps

const deleteTodo = useCallback((id) => {
setTodos(prev => prev.filter(todo => todo.id !== id));
}, []); // Stable

const filteredTodos = useCallback(() => {
return todos.filter(todo => todo.text.includes(filter));
}, [todos, filter]); // Updates only when needed

return (
<div>
<input value={filter} onChange={e => setFilter(e.target.value)} placeholder="Filter todos" />
<ChildComponent
todos={filteredTodos()}
onAddTodo={addTodo}
onDeleteTodo={deleteTodo}
/>
<button onClick={() => setFilter('')}>Clear Filter</button> {/* Won't re-render Child unnecessarily */}
</div>
);
}
```

