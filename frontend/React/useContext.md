# useConext

- Picture a family where Mom's favorite color (like "blue") needs to be shared with everyone in the house without
  yelling it from room to room. useContext is like a family newsletter that magically delivers the color to every kid's
  room, so everyone knows without asking Mom over and over.

## Professional definition:

- useContext is a React Hook that consumes a context value created by createContext. It allows components to subscribe
  to context changes without prop drilling, making global state (like themes or user auth) accessible deep in the
  component tree.

## PseudoCode:

```text
textCreate a context with default value
Provide the context value higher up in the tree
In child component:
Consume the context
When provider value changes, re-render consumers
```

## Easy Code Example:

```jsx
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
```

## Complex Code Example:

```jsx
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
```