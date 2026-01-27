# Custom Hook

- Imagine you are building many toys 🧸. Every toy needs: Batteries 🔋 On/Off switch Safety check
- Instead of building batteries again and again for every toy, you make one battery box and reuse it everywhere.
- In React: Many components repeat the same logic
    1. fetching data
    2. handling forms
    3. tracking window size
    4. authentication
- Copy-pasting that logic is boring and dangerous 😵
- So React says: “If logic is repeated, extract it into a Custom Hook.”
- A Custom Hook is just: a normal JavaScript function, whose name starts with use , and uses built-in hooks inside
- It does NOT return JSX, only logic and data.

## Professional Definition

- A Custom Hook is a reusable JavaScript function that encapsulates stateful logic using React Hooks and allows it to be
  shared across multiple components.
- Custom Hooks:
    1. Promote code reuse
    2. Improve readability
    3. Follow Separation of Concerns
    4. Must follow Rules of Hooks

## Simple Counter Example

```jsx
// useCounter.js
import { useState } from "react";

function useCounter(initialValue = 0) {
  const [count, setCount] = useState(initialValue);

  const increment = () => setCount(count + 1);
  const decrement = () => setCount(count - 1);

  return { count, increment, decrement };
}

export default useCounter;
```

- Usage in Component

```jsx
import useCounter from "./useCounter";

function Counter() {
  const { count, increment, decrement } = useCounter(10);

  return (
    <>
      <h2>{count}</h2>
      <button onClick={increment}>+</button>
      <button onClick={decrement}>-</button>
    </>
  );
}
```

## Fetch Data Custom Hook (Intermediate)

```jsx
// useFetch.js
import { useState, useEffect } from "react";

function useFetch(url) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        setData(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err);
        setLoading(false);
      });
  }, [url]);

  return { data, loading, error };
}

export default useFetch;
```

- usage

```jsx
const { data, loading, error } = useFetch(
  "https://jsonplaceholder.typicode.com/users"
);
```

## Window Size Custom Hook

```jsx
// useWindowSize.js
import { useState, useEffect } from "react";

function useWindowSize() {
  const [size, setSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight,
  });

  useEffect(() => {
    const handleResize = () => {
      setSize({
        width: window.innerWidth,
        height: window.innerHeight,
      });
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return size;
}

export default useWindowSize;
```

| Custom Hook    | Component   |
|----------------|-------------|
| Returns logic  | Returns JSX |
| No UI          | UI focused  |
| Reusable logic | Reusable UI |
| Uses hooks     | Uses hooks  |
