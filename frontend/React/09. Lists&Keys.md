## Lists & Keys

- Imagine your teacher calls attendance. Each student has: A name, A roll number.
- If two students had the same roll number, the teacher would get confused 😵‍💫.
- In React, when you show a list of items, React needs a unique identity for each item so it knows: Which one changed
  Which one moved, Which one was removed. That identity is called a key.
- Lists in React are rendered using JavaScript array methods like map(). Keys are unique identifiers assigned to list
  items to help React efficiently update and re-render elements during reconciliation.
- Keys improve performance and prevent unexpected UI behavior.
- Keys help React identify list items uniquely so it can efficiently update the UI during re-rendering.

## Rendering a List

```jsx
function UserList() {
  const users = ["Baba", "Ali", "Rahman"];

  return (
    <ul>
      {users.map((user, index) => (
        <li key={index}>{user}</li>
      ))}
    </ul>
  );
}
```

## Best Practice

```jsx
const users = [
  { id: 101, name: "Baba" },
  { id: 102, name: "Ali" },
  { id: 103, name: "Rahman" }
];

{users.map(user => (
  <li key={user.id}>{user.name}</li>
))}
```

## Rules

- Keys must be unique
- Keys should be stable
- Avoid using index if list can change
- Keys are not accessible as props

```jsx
function TodoList({ todos }) {
  // GOOD: Stable ID from data
  return (
    <ul>
      {todos.map((todo) => (
        <TodoItem
          key={todo.id} // Stable unique ID
          todo={todo}
        />
      ))}
    </ul>
  );

  // BAD: Index as key (causes bugs when list changes)
  // {todos.map((todo, index) => (
  //   <TodoItem key={index} todo={todo} /> // ❌
  // ))}
}

// For no IDs, generate stable keys
function generateKey(item) {
  return `${item.name}-${item.timestamp}`;
}
```

- **Key Rules:**
- Keys must be unique among siblings
- Keys must be stable across re-renders
- Keys help React identify which items changed