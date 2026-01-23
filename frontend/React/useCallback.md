# useCallback

- Every time your sibling asks you to high-five, you don't need to learn a whole new high-five move—useCallback
  remembers your favorite high-five style and only updates it if you add a spin or something new. It keeps things fast
  so your game doesn't lag from re-learning moves.

## Professional definition:

- useCallback is a React Hook that returns a memoized callback function that only changes if one of its dependencies has
  changed. It's used to optimize child components that rely on function props, preventing unnecessary re-renders.

## PseudoCode:

```text
textDefine function and dependencies
If dependencies changed:
Create new function instance
Else:
Return the same cached function
Pass the memoized function as prop
```

## Easy Code Example:

```jsx
jsximport React, { useState, useCallback } from 'react';

function List({ items, onItemClick }) {
console.log('List rendered'); // Only logs when items change, not onItemClick
return (
<ul>
{items.map((item, i) => (
<li key={i} onClick={() => onItemClick(item)}>{item}</li>
))}
</ul>
);
}

function App() {
const [items, setItems] = useState(['apple', 'banana']);
const [count, setCount] = useState(0);

const handleClick = useCallback((item) => {
console.log('Clicked:', item);
}, []); // No deps: same function always

return (
<div>
<List items={items} onItemClick={handleClick} />
<button onClick={() => setCount(count + 1)}>Count: {count}</button> {/* Doesn't re-render List */}
</div>
);
}
```

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

