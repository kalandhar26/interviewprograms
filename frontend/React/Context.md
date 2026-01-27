## Context

- Context provides a way to pass data deeply without prop drilling, via Provider/Consumer or useContext hook.

```jsx
import { createContext, useContext } from 'react';

const ThemeContext = createContext('light');

function Button() {
  const theme = useContext(ThemeContext);
  return <button className={theme}>Click</button>;
}

function App() {
  return (
    <ThemeContext.Provider value="dark">
      <Button />
    </ThemeContext.Provider>
  );
}
```