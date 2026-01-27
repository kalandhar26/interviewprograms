## Error Boundaries

- Imagine you are watching a movie in a theater 🎬.
- Suddenly: One speaker breaks 🔊 Should the entire movie stop? ❌
- No. Only that speaker is muted.
- Movie continues with a message: “Sound issue in this section”
- That’s exactly what Error Boundaries do in React.
- If one component crashes, React: Stops rendering that part, Shows a fallback UI .Keeps the rest of the app alive
- Components that catch JS errors in child trees, log them, and render fallbacks. Class-only (no hook equivalent yet in
  2025).
- Error Boundaries are React components that catch JavaScript errors in their child component tree during rendering,
  lifecycle methods, and constructors, and display a fallback UI instead of crashing the entire application.

## What Error Boundaries Catch

✅ Rendering errors
✅ Lifecycle errors
✅ Constructor errors

### Error Boundary Component (Class-Based)

```jsx
import React from "react";

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, info) {
    console.error(error, info);
  }

  render() {
    if (this.state.hasError) {
      return <h2>Something went wrong.</h2>;
    }

    return this.props.children;
  }
}

export default ErrorBoundary;
```
- usage
```text
<ErrorBoundary>
  <BuggyComponent />
</ErrorBoundary>
```
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