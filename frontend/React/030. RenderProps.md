# Render Props

- Imagine you have a magic box 📦. You don’t know exactly what will happen inside the box.
- But you get a special key 🗝️ that lets you decide how to use what comes out.
- In React: A Render Prop is like that key. A component provides data or behavior, You decide how to render it using a
  function passed as a prop.

## Professional Definition

- A Render Prop is a technique where a React component accepts a function as a prop and calls it to determine what to
  render.
- It’s used for sharing reusable logic between components while retaining flexibility in rendering.
- Prop name is usually render or children (function)
- The function receives data or callbacks
- Avoids HOC nesting issues

### Render Prop Component

```jsx
function MouseTracker({ render }) {
  const [position, setPosition] = React.useState({ x: 0, y: 0 });

  const handleMouseMove = (e) => {
    setPosition({ x: e.clientX, y: e.clientY });
  };

  return (
    <div onMouseMove={handleMouseMove} style={{ height: "200px", border: "1px solid black" }}>
      {render(position)}
    </div>
  );
}
```

### Using Render Prop

```jsx
function App() {
  return (
    <MouseTracker
      render={({ x, y }) => <h3>Mouse is at ({x}, {y})</h3>}
    />
  );
}

export default App;
```

- Displays current mouse coordinates as you move inside the div