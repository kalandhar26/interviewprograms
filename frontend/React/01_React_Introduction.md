# React Fundamentals

## 1. Create React App

- Create React App (CRA) is an official tool from the React team to bootstrap a new React project with zero
  configuration.
- It sets up a development environment with Babel for transpiling JSX/ES6+, Webpack for bundling, and tools for
  testing/hot reloading.
- As of React 18 (and into 2025), it's still viable but often supplemented or replaced by faster alternatives like Vite
  for new projects.

```bash
# Modern setup (recommended)
npm create vite@latest my-app -- --template react
cd my-app
npm install
npm run dev

# Legacy CRA (still works)
npx create-react-app my-app --template typescript
```

## 2. Core Concepts

### 2.1 Components: The Building Blocks

- **Functional Components (Always Use These)**
- Functional components are JavaScript functions that return JSX (React elements). - They're the preferred way to define
  UI in modern React (post-hooks era), stateless by default but can manage state via hooks.
- In React 18, they support concurrent features like transitions.

```jsx
// Modern arrow function style
const UserCard = ({ name, age }) => {
  return (
    <div className="user-card">
      <h2>{name}</h2>
      <p>Age: {age}</p>
    </div>
  );
};

// Usage
<UserCard name="John" age={25} />;
```

- **When to Use:** 99% of your components
- **Why:** Cleaner, hooks-compatible, better performance
- **Tip:** Use React.memo() for pure components that don't need re-rendering

- **Class Components (Legacy - Avoid for New Code)**
- Class components extend React.Component and use render() to return JSX.
- They support internal state and lifecycle methods.
- Are legacy in React 18 – hooks make functionals equivalent and simpler.

```jsx
// Only for maintaining old code
class UserCard extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}
```

- When Not to Use: New projects, refactoring old code gradually
- l̥Migration Path: Convert to functional components with hooks

### 2.2 JSX: JavaScript XML (JSX Components)

- JSX (JavaScript XML) is a syntax extension letting you write HTML-like code in JS.
- It's transpiled to React.createElement() calls.
- In React 18, it supports fragments (<>) and new JSX transform (no import needed if configured).

```jsx
// It's just JavaScript!
const element = <h1>Hello, {user.name}</h1>;

// Multi-line with parentheses
const list = (
  <ul>
    {items.map((item) => (
      <li key={item.id}>{item.name}</li>
    ))}
  </ul>
);

// Fragments for multiple elements
const Component = () => (
  <>
    <Header />
    <MainContent />
    <Footer />
  </>
);
```

**Rules:**

- One root element per return (use fragments <>)
- className not class
- htmlFor not for
- Events: onClick not onclick
- l̥Always close tags: <img /> not <img>

### 2.3 Props: Passing Data Down

- Props (properties) are read-only data passed from parent to child components, like function arguments. They're
  immutable within the child.

```jsx
// Parent Component
function App() {
  const user = { name: "Alice", role: "Admin" };
  return <Profile user={user} showDetails={true} />;
}

// Child Component
function Profile({ user, showDetails }) {
  return (
    <div>
      <h2>{user.name}</h2>
      {showDetails && <p>Role: {user.role}</p>}
    </div>
  );
}

// Default props
Profile.defaultProps = {
  showDetails: false,
};

// OR using destructuring default
function Profile({ user, showDetails = false }) {
  /* ... */
}
```

- **Best Practices:**
- Use destructuring for cleaner code
- Keep props read-only (immutable)
- Use TypeScript/PropTypes for validation
- Avoid prop drilling >3 levels (use Context)

### 2.4 State: Component Memory

- State is mutable data managed within a component, triggering re-renders on update. useState is the hook for adding
  state to functional components, returning [value, setter].

#### useState Hook

```jsx
import { useState } from "react";

function Counter() {
  // Declare state variable
  const [count, setCount] = useState(0);
  const [user, setUser] = useState({ name: "", age: 0 });

  // Functional update for previous state
  const increment = () => {
    setCount((prev) => prev + 1); // Correct
    // setCount(count + 1); // Less reliable
  };

  // Updating objects
  const updateUser = () => {
    setUser((prev) => ({
      ...prev, // Copy all properties
      age: prev.age + 1, // Update specific property
    }));
  };

  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={increment}>+</button>
      <button onClick={() => setCount(0)}>Reset</button>
    </div>
  );
}
```

- useState and useEffect Hooks (Combined as Core Hooks)
- useState manages local state (as above). useEffect handles side effects (e.g., API calls, subscriptions) after render,
  with dependency arrays for control.
- In React 18, useEffect supports concurrent cleanup.

- **When to Use State:**
- User input (forms, toggles)
- Data that changes over time
- UI state (loading, error, modal open)
- **When NOT to Use State:**
- Derived values (calculate from existing state)
- Static data (use constants)
- Shared data (lift up or use Context)
- **Important Rules:**
- Never modify state directly: state.count++ ❌
- State updates are asynchronous
- Multiple setStates may batch in React 18
- State is isolated to each component

#### setState and Component Lifecycle Methods

### 2.5 Event Handling

```jsx
function Form() {
  const [input, setInput] = useState("");

  // Handle click
  const handleClick = (event) => {
    console.log(event.target);
    event.preventDefault(); // Prevent default behavior
  };

  // Handle input with debouncing
  const handleChange = (event) => {
    setInput(event.target.value);
  };

  // Pass parameters
  const handleItemClick = (id) => {
    console.log("Clicked item:", id);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        value={input}
        onChange={handleChange}
        onFocus={() => console.log("focused")}
        onBlur={() => console.log("blurred")}
      />

      {/* Passing parameters */}
      <button onClick={() => handleItemClick(123)}>Click Me</button>

      {/* Or use data attributes */}
      <button
        data-id={123}
        onClick={(e) => handleItemClick(e.target.dataset.id)}
      >
        Click Me
      </button>
    </form>
  );
}
```

### 2.6 Conditional Rendering

- Rendering JSX based on conditions (e.g., if/else, ternaries) to show/hide elements dynamically.

```jsx
function UserStatus({ isLoggedIn, user }) {
  // 1. If-else (outside JSX)
  if (!isLoggedIn) {
    return <LoginButton />;
  }

  // 2. Ternary operator (in JSX)
  return (
    <div>
      <h1>Welcome back!</h1>
      {isLoggedIn ? <UserProfile user={user} /> : <GuestProfile />}

      {/* 3. Logical AND */}
      {isLoggedIn && <Dashboard />}

      {/* 4. Switch pattern */}
      {(() => {
        switch (user.role) {
          case "admin":
            return <AdminPanel />;
          case "user":
            return <UserPanel />;
          default:
            return <GuestPanel />;
        }
      })()}
    </div>
  );
}
```

### 2.7 Lists & Keys

- Rendering arrays of elements with map(). key prop uniquely identifies items for efficient reconciliation (React 18
  optimizes this with better diffing).

```jsx
function TodoList({ todos }) {
  // GOOD: Stable ID from data
  return (
    <ul>
      {todos.map((todo) => (
        <TodoItem
          key={todo.id} // Stable unique ID
          todo={todo}
        />
      ))}
    </ul>
  );

  // BAD: Index as key (causes bugs when list changes)
  // {todos.map((todo, index) => (
  //   <TodoItem key={index} todo={todo} /> // ❌
  // ))}
}

// For no IDs, generate stable keys
function generateKey(item) {
  return `${item.name}-${item.timestamp}`;
}
```

- **Key Rules:**
- Keys must be unique among siblings
- Keys must be stable across re-renders
- Keys help React identify which items changed

### 2.8 Forms

- Handling user input with uncontrolled (DOM-managed) or controlled (state-managed) components. Controlled is preferred
  for validation/sync.

```jsx
import { useState } from "react";

function SignupForm() {
  // Single state approach
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    agree: false,
  });

  // Handle all inputs with one function
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form data:", formData);
    // Submit to API
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleChange}
        required
      />

      <input
        type="password"
        name="password"
        value={formData.password}
        onChange={handleChange}
        minLength="8"
      />

      <label>
        <input
          type="checkbox"
          name="agree"
          checked={formData.agree}
          onChange={handleChange}
        />
        I agree to terms
      </label>

      <button type="submit">Sign Up</button>
    </form>
  );
}
```

- For complex forms: Use React Hook Form library

## Composition vs Inheritance

- Composition builds components by combining smaller ones (preferred); inheritance uses class extension (discouraged,
  leads to tight coupling).

```jsx
function Dialog({ title, children }) {
  return (
    <div className="dialog">
      <h2>{title}</h2>
      {children} {/* Slots for buttons, content */}
    </div>
  );
}
// Usage: <Dialog title="Hi"><p>Content</p><button>OK</button></Dialog>
```

## Higher-Order Components (HOCs) – Function returning enhanced component.

- A Higher-Order Component (HOC) is a function that takes a component as an argument and returns a new, enhanced
  component. It's a pattern in React for reusing component logic, such as adding props, state, or behavior (e.g.,
  authentication, data fetching, logging) without modifying the original component. HOCs promote composition over
  inheritance, making code more modular and testable.

- Basic HOC for Adding Props (withLogging)
- This HOC logs render events and injects a static prop.

```jsx
import React from 'react';

// HOC function
function withLogging(WrappedComponent) {
  return function LoggedComponent(props) {
    console.log(`Rendering ${WrappedComponent.name} with props:`, props);
    
    return <WrappedComponent {...props} extraProp="Logged!" />;
  };
}

// Base component
function Greeting({ name, extraProp }) {
  return <h1>Hello, {name}! {extraProp && `(Enhanced: ${extraProp})`}</h1>;
}

// Enhanced component
const LoggedGreeting = withLogging(Greeting);

// Usage in App
function App() {
  return <LoggedGreeting name="World" />;
}
```

- HOC with State and Effects (withDataFetching)
- This HOC fetches data on mount and passes it as props. Uses useState and useEffect for a functional approach (modern
  React 18 style).

```jsx
import React, { useState, useEffect } from 'react';

// HOC function (functional style)
function withDataFetching(WrappedComponent, fetchUrl) {
  return function DataFetchingComponent(props) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
      fetch(fetchUrl)
        .then(res => res.json())
        .then(setData)
        .catch(setError)
        .finally(() => setLoading(false));
    }, [fetchUrl]);

    // Pass enhanced props
    const enhancedProps = {
      ...props,
      data,
      loading,
      error,
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return <WrappedComponent {...enhancedProps} />;
  };
}

// Base component
function UserProfile({ userId, data }) {
  return (
    <div>
      <h2>User {userId}</h2>
      {data && <p>Name: {data.name}, Email: {data.email}</p>}
    </div>
  );
}

// Enhanced component (fetch users from a mock API)
const FetchedUserProfile = withDataFetching(UserProfile, 'https://jsonplaceholder.typicode.com/users/1');

// Usage in App
function App() {
  return <FetchedUserProfile userId={1} />;
}
```

- HOC for Authentication (withAuth) – Class-Based for Legacy
- For class components or advanced lifecycle needs.

```jsx
import React, { Component } from 'react';

// HOC function (class style)
function withAuth(WrappedComponent) {
  return class AuthenticatedComponent extends Component {
    state = { isAuthenticated: false, user: null };

    componentDidMount() {
      // Simulate auth check (e.g., from localStorage or API)
      const user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null;
      this.setState({ isAuthenticated: !!user, user });
    }

    render() {
      const { isAuthenticated, user } = this.state;

      if (!isAuthenticated) {
        return <div>Please log in.</div>;
      }

      return <WrappedComponent {...this.props} user={user} />;
    }
  };
}

// Base component
class Dashboard extends Component {
  render() {
    return (
      <div>
        <h2>Dashboard</h2>
        <p>Welcome, {this.props.user?.name}!</p>
      </div>
    );
  }
}

// Enhanced component
const AuthDashboard = withAuth(Dashboard);

// Usage in App (simulate login)
function App() {
  localStorage.setItem('user', JSON.stringify({ name: 'Alice' })); // Mock auth
  return <AuthDashboard />;
}
```