# Hooks

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