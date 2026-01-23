# Hooks

## useState

- useState is used to store and update data (state) inside a functional component.
- To make UI dynamic and interactive.

```jsx
import React, { useState } from 'react';

function Counter() {
  const [count, setCount] = useState(0); // Starts at 0

  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me!
      </button>
    </div>
  );
}
```

## useEffect

- useEffect is used to perform side effects like API calls, subscriptions, timers, etc.
- To run code after rendering.

```jsx
useEffect(() => {
  console.log("Component mounted");
}, []);
```

## useContext

- useContext is used to share data globally without passing props manually.
- useContext is used to access the value of a context in a functional component.
- To avoid prop drilling.

```jsx
const ThemeContext = React.createContext();

const theme = useContext(ThemeContext);
```

## useRef

- useRef is used to reference DOM elements or store values without re-rendering.
- For focus, timers, previous values.

```jsx
const inputRef = useRef();

inputRef.current.focus();
```

## useMemo

- useMemo is used to cache expensive calculations.
- To improve Performance

```jsx
const result = useMemo(() => heavyCalculation(num), [num]);
```

## useCallback

- useCallback is used to memoize functions.
- Prevents unnecessary re-creation of functions.

```jsx
const handleClick = useCallback(() => {
  setCount(c => c + 1);
}, []);
```

## useReducer

- useReducer is used for complex state logic.
- When state has multiple values or conditions. It is used for managing global state in an application.
- When state has multiple values or conditions.

```jsx
const [state, dispatch] = useReducer(reducer, initialState);
```

## Custom Hooks

- A Custom Hook is a reusable function that starts with use and contains React hooks.

## Rules of Hooks (Very Important)

1️⃣ Hooks must be called at the top level

```jsx
import React, { useState } from 'react';

function GoodComponent() {
  // Always at top level
  const [count, setCount] = useState(0);
  const [name, setName] = useState('Alice');
  
  // Safe: Hooks called every time, in order
  return (
    <div>
      <p>Count: {count}</p>
      <p>Name: {name}</p>
      <button onClick={() => setCount(count + 1)}>Increment</button>
    </div>
  );
}

// BAD Example (for illustration - don't use!):
function BadComponent() {
  const [show, setShow] = useState(false);
  if (show) {
    const [extra, setExtra] = useState(0); // Called conditionally - breaks!
  }
  return <button onClick={() => setShow(!show)}>Toggle</button>;
}
```

- Complex Code

```jsx
import React, { useState, useEffect } from 'react';

function UserDashboard() {
  // All hooks at top level, always in same order
  const [users, setUsers] = useState([]);
  const [filter, setFilter] = useState('all');
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    fetch('/api/users')
      .then(res => res.json())
      .then(data => {
        setUsers(data);
        setLoading(false);
      });
  }, []); // Runs once
  
  // BAD (commented): If filter changes, useEffect might not be called - chaos!
  // if (filter === 'active') {
  //   useEffect(() => { /* filter effect */ }, [filter]);
  // }
  
  const filteredUsers = users.filter(user => 
    filter === 'active' ? !user.inactive : true
  );
  
  if (loading) return <p>Loading...</p>;
  
  return (
    <div>
      <select value={filter} onChange={e => setFilter(e.target.value)}>
        <option value="all">All</option>
        <option value="active">Active</option>
      </select>
      <ul>
        {filteredUsers.map(user => (
          <li key={user.id}>{user.name}</li>
        ))}
      </ul>
    </div>
  );
}
```

2️⃣ Hooks must be called only inside React functions

```jsx
import React, { useState } from 'react';

// GOOD: Inside React functional component
function Counter() {
  const [count, setCount] = useState(0); // Works!
  return <button onClick={() => setCount(count + 1)}>{count}</button>;
}

// BAD: In a regular function (don't do this!)
function badHelper() {
  const [secret, setSecret] = useState('hidden'); // Error!
  return secret;
}

function App() {
  // You can call React components here, but not hooks directly
  return <Counter />;
}
```

- Complex Code

```jsx
import React, { useState, useEffect, useContext } from 'react';

const ThemeContext = React.createContext();

// Custom Hook - GOOD: It's a React function starting with 'use'
function useTheme() {
  const theme = useContext(ThemeContext); // Hook inside custom hook - OK
  const [isDark, setIsDark] = useState(theme === 'dark');
  return { isDark, toggle: () => setIsDark(!isDark) };
}

// Regular helper function - BAD for hooks
function calculateTotal(items) { // Plain JS function
  // const [total, setTotal] = useState(0); // Error: Not in React function!
  return items.reduce((sum, item) => sum + item.price, 0); // Use plain JS instead
}

function ShoppingCart() {
  // All hooks inside React component - GOOD
  const [items, setItems] = useState([]);
  const [cartTotal, setCartTotal] = useState(0);
  const { isDark, toggle } = useTheme(); // Using custom hook - OK
  
  useEffect(() => {
    setCartTotal(calculateTotal(items)); // Call helper here - fine
  }, [items]);
  
  return (
    <div className={isDark ? 'dark' : 'light'}>
      <button onClick={toggle}>Toggle Theme</button>
      <ul>
        {items.map(item => <li key={item.id}>{item.name} - ${item.price}</li>)}
      </ul>
      <p>Total: ${cartTotal}</p>
      <button onClick={() => setItems([...items, { id: Date.now(), name: 'New Item', price: 10 }])}>
        Add Item
      </button>
    </div>
  );
}

function App() {
  return (
    <ThemeContext.Provider value="dark">
      <ShoppingCart />
    </ThemeContext.Provider>
  );
}
```

3️⃣ Custom Hooks must start with use

```jsx
import React, { useState, useEffect } from 'react';

// GOOD: Custom hook starts with 'use'
function useCounter(initial = 0) {
  const [count, setCount] = useState(initial);
  
  const increment = () => setCount(c => c + 1);
  const decrement = () => setCount(c => c - 1);
  
  return { count, increment, decrement };
}

function CounterDisplay() {
  const { count, increment, decrement } = useCounter(5); // Use it like a built-in hook
  
  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={increment}>+</button>
      <button onClick={decrement}>-</button>
    </div>
  );
}

// BAD (for illustration): Doesn't start with 'use'
/*
function counter(initial) {
  const [count] = useState(initial); // Linter error!
  return count;
}
*/
```

- Complex Code

```jsx
import React, { useState, useEffect } from 'react';

// GOOD: Custom hook starts with 'use'
function useFetch(url) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  useEffect(() => {
    if (!url) return;
    
    setLoading(true);
    fetch(url)
      .then(res => {
        if (!res.ok) throw new Error('Fetch failed');
        return res.json();
      })
      .then(setData)
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [url]);
  
  return { data, loading, error, refetch: () => setLoading(true) };
}

// Another custom hook using the first one
function useUserProfile(userId) {
  const { data: user, loading, error } = useFetch(`/api/users/${userId}`);
  
  // Derived state
  const [posts, setPosts] = useState([]);
  
  useEffect(() => {
    if (user) {
      fetch(`/api/posts?userId=${user.id}`)
        .then(res => res.json())
        .then(setPosts);
    }
  }, [user]);
  
  return { user, posts, loading, error };
}

function UserProfilePage({ userId }) {
  const { user, posts, loading, error } = useUserProfile(userId);
  
  if (loading) return <p>Loading profile...</p>;
  if (error) return <p>Error: {error}</p>;
  
  return (
    <div>
      <h1>{user?.name}'s Profile</h1>
      <p>Email: {user?.email}</p>
      <h2>Posts ({posts.length})</h2>
      <ul>
        {posts.map(post => <li key={post.id}>{post.title}</li>)}
      </ul>
    </div>
  );
}

// BAD (commented): Custom hook without 'use' prefix
/*
function fetchData(url) { // Linter warns: Not a hook!
  const [data] = useState(null); // Conventions broken
  // ...
}
*/
```