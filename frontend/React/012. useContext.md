# useContext

- Imagine you are in a big classroom. Teacher has some important announcements. Instead of telling each student one by
  one, the teacher shouts in the room
- Everyone hears it immediately. In React, useContext is like that classroom announcement.
- It lets data flow to multiple components without passing it through every single intermediate component (no “parent →
  child → grandchild” hassle).
- useContext is a React Hook that allows function components to consume context values. Context provides a way to share
  data globally across the component tree without passing props down manually at every level.
- It is often used for:
- Theme settings
- User authentication info
- Language preferences
- Global application state

## createContext

```jsx
import { createContext } from "react";

export const ThemeContext = createContext("light");
```

## provideContext

```jsx
import { ThemeContext } from "./ThemeContext";

function App() {
  return (
    <ThemeContext.Provider value="dark">
      <Toolbar />
    </ThemeContext.Provider>
  );
}
```

## Consume Context in Child Component

```jsx
import { useContext } from "react";
import { ThemeContext } from "./ThemeContext";

function Toolbar() {
  const theme = useContext(ThemeContext);
  return <h2>Current Theme: {theme}</h2>;
}
```

# Important Rules (Interview Must-Know)

- Always wrap consuming components inside a Provider
- useContext only reads the current context value
- Changes in context trigger re-render of consuming components
- Avoid unnecessary context usage for performance-critical parts