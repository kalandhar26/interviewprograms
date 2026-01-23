# useState

## Easy Definition:

- Imagine you're playing a video game and you have a score that starts at zero. Every time you jump over an obstacle,
  the score goes up by one. useState is like a magic box in your game code that remembers your score and lets you change
  it whenever you want, so the screen updates to show the new number without restarting the whole game.

## Professional definition:

- useState is a React Hook that lets you add **state** to functional components.
- It returns a pair: the current state value and a setter function to update it.
- This triggers a re-render when the state changes, making the UI reactive.
- useState is used to store and update data (state) inside a functional component.
- To make UI dynamic and interactive.

```text
Initialize state with a starting value
Get current state value
When you want to update:
  Call the setter with new value
  React re-renders the component with the updated state
```

- Simple Code Example

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

- Complex Code Example

```jsx
import React, { useState } from 'react';

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
```