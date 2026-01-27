# ForwardRef

- Imagine you have a secret tunnel 🕳️ in your house.
- Your friend wants to directly reach the kitchen 🍳 without opening every room.
- Instead of going through living room → hallway → kitchen, You hand them a shortcut directly to the kitchen door.
- In React: Normally, parent can’t access child’s DOM directly. forwardRef acts like that shortcut
- Parent gets direct reference to the child DOM node or component instance.

- **forwardRef** is a React API that allows a parent component to pass a ref through a child component to access a DOM
  element or child component instance. It’s mainly used for manipulating DOM nodes or integrating with third-party
  libraries.

## forwardRef Component

```jsx
import React from "react";

const CustomInput = React.forwardRef((props, ref) => {
  return <input ref={ref} {...props} />;
});
```

## using forwardRef

```jsx
function App() {
  const inputRef = React.useRef();

  const focusInput = () => {
    inputRef.current.focus();
  };

  return (
    <>
      <CustomInput ref={inputRef} placeholder="Type here..." />
      <button onClick={focusInput}>Focus Input</button>
    </>
  );
}

export default App;
```

- Output: Clicking the button focuses the input