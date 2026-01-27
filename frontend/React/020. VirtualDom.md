# VirtualDOM

- The Virtual DOM is a lightweight JavaScript representation of the real DOM.
  React uses it to efficiently compute the minimum set of changes required to update the UI, improving performance by
  reducing direct DOM manipulations.

## Reconciliation

- Reconciliation is the process by which React compares the previous Virtual DOM tree with the new one to determine the
  minimal set of changes required to update the real DOM efficiently.

## React.Memo

- React.memo is a higher-order component that memoizes a functional component and prevents unnecessary re-renders by
  performing a shallow comparison of props.
- If props do not change, the component is not re-rendered.

### Without React.memo:

- Child components re-render unnecessarily
- Performance issues in large apps

```jsx
function Child({ name }) {
  console.log("Child rendered");
  return <h3>Hello {name}</h3>;
}

function Parent() {
  const [count, setCount] = React.useState(0);

  return (
    <>
      <Child name="Baba" />
      <button onClick={() => setCount(count + 1)}>+</button>
    </>
  );
}
```

- Child renders every time button is clicked ❌

### With React.memo:

```jsx
const Child = React.memo(function Child({ name }) {
  console.log("Child rendered");
  return <h3>Hello {name}</h3>;
});
```

- Parent re-renders
- Child stays untouched if name doesn’t change ✅
- Skips re-rendering
- Works perfectly with useCallback
- Optimizes reconciliation process

## Code Splitting

- Code Splitting is a performance optimization technique where the application bundle is divided into smaller chunks
  that are loaded on demand, reducing initial load time and improving user experience.

## Lazy Loading

- Lazy Loading is a technique where components or resources are loaded dynamically at runtime, only when they are
  needed, improving initial load time and application performance.

| Code Splitting   | Lazy Loading             |
|------------------|--------------------------|
| Concept          | Implementation           |
| Dividing bundles | Loading chunks on demand |
| Tool-agnostic    | Uses `React.lazy()`      |

### Lazy Load Component

```jsx
import { lazy } from "react";

const Dashboard = lazy(() => import("./Dashboard"));

```

### Using Suspense

```jsx
import { Suspense } from "react";

<Suspense fallback={<h2>Loading Dashboard...</h2>}>
  <Dashboard />
</Suspense>
```

- Component loads only when rendered.

### Lazy loading with Routing

```jsx
const Home = lazy(() => import("./Home"));
const Profile = lazy(() => import("./Profile"));

<Route
  path="/profile"
  element={
    <Suspense fallback={<div>Loading...</div>}>
      <Profile />
    </Suspense>
  }
/>
```

- Route-based lazy loading is most common in production.

## Suspense

- Suspense is a React component that allows you to handle loading states declaratively by showing a fallback UI while
  waiting for asynchronous operations like lazy-loaded components.

### Basic Suspense

```jsx
import { Suspense, lazy } from "react";

const Dashboard = lazy(() => import("./Dashboard"));

<Suspense fallback={<h2>Loading...</h2>}>
  <Dashboard />
</Suspense>

```

## Multiple Lazy Components

```jsx
<Suspense fallback={<Spinner />}>
  <Home />
  <Profile />
</Suspense>
```

- One Suspense can wrap multiple lazy components.
- Suspense does not replace loaders manually
- It works with: React.lazy, Concurrent features (future-ready)
- Fallback can be: Spinner, Skeleton, Text