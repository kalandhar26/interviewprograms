# useReducer

- Imagine you have a video game controller 🎮. Every time you press a button, something happens: move, jump, shoot
- The game decides what to do based on the button pressed. The controller doesn’t store the game state — it just sends
  instructions
- The game engine updates the state and shows it on the screen
- In React, useReducer is like the game engine:
- State → the current situation of the game
- Action → instructions (like button presses)
- Reducer → decides how state changes based on the action
- This is perfect when state logic is complex or has multiple sub-values.

## Professional definition

- useReducer is a React Hook used for managing complex state logic in function components.
- useReducer is a hook for managing complex state logic using a reducer function that takes the current state and an action to return a new state.
- **It is an alternative to useState when:**
- State transitions are complicated
- New state depends on the previous state
- Multiple sub-values need to be updated together
- useReducer follows the reducer pattern:

```text
newState = reducer(currentState, action)
```

## Easy Code Example:

```jsx
import { useReducer } from "react";

// Step 1: Define reducer function
function reducer(state, action) {
  switch (action.type) {
    case "increment":
      return { count: state.count + 1 };
    case "decrement":
      return { count: state.count - 1 };
    case "reset":
      return { count: 0 };
    default:
      return state;
  }
}

// Step 2: Use reducer in component
function Counter() {
  const [state, dispatch] = useReducer(reducer, { count: 0 });

  return (
    <div>
      <h2>Count: {state.count}</h2>
      <button onClick={() => dispatch({ type: "increment" })}>+</button>
      <button onClick={() => dispatch({ type: "decrement" })}>-</button>
      <button onClick={() => dispatch({ type: "reset" })}>Reset</button>
    </div>
  );
}

export default Counter;

```

- state → current state
- dispatch → function to send actions
- reducer → pure function deciding new state

## Important Rules (Interview Must-Know)

- Reducer must be a pure function (no side effects)
- Always use dispatch to update state, never mutate state directly
- Ideal for complex state or multiple related values
- Can be combined with useContext for global state

## Complex Code Example:

```jsx
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
}
```