# Higher Order Components

- Imagine you have a plain toy car 🚗.
- It’s functional but basic. You want to add lights, sounds, and remote control.
- Instead of making a new car from scratch, you wrap your car with new features:
- Car + Lights = better car, Car + Lights + Sounds = even better
- In React: A HOC is like a feature wrapper for components.
- It takes a component, adds functionality, and returns a new enhanced component.
- Example: Adding logging, authentication, or theme without modifying the original component.

## Professional Definition

- A Higher Order Component (HOC) is a function that takes a React component as input and returns a new component with
  added props, state, or behavior.
  It is used to reuse logic across multiple components without repeating code.

### HOC Definition

```jsx
function withLogger(WrappedComponent) {
  return function EnhancedComponent(props) {
    console.log("Rendering:", WrappedComponent.name);
    return <WrappedComponent {...props} />;
  };
}
```

### Using HOC

```jsx
function Button(props) {
  return <button>{props.label}</button>;
}

// Wrap Button with logging feature
const ButtonWithLogger = withLogger(Button);

export default function App() {
  return <ButtonWithLogger label="Click Me" />;
}

```

- Logs "Rendering: Button" every time the component renders
- Original Button component remains untouched

### Real-World Examples of HOC

- withAuth → wraps components to restrict access
- withTheme → injects theme props
- withErrorBoundary → wraps components for error handling