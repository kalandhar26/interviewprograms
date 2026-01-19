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

## Refs
- Refs provide direct access to DOM nodes or component instances, bypassing React's render cycle. Use useRef hook in functionals.

```jsx
import { useRef } from 'react';

function TextInput() {
  const inputRef = useRef(null);

  const focusInput = () => inputRef.current.focus();

  return (
    <>
      <input ref={inputRef} />
      <button onClick={focusInput}>Focus</button>
    </>
  );
}
```

## Error Boundaries
- Components that catch JS errors in child trees, log them, and render fallbacks. Class-only (no hook equivalent yet in 2025).

```jsx
class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    console.log(error, errorInfo);
  }

  render() {
    if (this.state.hasError) return <h1>Something went wrong.</h1>;
    return this.props.children;
  }
}
// Usage: <ErrorBoundary><BuggyComp /></ErrorBoundary>
```

## Portals
- Render children into a DOM node outside the parent hierarchy (e.g., modals). Uses createPortal from react-dom

```jsx
import { createPortal } from 'react-dom';

function Modal({ children }) {
  return createPortal(
    <div className="modal">{children}</div>,
    document.getElementById('modal-root')
  );
}
```

## HTTP Requests (GET and POST)
- Fetching/sending data via Fetch API or Axios. GET retrieves; POST submits. Integrate with useEffect/useState.

```jsx
import { useState, useEffect } from 'react';

function UserForm() {
  const [users, setUsers] = useState([]);
  const [name, setName] = useState('');

  useEffect(() => {
    fetch('/api/users') // GET
      .then(res => res.json())
      .then(setUsers);
  }, []);

  const handleSubmit = e => {
    e.preventDefault();
    fetch('/api/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name })
    }).then(() => setName(''));
  };

  return (
    <form onSubmit={handleSubmit}>
      <input value={name} onChange={e => setName(e.target.value)} />
      <button type="submit">Add</button>
      <ul>{users.map(u => <li key={u.id}>{u.name}</li>)}</ul>
    </form>
  );
}
```

# Hooks
- Hooks are functions for state/logic reuse in functionals. Covered individually below for depth.

## 1. useEffect: Side Effects

```jsx
import { useState, useEffect } from "react";

function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // 🔹 Run once on mount (componentDidMount)
  useEffect(() => {
    console.log("Component mounted");

    // Cleanup function (componentWillUnmount)
    return () => {
      console.log("Component unmounting");
    };
  }, []); // Empty dependency array

  // 🔹 Run when userId changes (componentDidUpdate)
  useEffect(() => {
    if (!userId) return;

    const fetchUser = async () => {
      setLoading(true);
      try {
        const response = await fetch(`/api/users/${userId}`);
        const data = await response.json();
        setUser(data);
      } catch (error) {
        console.error("Failed to fetch user:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, [userId]); // Dependency array

  // 🔹 Run on every render (use with caution)
  useEffect(() => {
    console.log("Component rendered");
  }); // No dependency array

  if (loading) return <Spinner />;
  return <div>{user?.name}</div>;
}
```

- **Common Use Cases:**
- Data fetching
- Setting up subscriptions
- Manual DOM manipulations
- Setting timers

```jsx
// Timer cleanup
useEffect(() => {
  const interval = setInterval(() => {
    console.log("Tick");
  }, 1000);

  return () => clearInterval(interval);
}, []);

// Event listener cleanup
useEffect(() => {
  const handleResize = () => {
    console.log(window.innerWidth);
  };

  window.addEventListener("resize", handleResize);
  return () => window.removeEventListener("resize", handleResize);
}, []);
```

## 2. useContext: Global State

```jsx
// 1. Create Context
import { createContext, useContext, useState } from "react";

const ThemeContext = createContext("light"); // Default value

// 2. Create Provider Component
function ThemeProvider({ children }) {
  const [theme, setTheme] = useState("light");

  const toggleTheme = () => {
    setTheme((prev) => (prev === "light" ? "dark" : "light"));
  };

  return (
    <ThemeContext.Provider value={{ theme, toggleTheme }}>
      {children}
    </ThemeContext.Provider>
  );
}

// 3. Use in App
function App() {
  return (
    <ThemeProvider>
      <Header />
      <Main />
      <Footer />
    </ThemeProvider>
  );
}

// 4. Consume in any component
function Header() {
  const { theme, toggleTheme } = useContext(ThemeContext);

  return (
    <header className={`theme-${theme}`}>
      <button onClick={toggleTheme}>
        Switch to {theme === "light" ? "Dark" : "Light"}
      </button>
    </header>
  );
}
```

- **_When to Use Context:_**
- Theme (light/dark mode)
- User authentication
- Language/locale
- Global notifications
- **When NOT to Use Context:**
- High-frequency updates (use state management libs)
- Form state (use local state or form libraries)

## 3. useRef: Direct DOM Access

```jsx
import { useRef, useEffect } from "react";

function FocusInput() {
  // 1. Create ref
  const inputRef = useRef(null);
  const renderCount = useRef(0);

  // 2. Focus on mount
  useEffect(() => {
    inputRef.current.focus();
    renderCount.current += 1;
  }, []);

  const handleClick = () => {
    // 3. Access DOM element
    console.log(inputRef.current.value);
    inputRef.current.select();
  };

  return (
    <div>
      <input ref={inputRef} type="text" />
      <button onClick={handleClick}>Get Value</button>
      <p>Renders: {renderCount.current}</p>
    </div>
  );
}

// Forwarding refs
const CustomInput = React.forwardRef((props, ref) => (
  <input ref={ref} {...props} />
));
```

- **useRef vs useState:**
- **useRef:** Mutable, doesn't trigger re-renders
- **useState:** Immutable, triggers re-renders

- **Use Cases:**
- Accessing DOM elements
- Storing previous values
- Keeping mutable variables
- Timer IDs, interval references

## 4. useReducer: Complex State Logic

```jsx
import { useReducer } from "react";

// 1. Reducer function
function todoReducer(state, action) {
  switch (action.type) {
    case "ADD_TODO":
      return {
        ...state,
        todos: [...state.todos, action.payload],
      };
    case "TOGGLE_TODO":
      return {
        ...state,
        todos: state.todos.map((todo) =>
          todo.id === action.payload
            ? { ...todo, completed: !todo.completed }
            : todo
        ),
      };
    case "SET_FILTER":
      return {
        ...state,
        filter: action.payload,
      };
    default:
      return state;
  }
}

// 2. Initial state
const initialState = {
  todos: [],
  filter: "all",
};

function TodoApp() {
  // 3. Use reducer
  const [state, dispatch] = useReducer(todoReducer, initialState);

  const addTodo = (text) => {
    dispatch({
      type: "ADD_TODO",
      payload: { id: Date.now(), text, completed: false },
    });
  };

  return (
    <div>
      <button onClick={() => addTodo("New Task")}>Add Todo</button>

      {state.todos.map((todo) => (
        <div key={todo.id}>
          <input
            type="checkbox"
            checked={todo.completed}
            onChange={() =>
              dispatch({
                type: "TOGGLE_TODO",
                payload: todo.id,
              })
            }
          />
          {todo.text}
        </div>
      ))}
    </div>
  );
}
```

- **When to Use useReducer:**
- Complex state with multiple sub-values
- Next state depends on previous state
- State transitions need to be predictable
- Sharing state logic between components

## 5. useMemo & useCallback: Performance Optimization

```jsx
import { useMemo, useCallback, useState } from "react";

function ExpensiveComponent({ list, filter }) {
  // 🔹 Memoize expensive calculation
  const filteredList = useMemo(() => {
    console.log("Filtering..."); // Only runs when dependencies change
    return list.filter((item) => item.includes(filter));
  }, [list, filter]); // Dependency array

  // 🔹 Memoize function (prevents unnecessary re-renders)
  const handleClick = useCallback(() => {
    console.log("Clicked with filter:", filter);
  }, [filter]); // Only recreates when filter changes

  // 🔹 Without useCallback (recreates on every render)
  const badHandleClick = () => {
    console.log("Clicked");
  };

  return (
    <div>
      <ChildComponent onClick={handleClick} />
      <ul>
        {filteredList.map((item) => (
          <li key={item}>{item}</li>
        ))}
      </ul>
    </div>
  );
}

// Child component with React.memo
const ChildComponent = React.memo(({ onClick }) => {
  console.log("Child rendered");
  return <button onClick={onClick}>Click me</button>;
});
```

- **useMemo vs useCallback:**
- **useMemo:** Returns memoized value
- **useCallback:** Returns memoized function
- **When to Optimize:**
- Large lists or expensive calculations
- Passing callbacks to memoized child components
- Performance issues detected (measure first!)
- **Golden Rule:** Don't optimize prematurely! Use React DevTools Profiler to identify bottlenecks.

## 6.Custom Hooks: Reusable Logic

```jsx
// hooks/useLocalStorage.js
import { useState, useEffect } from "react";

function useLocalStorage(key, initialValue) {
  // Get from localStorage or use initial
  const [value, setValue] = useState(() => {
    const stored = localStorage.getItem(key);
    return stored ? JSON.parse(stored) : initialValue;
  });

  // Update localStorage when value changes
  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(value));
  }, [key, value]);

  return [value, setValue];
}

// hooks/useFetch.js
function useFetch(url) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const controller = new AbortController();

    const fetchData = async () => {
      try {
        const response = await fetch(url, {
          signal: controller.signal,
        });
        if (!response.ok) throw new Error("Network error");
        const result = await response.json();
        setData(result);
      } catch (err) {
        if (err.name !== "AbortError") {
          setError(err.message);
        }
      } finally {
        setLoading(false);
      }
    };

    fetchData();

    return () => controller.abort();
  }, [url]);

  return { data, loading, error };
}

// Usage
function UserProfile({ userId }) {
  const { data: user, loading, error } = useFetch(`/api/users/${userId}`);
  const [theme, setTheme] = useLocalStorage("theme", "light");

  if (loading) return <Spinner />;
  if (error) return <Error message={error} />;

  return (
    <div className={`theme-${theme}`}>
      <h1>{user.name}</h1>
    </div>
  );
}
```

- **Custom Hook Rules:**
- Name must start with use
- Can call other hooks
- Share stateful logic, not state itself

## 7. Error Boundaries (Class Component Only)

```jsx
class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    // Log to error reporting service
    console.error("Error:", error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="error-boundary">
          <h2>Something went wrong</h2>
          <button onClick={() => this.setState({ hasError: false })}>
            Try again
          </button>
        </div>
      );
    }

    return this.props.children;
  }
}

// Usage
function App() {
  return (
    <ErrorBoundary>
      <BuggyComponent />
    </ErrorBoundary>
  );
}
```

- For functional components: Use react-error-boundary library

## 8. Code Splitting & Lazy Loading

```jsx
import { lazy, Suspense } from "react";

// Dynamically import component
const HeavyComponent = lazy(() => import("./HeavyComponent"));
const Dashboard = lazy(() => import("./Dashboard"));

function App() {
  return (
    <Suspense
      fallback={
        <div className="loading">
          <Spinner />
          <p>Loading component...</p>
        </div>
      }
    >
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/heavy" element={<HeavyComponent />} />
      </Routes>
    </Suspense>
  );
}
```

- Faster initial load
- Split by route or feature
- Better performance metrics

## 9. React 18 Concurrent Features

```jsx
import { useState, useTransition, useDeferredValue } from "react";

function SearchComponent() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [isPending, startTransition] = useTransition();

  // Deferred value for non-urgent updates
  const deferredQuery = useDeferredValue(query);

  const handleSearch = (value) => {
    setQuery(value);

    // Mark as non-urgent transition
    startTransition(() => {
      // Expensive computation
      const filtered = largeList.filter((item) => item.includes(value));
      setResults(filtered);
    });
  };

  return (
    <div>
      <input
        value={query}
        onChange={(e) => handleSearch(e.target.value)}
        placeholder="Search..."
      />

      {/* Show loading during transition */}
      {isPending && <small>Loading results...</small>}

      {/* Use deferred value for rendering */}
      <SearchResults query={deferredQuery} results={results} />
    </div>
  );
}
```

- **New React 18 Features:**
- **Automatic Batching:** Multiple state updates batched
- **Transitions:** Mark updates as non-urgent
- **Suspense for Data Fetching:** Better loading states
- **New Hooks:** useId, useSyncExternalStore

## 10. State Management Solutions

| Library              | When to Use                    | Size   | Learning Curve |
| -------------------- | ------------------------------ | ------ | -------------- |
| Context + useReducer | Small apps, theme, auth        | 0 kB   | Easy           |
| Zustand              | Most apps, simple global state | ~1 kB  | Easy           |
| Redux Toolkit        | Large apps, complex state      | ~10 kB | Medium         |
| Recoil               | Complex derived state          | ~15 kB | Medium         |
| MobX                 | OOP style, reactive            | ~15 kB | Medium         |

### Zustand Example

```javascript
import create from "zustand";

const useStore = create((set) => ({
  bears: 0,
  increase: () => set((state) => ({ bears: state.bears + 1 })),
  removeAllBears: () => set({ bears: 0 }),
}));

function BearCounter() {
  const bears = useStore((state) => state.bears);
  const increase = useStore((state) => state.increase);

  return <button onClick={increase}>Bears: {bears}</button>;
}
```

## 11. Data Fetching Libraries

### React Query (TanStack Query) - RECOMMENDED:

```jsx
import { useQuery, useMutation, QueryClient } from "@tanstack/react-query";

const queryClient = new QueryClient();

function Users() {
  const { data, isLoading, error } = useQuery({
    queryKey: ["users"],
    queryFn: () => fetch("/api/users").then((res) => res.json()),
    staleTime: 5 * 60 * 1000, // 5 minutes
  });

  const mutation = useMutation({
    mutationFn: (newUser) =>
      fetch("/api/users", {
        method: "POST",
        body: JSON.stringify(newUser),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries(["users"]);
    },
  });

  if (isLoading) return "Loading...";
  return (
    <ul>
      {data.map((user) => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}
```

### Form Management

- React Hook Form - RECOMMENDED

```jsx
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});

function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const onSubmit = async (data) => {
    await login(data);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register("email")} placeholder="Email" />
      {errors.email && <span>{errors.email.message}</span>}

      <input type="password" {...register("password")} placeholder="Password" />
      {errors.password && <span>{errors.password.message}</span>}

      <button type="submit" disabled={isSubmitting}>
        {isSubmitting ? "Logging in..." : "Login"}
      </button>
    </form>
  );
}
```

### Routing

- React Router v6

```jsx
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/users/:id" element={<UserProfile />} />
        <Route path="/dashboard/*" element={<Dashboard />} />

        {/* Nested routes */}
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<AdminDashboard />} />
          <Route path="users" element={<UserManagement />} />
          <Route path="settings" element={<Settings />} />
        </Route>

        {/* 404 route */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

// Access params
function UserProfile() {
  const { id } = useParams();
  const navigate = useNavigate();

  return (
    <div>
      <h1>User {id}</h1>
      <button onClick={() => navigate("/")}>Go Home</button>
    </div>
  );
}
```

### Styling Solutions

- Tailwind CSS (Recommended for 2025)

```jsx
function Card({ title, children }) {
  return (
    <div className="max-w-md mx-auto bg-white rounded-xl shadow-lg overflow-hidden md:max-w-2xl">
      <div className="md:flex">
        <div className="p-8">
          <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">
            {title}
          </div>
          <div className="mt-2 text-gray-500">{children}</div>
        </div>
      </div>
    </div>
  );
}
```

- **Alternative:** CSS Modules, Styled Components, Emotion

### Testing Strategy

- **React Testing Library:**

```jsx
import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Counter from "./Counter";

test("increments counter", async () => {
  const user = userEvent.setup();
  render(<Counter />);

  // Get by role (preferred)
  const button = screen.getByRole("button", { name: /increment/i });

  // Click button
  await user.click(button);

  // Assert
  expect(screen.getByText("Count: 1")).toBeInTheDocument();
});

// Mock API calls
test("fetches data", async () => {
  // Mock fetch
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () => Promise.resolve({ data: "test" }),
    })
  );

  render(<DataFetcher />);

  // Wait for data
  expect(await screen.findByText("test")).toBeInTheDocument();
});
```

```jsx
import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Counter from "./Counter";

test("increments counter", async () => {
  const user = userEvent.setup();
  render(<Counter />);

  // Get by role (preferred)
  const button = screen.getByRole("button", { name: /increment/i });

  // Click button
  await user.click(button);

  // Assert
  expect(screen.getByText("Count: 1")).toBeInTheDocument();
});

// Mock API calls
test("fetches data", async () => {
  // Mock fetch
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () => Promise.resolve({ data: "test" }),
    })
  );

  render(<DataFetcher />);

  // Wait for data
  expect(await screen.findByText("test")).toBeInTheDocument();
});
```

## Render Props
- Pattern passing a function as prop to share code; component renders based on that function's output. Less common post-hooks, but useful for logic sharing.

```jsx
function MouseTracker({ render }) {
  const [position, setPosition] = useState({ x: 0, y: 0 });
  // ...useEffect for mouse move
  return <div onMouseMove={...}>{render(position)}</div>;
}
// Usage: <MouseTracker render={pos => <h1>X: {pos.x}</h1>} />
```

## Reconciliation
- React's process diffing virtual DOM against real DOM for minimal updates. Uses keys for lists; React 18 enhances with concurrent mode (offscreen rendering).

## State Management (Redux / MobX / Recoil)
- Libraries for predictable global state.
- Redux: Flux with reducers/actions. 
- MobX: Observable-based. 
- Recoil: Atom/selector atoms for fine-grained.

```jsx
// store.js
import { createStore } from 'redux';
const reducer = (state = { count: 0 }, action) => action.type === 'INC' ? { ...state, count: state.count + 1 } : state;
export const store = createStore(reducer);

// Component
import { useSelector, useDispatch } from 'react-redux';
function Counter() {
  const count = useSelector(state => state.count);
  const dispatch = useDispatch();
  return <button onClick={() => dispatch({ type: 'INC' })}>{count}</button>;
}
```

## Apollo Client (GraphQL) / React Query
- Apollo: Full GraphQL client with caching/queries. 
- React Query (TanStack Query): REST/GraphQL data syncing, optimistic updates.

```jsx
import { useQuery, useMutation, QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

function App() {
  return <QueryClientProvider client={queryClient}><Todos /></QueryClientProvider>;
}

function Todos() {
  const { data } = useQuery(['todos'], () => fetch('/api/todos').then(res => res.json()));
  const mutation = useMutation((newTodo) => fetch('/api/todos', { method: 'POST', body: JSON.stringify(newTodo) }));
  return (
    <ul>
      {data?.map(todo => <li key={todo.id}>{todo.text}</li>)}
      <button onClick={() => mutation.mutate({ text: 'New' })}>Add</button>
    </ul>
  );
}
```

## Routing (React Location / React Router)
- Client-side navigation. 
- React Router: Declarative routes. 
- React Location: TanStack's data-aware router.

```jsx
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <nav><Link to="/">Home</Link></nav>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
      </Routes>
    </BrowserRouter>
  );
}
```

## Styling (Styled Components / Emotion / Tailwind CSS / Chakra UI / Material UI / Ant Design)

- CSS-in-JS (Styled/Emotion: Tagged templates) vs utility (Tailwind: Classes) vs component libs (Chakra/MUI/Ant: Pre-built UI).

```jsx
import styled from 'styled-components';

const Button = styled.button`
  background: ${props => props.primary ? 'blue' : 'gray'};
  color: white;
`;

function App() {
  return <Button primary>Click</Button>;
}
```

## Forms (Formik / React Hook Form)
- Form libs for validation, state. 
- Formik: Component-driven. 
- React Hook Form: Hook-based, uncontrolled.

```jsx
import { useForm } from 'react-hook-form';

function Login() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const onSubmit = data => console.log(data);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register('email', { required: true })} />
      {errors.email && <p>Error</p>}
      <button type="submit">Submit</button>
    </form>
  );
}
```

## Testing (Jest + React Testing Library / Cypress)

- Jest/RTL: Unit/integration (render, fire events). Cypress: E2E browser testing.

```jsx
import { render, screen, fireEvent } from '@testing-library/react';
import Counter from './Counter';

test('increments', () => {
  render(<Counter />);
  fireEvent.click(screen.getByText('Increment'));
  expect(screen.getByText('1')).toBeInTheDocument();
});
```

