# Axios

- Axios is like a super-smart mail carrier that not only delivers your letter but also automatically opens the reply,
  reads it if it's in code, and tells you if the address was wrong—all without you lifting a finger extra. You add it
  like a new toy to your project, and it makes sending messages to servers way easier than plain mail.

## Professional definition:

- Axios is a popular third-party promise-based HTTP client for browsers and Node.js. It automatically transforms JSON
  requests/responses, handles errors for non-2xx statuses, supports interceptors for global config (e.g., auth tokens),
  and provides features like request/response cancellation and progress tracking.

## PseudoCode:

```text
textimport axios from 'axios';

async function makeRequest(url, config = {}) {
try {
const response = await axios(url, config); // Auto JSON handling
return response.data; // Data is already parsed
} catch (error) {
if (error.response) {
console.error('Server error:', error.response.status, error.response.data);
} else {
console.error('Network error:', error.message);
}
}
}

// Usage: GET
makeRequest('/api/users');

// POST
makeRequest('/api/users', {
method: 'POST',
headers: { 'Authorization': 'Bearer token123' },
data: { name: 'Alice' } // Auto JSON.stringify
});
```

## Easy Code Example:

```jsx
jsximport React, { useState, useEffect } from 'react';
import axios from 'axios';

function UserList() {
const [users, setUsers] = useState([]);

useEffect(() => {
axios.get('/api/users') // Simple GET
.then(response => setUsers(response.data))
.catch(error => console.error('Axios error:', error));
}, []);

return (
<ul>
{users.map(user => <li key={user.id}>{user.name}</li>)}
</ul>
);
}
```

## Complex Code Example:

```jsx
jsximport React, { useState, useEffect } from 'react';
import axios from 'axios';

// Global interceptor for auth (runs on every request)
axios.interceptors.request.use(config => {
config.headers.Authorization = 'Bearer token123';
return config;
});

axios.interceptors.response.use(
response => response,
error => {
if (error.response?.status === 401) {
// Handle unauthorized - e.g., redirect to login
console.log('Unauthorized - logging out');
}
return Promise.reject(error);
}
);

function TodoApp() {
const [todos, setTodos] = useState([]);
const [newTodo, setNewTodo] = useState('');
const [loading, setLoading] = useState(false);

// Fetch all todos
useEffect(() => {
const fetchTodos = async () => {
setLoading(true);
try {
const response = await axios.get('/api/todos'); // Auto headers from interceptor
setTodos(response.data);
} catch (error) {
console.error('Failed to fetch todos:', error.response?.data || error.message);
} finally {
setLoading(false);
}
};
fetchTodos();
}, []);

// Add a new todo
const addTodo = async () => {
if (!newTodo) return;
try {
const response = await axios.post('/api/todos', { // Auto JSON
text: newTodo,
completed: false
});
setTodos(prev => [...prev, response.data]);
setNewTodo('');
} catch (error) {
console.error('Add todo error:', error.response?.data || error.message);
}
};

// Update a todo (PUT)
const toggleTodo = async (id, completed) => {
try {
const response = await axios.put(`/api/todos/${id}`, { completed: !completed });
setTodos(prev => prev.map(todo =>
todo.id === id ? response.data : todo
));
} catch (error) {
console.error('Toggle todo error:', error.response?.data || error.message);
}
};

// Delete a todo
const deleteTodo = async (id) => {
try {
await axios.delete(`/api/todos/${id}`);
setTodos(prev => prev.filter(todo => todo.id !== id));
} catch (error) {
console.error('Delete todo error:', error.response?.data || error.message);
}
};

if (loading) return <p>Loading todos...</p>;

return (
<div>
<input
value={newTodo}
onChange={e => setNewTodo(e.target.value)}
placeholder="New todo"
/>
<button onClick={addTodo}>Add Todo</button>
<ul>
{todos.map(todo => (
<li key={todo.id}>
<input
type="checkbox"
checked={todo.completed}
onChange={() => toggleTodo(todo.id, todo.completed)}
/>
{todo.text}
<button onClick={() => deleteTodo(todo.id)}>Delete</button>
</li>
))}
</ul>
</div>
);
}
```

# Axios Interceptors

- Axios interceptors are functions that "intercept" Axios requests or responses, allowing you to modify them or perform
  actions before they reach your code or before they are sent. This is useful for tasks like adding authentication
  tokens,
  logging, or transforming data.
- Axios interceptors are middleware functions that hook into the HTTP request/response lifecycle. They allow you to
  intercept, modify, or cancel requests before they are sent (request interceptors) or responses before they are
  handled (response interceptors). Interceptors are registered globally on the Axios instance or per-instance and can be
  synchronous or asynchronous. They return a unique ID for removal and support error handling. This enables
  cross-cutting concerns like authentication, logging, and retries without altering individual request code.

## How They Work (In Depth):

- Axios creates a request pipeline where interceptors are executed in the order they are registered. For requests:
    1. The app calls axios.request(config).
    2. Request interceptors run sequentially; each can modify config (e.g., add headers) or return a new config/Promise.
    3. If an interceptor throws/cancels, the chain stops, and an error is passed to response interceptors.
    4. The request is sent via the adapter (e.g., XMLHttpRequest in browser).

- For responses:

    1. The adapter receives the raw response.
    2. Response interceptors run in reverse order (last registered first). Each receives the response object and can
       modify it or return a new one.
    3. If successful, the modified response is resolved; if an error occurs, it's passed to error-handling interceptors.
       Internally, Axios uses a queue of interceptor functions. Each interceptor receives (value, meta) where value is
       the
       config/response/error, and meta tracks the resolver/rejector for Promise chaining. Async interceptors must return
       Promises to avoid blocking.

### Types of Interceptors:

- *Request Interceptors:* Execute before the request is sent. Useful for adding auth tokens, modifying URLs, or
  canceling invalid requests. Signature: function (config) { return config; } (or Promise).
- *Response Interceptors:* Execute after the response is received but before it's delivered to .then(). Handle success
  or errors. Signature: function (response) { return response; } for success, or (error) => { return Promise.reject(
  error); } for errors.

text# Hooks

## useState

- useState is used to store and update data (state) inside a functional component.
- To make UI dynamic and interactive.

```jsx
const [count, setCount] = React.useState(0);

<button onClick={() => setCount(count + 1)}>
  Count: {count}
</button>
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
2️⃣ Hooks must be called only inside React functions
3️⃣ Custom Hooks must start with use
========================= For Every Hook need an explaination so that Even 15 kid can understand. Example format:
useEffect:
Kids Explanation:
Professional definition:
PseudoCode:
Easy Code Example
Complex Code Example Do this for every HooksuseState
Kids Explanation:
Imagine you're playing a video game and you have a score that starts at zero. Every time you jump over an obstacle, the
score goes up by one. useState is like a magic box in your game code that remembers your score and lets you change it
whenever you want, so the screen updates to show the new number without restarting the whole game.
Professional definition:
useState is a React Hook that lets you add state to functional components. It returns a pair: the current state value
and a setter function to update it. This triggers a re-render when the state changes, making the UI reactive.
PseudoCode:
textInitialize state with a starting value
Get current state value
When you want to update:
Call the setter with new value
React re-renders the component with the updated state
Easy Code Example:
jsximport React, { useState } from 'react';

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
Complex Code Example:
jsximport React, { useState } from 'react';

function UserProfile() {
const [user, setUser] = useState({ name: '', age: 0, isLoggedIn: false });

const updateUser = (field, value) => {
setUser(prevUser => ({ ...prevUser, [field]: value })); // Updates one field without losing others
};

return (
<div>
<input
placeholder="Name"
value={user.name}
onChange={(e) => updateUser('name', e.target.value)}
/>
<input
type="number"
placeholder="Age"
value={user.age}
onChange={(e) => updateUser('age', parseInt(e.target.value))}
/>
<p>Welcome, {user.name}! Age: {user.age}</p>
<button onClick={() => updateUser('isLoggedIn', !user.isLoggedIn)}>
{user.isLoggedIn ? 'Logout' : 'Login'}
</button>
</div>
);
}
useEffect
Kids Explanation:
Think of useEffect like a reminder note on your fridge. Every time you open the fridge (your app loads or changes), it checks the note and does something, like calling your mom to say you're home safe (like fetching new game levels from the internet). You can set it to only remind you once or every time.
Professional definition:
useEffect is a React Hook that lets you perform side effects in functional components, such as data fetching, subscriptions, or manually changing the DOM. It runs after every render by default but can be controlled with a dependency array. Cleanup functions can be returned for unmounting.
PseudoCode:
textAfter component renders:
If dependencies changed (or empty array for once):
Run the effect code (e.g., fetch data)
Optional: Return a cleanup function to run before next effect or unmount
Easy Code Example:
jsximport React, { useState, useEffect } from 'react';

function Timer() {
const [seconds, setSeconds] = useState(0);

useEffect(() => {
const interval = setInterval(() => {
setSeconds(s => s + 1);
}, 1000);
return () => clearInterval(interval); // Cleanup
}, []); // Empty array: runs once

return <p>Seconds: {seconds}</p>;
}
Complex Code Example:
jsximport React, { useState, useEffect } from 'react';

function WeatherApp() {
const [weather, setWeather] = useState(null);
const [city, setCity] = useState('New York');

useEffect(() => {
if (city) {
fetch(`https://api.weather.com/v1/current?city=${city}`)
.then(res => res.json())
.then(data => setWeather(data))
.catch(err => console.error('Weather fetch failed:', err));
}
}, [city]); // Runs when city changes

useEffect(() => {
const handleResize = () => console.log('Window resized');
window.addEventListener('resize', handleResize);
return () => window.removeEventListener('resize', handleResize); // Cleanup
}, []); // Runs once, cleans up on unmount

return (
<div>
<input value={city} onChange={e => setCity(e.target.value)} />
{weather && <p>Temperature in {city}: {weather.temp}°C</p>}
</div>
);
}
useContext
Kids Explanation:
Picture a family where Mom's favorite color (like "blue") needs to be shared with everyone in the house without yelling it from room to room. useContext is like a family newsletter that magically delivers the color to every kid's room, so everyone knows without asking Mom over and over.
Professional definition:
useContext is a React Hook that consumes a context value created by createContext. It allows components to subscribe to context changes without prop drilling, making global state (like themes or user auth) accessible deep in the component tree.
PseudoCode:
textCreate a context with default value
Provide the context value higher up in the tree
In child component:
Consume the context
When provider value changes, re-render consumers
Easy Code Example:
jsximport React, { createContext, useContext } from 'react';

const ThemeContext = createContext('light'); // Default theme

function Button() {
const theme = useContext(ThemeContext); // Gets current theme
return <button className={theme}>Click me!</button>;
}

function App() {
return (
<ThemeContext.Provider value="dark">
<Button />
</ThemeContext.Provider>
);
}
Complex Code Example:
jsximport React, { createContext, useContext, useState } from 'react';

const UserContext = createContext();

function UserProfile() {
const { user, setUser } = useContext(UserContext);
return (
<div>
<p>Hello, {user.name}!</p>
<button onClick={() => setUser({ ...user, name: 'New Name' })}>
Update Name
</button>
</div>
);
}

function LoginButton() {
const { user, setUser } = useContext(UserContext);
return (
<button onClick={() => setUser({ name: 'Logged In User', id: 123 })}>
{user.id ? 'Logout' : 'Login'}
</button>
);
}

function App() {
const [user, setUser] = useState({ name: 'Guest', id: null });
return (
<UserContext.Provider value={{ user, setUser }}>
<LoginButton />
<UserProfile />
</UserContext.Provider>
);
}
useRef
Kids Explanation:
useRef is like a secret drawer in your desk that holds a toy (or a note) forever, even if you clean your room a hundred
times. You can peek in the drawer anytime to grab the toy without telling anyone or making the room messy again—perfect
for remembering where your mouse cursor is without redrawing the whole picture.
Professional definition:
useRef is a React Hook that creates a mutable ref object whose .current property is initialized to the passed argument.
It persists across re-renders without triggering them and is commonly used for accessing DOM nodes or storing mutable
values like intervals.
PseudoCode:
textCreate ref with initial value (e.g., null for DOM)
If targeting DOM: Attach ref to element
Access/modify: ref.current = newValue or element.focus()
Ref survives re-renders without causing them
Easy Code Example:
jsximport React, { useRef } from 'react';

function TextInput() {
const inputRef = useRef(null);

const focusInput = () => {
inputRef.current.focus(); // Focus the input
};

return (
<div>
<input ref={inputRef} type="text" />
<button onClick={focusInput}>Focus the input</button>
</div>
);
}
Complex Code Example:
jsximport React, { useRef, useState } from 'react';

function VideoPlayer() {
const videoRef = useRef(null);
const [isPlaying, setIsPlaying] = useState(false);

const handlePlay = () => {
videoRef.current.play();
setIsPlaying(true);
};

const handleStop = () => {
videoRef.current.pause();
videoRef.current.currentTime = 0; // Reset to start
setIsPlaying(false);
};

const prevVolume = useRef(1); // Store previous volume without re-render

const changeVolume = (newVol) => {
prevVolume.current = videoRef.current.volume; // Save old
videoRef.current.volume = newVol;
};

return (
<div>
<video ref={videoRef} width="400" src="video.mp4" />
<button onClick={handlePlay} disabled={isPlaying}>Play</button>
<button onClick={handleStop}>Stop</button>
<input
type="range"
min="0"
max="1"
step="0.1"
defaultValue="1"
onChange={e => changeVolume(e.target.value)}
/>
<p>Last volume: {prevVolume.current}</p>
</div>
);
}
useMemo
Kids Explanation:
Doing hard math homework every time your friend asks "What's 5 x 7?" is tiring. useMemo is like writing the answer on a sticky note and only recalculating if the numbers change—like if it becomes 6 x 7. It saves your brain power for fun stuff!
Professional definition:
useMemo is a React Hook that memoizes a value by recomputing it only when one of its dependencies has changed. It's used to optimize performance by avoiding expensive calculations on every render.
PseudoCode:
textDefine dependencies (e.g., [num])
If any dependency changed since last render:
Recalculate the value
Else:
Reuse the cached value
Return the value
Easy Code Example:
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
Complex Code Example:
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
useCallback
Kids Explanation:
Every time your sibling asks you to high-five, you don't need to learn a whole new high-five move—useCallback remembers your favorite high-five style and only updates it if you add a spin or something new. It keeps things fast so your game doesn't lag from re-learning moves.
Professional definition:
useCallback is a React Hook that returns a memoized callback function that only changes if one of its dependencies has changed. It's used to optimize child components that rely on function props, preventing unnecessary re-renders.
PseudoCode:
textDefine function and dependencies
If dependencies changed:
Create new function instance
Else:
Return the same cached function
Pass the memoized function as prop
Easy Code Example:
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
Complex Code Example:
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
useReducer
Kids Explanation:
Managing a bunch of toys in different boxes (like colors, sizes, and favorites) can be chaotic if you move them one by one. useReducer is like a boss who listens to your commands ("add red toy" or "remove big ones") and updates all the boxes in one smart plan, so nothing gets lost in the shuffle.
Professional definition:
useReducer is a React Hook for managing complex state logic using a reducer function. It takes a reducer and initial state, returning the current state and a dispatch function. It's similar to Redux but local to components, ideal for state with multiple sub-values or transitions.
PseudoCode:
textDefine reducer: (currentState, action) => newState
Initialize with initialState
Dispatch action: { type: 'ACTION_TYPE', payload: data }
Reducer checks action.type and updates state accordingly
Re-render with new state
Easy Code Example:
jsximport React, { useReducer } from 'react';

const initialState = { count: 0 };

function reducer(state, action) {
switch (action.type) {
case 'increment':
return { count: state.count + 1 };
case 'decrement':
return { count: state.count - 1 };
default:
return state;
}
}

function Counter() {
const [state, dispatch] = useReducer(reducer, initialState);

return (
<div>
Count: {state.count}
<button onClick={() => dispatch({ type: 'increment' })}>+</button>
<button onClick={() => dispatch({ type: 'decrement' })}>-</button>
</div>
);
}
Complex Code Example:
jsximport React, { useReducer } from 'react';

const initialState = {
users: [],
loading: false,
error: null,
filter: 'all'
};

function reducer(state, action) {
switch (action.type) {
case 'LOAD_USERS_START':
return { ...state, loading: true, error: null };
case 'LOAD_USERS_SUCCESS':
return { ...state, loading: false, users: action.payload };
case 'LOAD_USERS_ERROR':
return { ...state, loading: false, error: action.payload };
case 'ADD_USER':
return { ...state, users: [...state.users, action.payload] };
case 'DELETE_USER':
return { ...state, users: state.users.filter(u => u.id !== action.payload) };
case 'SET_FILTER':
return { ...state, filter: action.payload };
default:
return state;
}
}

function UserManager() {
const [state, dispatch] = useReducer(reducer, initialState);

const loadUsers = () => {
dispatch({ type: 'LOAD_USERS_START' });
fetch('/api/users')
.then(res => res.json())
.then(users => dispatch({ type: 'LOAD_USERS_SUCCESS', payload: users }))
.catch(err => dispatch({ type: 'LOAD_USERS_ERROR', payload: err.message }));
};

const addUser = (name) => {
dispatch({ type: 'ADD_USER', payload: { id: Date.now(), name } });
};

const filteredUsers = state.users.filter(user => {
if (state.filter === 'admin') return user.role === 'admin';
return true;
});

return (
<div>
<button onClick={loadUsers}>Load Users</button>
{state.loading && <p>Loading...</p>}
{state.error && <p>Error: {state.error}</p>}
<select onChange={e => dispatch({ type: 'SET_FILTER', payload: e.target.value })}>
<option value="all">All</option>
<option value="admin">Admins</option>
</select>
<ul>
{filteredUsers.map(user => (
<li key={user.id}>
{user.name} ({user.role})
<button onClick={() => dispatch({ type: 'DELETE_USER', payload: user.id })}>Delete</button>
</li>
))}
</ul>
<input placeholder="New user name" onKeyPress={e => {
if (e.key === 'Enter') addUser(e.target.value);
}} />
</div>
);
}4.3sExplain Rules of HooksRule 1: Hooks must be called at the top level
Kids Explanation:
Imagine building a Lego tower—you can't add the big base pieces halfway up; you have to start from the bottom every time. Hooks are like those base pieces: you always call them right at the start of your "tower" (your component), not inside loops or if-statements, so React knows exactly how many pieces to expect and doesn't get confused.
Professional definition:
Hooks must be called unconditionally at the top level of React functional components or custom hooks, not inside loops, conditions, or nested functions. This ensures React can maintain the order of hook calls consistently across re-renders, preserving the internal state tracking.
PseudoCode:
textFunction Component() {
// Top level only - always called in same order
const [state1] = useState(initial);
const state2 = useEffect(effect);

// BAD: Inside if or loop - order changes on re-renders
if (condition) {
useState(otherInitial); // React can't track this reliably
}

return <div>...</div>;
}
Easy Code Example:
jsximport React, { useState } from 'react';

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
Complex Code Example:
jsximport React, { useState, useEffect } from 'react';

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
// useEffect(() => { /* filter effect */ }, [filter]);
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
## Rule 2: Hooks must be called only inside React functions
Kids Explanation:
Hooks are special tools only for React's "playroom" (functional components or custom hooks). If you try using them outside, like in a regular helper function or just anywhere in your code, it's like bringing toys to the kitchen—they don't work there, and React gets mad because it can't keep track of the fun.
Professional definition:
Hooks can only be invoked from within React functional components or other custom hooks. Calling them from regular JavaScript functions, event handlers, or class components violates the rule, as React relies on the component lifecycle to associate hook calls with state and effects.
## PseudoCode:
```text
textfunction MyReactComponent() {
// GOOD: Inside React function
const [data] = useState(null);
return <div>{data}</div>;
}

function helperFunction() {
// BAD: Not a React function - no lifecycle tracking
useState(initial); // Error: Hooks can't be used here
}

// Custom Hook (also good, as it's a React function starting with 'use')
function useCustomLogic() {
const [value] = useState(0);
return value;
}
```
## Easy Code Example:
```jsx
jsximport React, { useState } from 'react';

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
### Complex Code Example:
```jsx
jsximport React, { useState, useEffect, useContext } from 'react';

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
### Rule 3: Custom Hooks must start with use
Kids Explanation:
When you make your own special tool (custom hook), you have to name it starting with "use" so React knows it's a magic
React tool, not just any old function. It's like labeling your homemade slime "Use-Slime" instead of "Blob"—that way,
everyone knows it's for playing with in the React game.
Professional definition:
Custom hooks are JavaScript functions that use other hooks, but they must be named with a prefix of "use" followed by a
capitalized word (e.g., useFetch). This convention allows React to identify and treat them as hooks during linting and
runtime, enforcing the other rules automatically.
PseudoCode:
text// GOOD: Starts with 'use' - React treats it as a hook
function useMyCustomHook() {
const [state] = useState(initial);
return state;
}

// BAD: Doesn't start with 'use' - Not recognized as hook
function myCustomHook() {
const [state] = useState(initial); // Still works but linter warns - breaks conventions
return state;
}

// Usage in component:
function Component() {
const value = useMyCustomHook(); // Follows rules
return <div>{value}</div>;
}
Easy Code Example:
jsximport React, { useState, useEffect } from 'react';

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
Complex Code Example:
jsximport React, { useState, useEffect } from 'react';

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
*/2.9sExplain How you use Axios and Fetch?Fetch API
Kids Explanation:
Imagine Fetch like sending a letter to a friend by mail—you tell it where to go (the URL), what to say (like GET or
POST), and wait for a reply (the data). It's built right into your browser, so no extra apps needed, but you have to
handle the "Did it arrive okay?" part yourself if something goes wrong.
Professional definition:
The Fetch API is a native JavaScript interface for making HTTP requests. It's promise-based, supports modern features
like async/await, and works for GET, POST, PUT, DELETE, etc. Unlike older XMLHttpRequest, it's simpler but requires
manual JSON parsing and error handling for non-2xx responses.
PseudoCode:
textasync function makeRequest(url, options = {}) {
try {
const response = await fetch(url, options); // Send request
if (!response.ok) throw new Error('Request failed'); // Check status
const data = await response.json(); // Parse response
return data;
} catch (error) {
console.error('Error:', error); // Handle errors
}
}

// Usage: GET
makeRequest('/api/users');

// POST
makeRequest('/api/users', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify({ name: 'Alice' })
});
Easy Code Example:
jsximport React, { useState, useEffect } from 'react';

function UserList() {
const [users, setUsers] = useState([]);

useEffect(() => {
fetch('/api/users') // Simple GET request
.then(response => response.json())
.then(data => setUsers(data))
.catch(error => console.error('Fetch error:', error));
}, []);

return (
<ul>
{users.map(user => <li key={user.id}>{user.name}</li>)}
</ul>
);
}
Complex Code Example:
jsximport React, { useState, useEffect } from 'react';

function TodoApp() {
const [todos, setTodos] = useState([]);
const [newTodo, setNewTodo] = useState('');
const [loading, setLoading] = useState(false);

// Fetch all todos
useEffect(() => {
const fetchTodos = async () => {
setLoading(true);
try {
const response = await fetch('/api/todos', {
method: 'GET',
headers: { 'Authorization': 'Bearer token123' } // Custom header
});
if (!response.ok) {
throw new Error(`HTTP error! status: ${response.status}`);
}
const data = await response.json();
setTodos(data);
} catch (error) {
console.error('Failed to fetch todos:', error);
} finally {
setLoading(false);
}
};
fetchTodos();
}, []);

// Add a new todo
const addTodo = async () => {
if (!newTodo) return;
try {
const response = await fetch('/api/todos', {
method: 'POST',
headers: {
'Content-Type': 'application/json',
'Authorization': 'Bearer token123'
},
body: JSON.stringify({ text: newTodo, completed: false })
});
if (!response.ok) throw new Error('Failed to add todo');
const addedTodo = await response.json();
setTodos(prev => [...prev, addedTodo]);
setNewTodo('');
} catch (error) {
console.error('Add todo error:', error);
}
};

// Delete a todo
const deleteTodo = async (id) => {
try {
const response = await fetch(`/api/todos/${id}`, {
method: 'DELETE',
headers: { 'Authorization': 'Bearer token123' }
});
if (!response.ok) throw new Error('Failed to delete todo');
setTodos(prev => prev.filter(todo => todo.id !== id));
} catch (error) {
console.error('Delete todo error:', error);
}
};

if (loading) return <p>Loading todos...</p>;

return (
<div>
<input
value={newTodo}
onChange={e => setNewTodo(e.target.value)}
placeholder="New todo"
/>
<button onClick={addTodo}>Add Todo</button>
<ul>
{todos.map(todo => (
<li key={todo.id}>
{todo.text} {todo.completed ? '(Done)' : ''}
<button onClick={() => deleteTodo(todo.id)}>Delete</button>
</li>
))}
</ul>
</div>
);
}
Axios
Kids Explanation:
Axios is like a super-smart mail carrier that not only delivers your letter but also automatically opens the reply, reads it if it's in code, and tells you if the address was wrong—all without you lifting a finger extra. You add it like a new toy to your project, and it makes sending messages to servers way easier than plain mail.
Professional definition:
Axios is a popular third-party promise-based HTTP client for browsers and Node.js. It automatically transforms JSON requests/responses, handles errors for non-2xx statuses, supports interceptors for global config (e.g., auth tokens), and provides features like request/response cancellation and progress tracking.
PseudoCode:
textimport axios from 'axios';

async function makeRequest(url, config = {}) {
try {
const response = await axios(url, config); // Auto JSON handling
return response.data; // Data is already parsed
} catch (error) {
if (error.response) {
console.error('Server error:', error.response.status, error.response.data);
} else {
console.error('Network error:', error.message);
}
}
}

// Usage: GET
makeRequest('/api/users');

// POST
makeRequest('/api/users', {
method: 'POST',
headers: { 'Authorization': 'Bearer token123' },
data: { name: 'Alice' } // Auto JSON.stringify
});
Easy Code Example:
jsximport React, { useState, useEffect } from 'react';
import axios from 'axios';

function UserList() {
const [users, setUsers] = useState([]);

useEffect(() => {
axios.get('/api/users') // Simple GET
.then(response => setUsers(response.data))
.catch(error => console.error('Axios error:', error));
}, []);

return (
<ul>
{users.map(user => <li key={user.id}>{user.name}</li>)}
</ul>
);
}
Complex Code Example:
jsximport React, { useState, useEffect } from 'react';
import axios from 'axios';

// Global interceptor for auth (runs on every request)
axios.interceptors.request.use(config => {
config.headers.Authorization = 'Bearer token123';
return config;
});

axios.interceptors.response.use(
response => response,
error => {
if (error.response?.status === 401) {
// Handle unauthorized - e.g., redirect to login
console.log('Unauthorized - logging out');
}
return Promise.reject(error);
}
);

function TodoApp() {
const [todos, setTodos] = useState([]);
const [newTodo, setNewTodo] = useState('');
const [loading, setLoading] = useState(false);

// Fetch all todos
useEffect(() => {
const fetchTodos = async () => {
setLoading(true);
try {
const response = await axios.get('/api/todos'); // Auto headers from interceptor
setTodos(response.data);
} catch (error) {
console.error('Failed to fetch todos:', error.response?.data || error.message);
} finally {
setLoading(false);
}
};
fetchTodos();
}, []);

// Add a new todo
const addTodo = async () => {
if (!newTodo) return;
try {
const response = await axios.post('/api/todos', { // Auto JSON
text: newTodo,
completed: false
});
setTodos(prev => [...prev, response.data]);
setNewTodo('');
} catch (error) {
console.error('Add todo error:', error.response?.data || error.message);
}
};

// Update a todo (PUT)
const toggleTodo = async (id, completed) => {
try {
const response = await axios.put(`/api/todos/${id}`, { completed: !completed });
setTodos(prev => prev.map(todo =>
todo.id === id ? response.data : todo
));
} catch (error) {
console.error('Toggle todo error:', error.response?.data || error.message);
}
};

// Delete a todo
const deleteTodo = async (id) => {
try {
await axios.delete(`/api/todos/${id}`);
setTodos(prev => prev.filter(todo => todo.id !== id));
} catch (error) {
console.error('Delete todo error:', error.response?.data || error.message);
}
};

if (loading) return <p>Loading todos...</p>;

return (
<div>
<input
value={newTodo}
onChange={e => setNewTodo(e.target.value)}
placeholder="New todo"
/>
<button onClick={addTodo}>Add Todo</button>
<ul>
{todos.map(todo => (
<li key={todo.id}>
<input
type="checkbox"
checked={todo.completed}
onChange={() => toggleTodo(todo.id, todo.completed)}
/>
{todo.text}
<button onClick={() => deleteTodo(todo.id)}>Delete</button>
</li>
))}
</ul>
</div>
);
}2.6sExplain Axios Interceptors in DepthAxios Interceptors
Kids Explanation:
Imagine Axios as a busy mail carrier delivering letters (requests) to your friends (servers). Interceptors are like checkpoints along the route: one at the mailbox before sending (request interceptor) to add a sticker or check the address, and one at the friend's door after delivery (response interceptor) to read the reply and maybe add a thank-you note. They let you tweak things automatically without rewriting the whole mailing system every time.
Professional Definition:
Axios interceptors are middleware functions that hook into the HTTP request/response lifecycle. They allow you to intercept, modify, or cancel requests before they are sent (request interceptors) or responses before they are handled (response interceptors). Interceptors are registered globally on the Axios instance or per-instance and can be synchronous or asynchronous. They return a unique ID for removal and support error handling. This enables cross-cutting concerns like authentication, logging, and retries without altering individual request code.
How They Work (In Depth):
Axios creates a request pipeline where interceptors are executed in the order they are registered. For requests:

The app calls axios.request(config).
Request interceptors run sequentially; each can modify config (e.g., add headers) or return a new config/Promise.
If an interceptor throws/cancels, the chain stops, and an error is passed to response interceptors.
The request is sent via the adapter (e.g., XMLHttpRequest in browser).

For responses:

The adapter receives the raw response.
Response interceptors run in reverse order (last registered first). Each receives the response object and can modify it
or return a new one.
If successful, the modified response is resolved; if an error occurs, it's passed to error-handling interceptors.
Internally, Axios uses a queue of interceptor functions. Each interceptor receives (value, meta) where value is the
config/response/error, and meta tracks the resolver/rejector for Promise chaining. Async interceptors must return
Promises to avoid blocking.

### Types of Interceptors:

- **Request Interceptors:** Execute before the request is sent. Useful for adding auth tokens, modifying URLs, or
  canceling invalid requests. Signature: function (config) { return config; } (or Promise).
- **Response Interceptors:** Execute after the response is received but before it's delivered to .then(). Handle success
  or errors. Signature: function (response) { return response; } for success, or (error) => { return Promise.reject(
  error); } for errors.

### PseudoCode (Request Interceptor):

```text
textaxios.interceptors.request.use(
(config) => { // Success handler
config.headers.Authorization = 'Bearer ' + getToken(); // Modify
return config; // Or return Promise.resolve(modifiedConfig)
},
(error) => { // Error handler (rarely used)
return Promise.reject(error);
}
);
```

### PseudoCode (Response Interceptor):

```text
textaxios.interceptors.response.use(
(response) => { // Success: Runs for 2xx-3xx
console.log('Success:', response.status);
return response; // Or transform data: return { ...response, data: processedData }
},
(error) => { // Error: Runs for 4xx-5xx or network issues
if (error.response?.status === 401) {
// Refresh token logic
return refreshToken().then(() => axios.request(error.config)); // Retry
}
return Promise.reject(error); // Re-throw
}
);
```

### Easy Code Example:

```jsx
jsximport axios from 'axios';

// Simple global request interceptor: Add a timestamp header to every request
axios.interceptors.request.use((config) => {
config.headers['X-Timestamp'] = Date.now();
console.log('Request sent at:', new Date().toISOString());
return config;
});

// Simple global response interceptor: Log status for all responses
axios.interceptors.response.use(
(response) => {
console.log('Response status:', response.status);
return response;
},
(error) => {
console.error('Error status:', error.response?.status || 'Network Error');
return Promise.reject(error);
}
);

// Usage: Any axios call will trigger these
axios.get('/api/users').then(res => console.log(res.data));
```

### Complex Code Example:

```jsx
jsximport React, { useState } from 'react';
import axios from 'axios';

let isRefreshing = false;
let failedQueue = [];

// Token refresh function (simulate)
const refreshToken = async () => {
const response = await axios.post('/api/auth/refresh', {}, {
withCredentials: true // Assume refresh token in cookies
});
return response.data.accessToken;
};

// Request interceptor: Add auth token if available
const requestInterceptor = axios.interceptors.request.use(
(config) => {
const token = localStorage.getItem('accessToken');
if (token) {
config.headers.Authorization = `Bearer ${token}`;
}
return config;
},
(error) => Promise.reject(error)
);

// Response interceptor: Handle 401 by refreshing token and retrying
const responseInterceptor = axios.interceptors.response.use(
(response) => response, // Pass through on success
async (error) => {
const originalRequest = error.config;

    // If 401 and not already refreshing, start refresh
    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        // Queue failed requests to retry after refresh
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        }).then(() => {
          return axios(originalRequest); // Retry after refresh
        }).catch(err => {
          Promise.all(failedQueue.map(req => req.reject(err)));
          failedQueue = [];
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        const token = await refreshToken();
        localStorage.setItem('accessToken', token);

        // Retry original request
        const retryResponse = await axios(originalRequest);
        isRefreshing = false;

        // Resolve queued requests
        failedQueue.forEach(req => {
          req.resolve(retryResponse);
        });
        failedQueue = [];

        return retryResponse;
      } catch (refreshError) {
        isRefreshing = false;
        localStorage.removeItem('accessToken');
        failedQueue.forEach(req => req.reject(refreshError));
        failedQueue = [];
        // Redirect to login or handle logout
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    // For other errors, just reject
    return Promise.reject(error);
}
);

// Eject interceptors when done (e.g., on logout)
const removeInterceptors = () => {
axios.interceptors.request.eject(requestInterceptor);
axios.interceptors.response.eject(responseInterceptor);
};

function AuthenticatedApp() {
const [users, setUsers] = useState([]);

const fetchUsers = async () => {
try {
const response = await axios.get('/api/users'); // Interceptors auto-handle auth/errors
setUsers(response.data);
} catch (error) {
console.error('Failed to fetch users:', error);
}
};

return (
<div>
<button onClick={fetchUsers}>Load Users</button>
<ul>
{users.map(user => <li key={user.id}>{user.name}</li>)}
</ul>
<button onClick={removeInterceptors}>Logout (Remove Interceptors)</button>
</div>
);
}
```

#### Common Use Cases:

- Authentication: Automatically add/refresh JWT tokens (as in the complex example).
- Logging/Monitoring: Track request timings, errors, or payloads for debugging/analytics (e.g., integrate with Sentry).
- Error Handling: Centralize 500-error toasts or retries for flaky APIs.
- Request Modification: Add user agents, compress payloads, or transform URLs based on environment.
- Caching: Check cache before requesting (pair with localStorage or Redux).
- Rate Limiting: Delay requests if exceeding API limits.

#### Best Practices:

- Order Matters: Register interceptors in sequence—earlier request interceptors run first, response in reverse.
- Async Handling: Always return a Promise from async interceptors to avoid hanging requests.
- Avoid Side Effects: Don't mutate shared state without care; use immutable updates.
- Per-Instance: For isolated logic (e.g., one API client per service), create const api = axios.create() and add
  interceptors to api.
- Cleanup: Eject interceptors on unmount/logout to prevent memory leaks.
- Error Propagation: In response error handlers, re-throw or reject to let callers handle specifics.
- Testing: Mock interceptors in unit tests with jest.spyOn(axios.interceptors.request, 'use').

#### Common Pitfalls:

- Infinite Loops: Retrying without a flag (e.g., _retry) can loop on 401s.
- Not Returning Values: Forgetting return config or return response skips the chain.
- Global vs. Instance: Globals affect all requests; use instances for modularity.
- Unhandled Rejections: Always chain .catch() or use response error interceptors.
- Browser-Specific: Credentials/cookies need withCredentials: true; CORS must be server-configured.
- Overuse: Interceptors add overhead—use for truly global concerns, not per-request logic.
