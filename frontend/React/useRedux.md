# UseReducer

- Managing a bunch of toys in different boxes (like colors, sizes, and favorites) can be chaotic if you move them one by
  one. useReducer is like a boss who listens to your commands ("add red toy" or "remove big ones") and updates all the
  boxes in one smart plan, so nothing gets lost in the shuffle.

## Definition

- useReducer is a React Hook for managing complex state logic using a reducer function. It takes a reducer and initial
  state, returning the current state and a dispatch function. It's similar to Redux but local to components, ideal for
  state with multiple sub-values or transitions.

## PseudoCode

```text
 Define reducer: (currentState, action) => newState
  Initialize with initialState
  Dispatch action: { type: 'ACTION_TYPE', payload: data }
  Reducer checks action.type and updates state accordingly
  Re-render with new state
```

```jsx
import React, { useReducer } from 'react';

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
```

- Complex Code Example

```jsx
import React, { useReducer } from 'react';

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