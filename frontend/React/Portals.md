# Portals

- Imagine you are throwing a party at home 🎉. You want to show fireworks outside in the backyard.
- You don’t build fireworks inside your living room 🏠❌ Instead, you render them directly outside 🧨✅
- In React: Sometimes you want a component to render outside its parent DOM hierarchy.
- Example: modals, tooltips, popovers
- React Portals let you do exactly that, Component stays logically in React tree but appears somewhere else in the DOM

## Profession Definition

- React Portals provide a way to render a component’s children into a DOM node outside the parent component hierarchy,
  while maintaining React’s event handling and state management.
- Useful for modals, tooltips, dropdowns
- Keeps component logically in React tree
- DOM-wise, rendered elsewhere

```html
<body>
  <div id="root"></div>
  <div id="modal-root"></div> <!-- Portal target -->
</body>

```

### Portal Component

```jsx
import React from "react";
import ReactDOM from "react-dom";

function Modal({ children }) {
  return ReactDOM.createPortal(
    <div className="modal">{children}</div>,
    document.getElementById("modal-root")
  );
}

export default Modal;
```

### using Portals

```jsx
import Modal from "./Modal";

function App() {
  const [show, setShow] = React.useState(false);

  return (
    <>
      <button onClick={() => setShow(true)}>Open Modal</button>
      {show && (
        <Modal>
          <h2>Hello from Modal!</h2>
          <button onClick={() => setShow(false)}>Close</button>
        </Modal>
      )}
    </>
  );
}

export default App;
```
- Modal appears outside root div, still fully functional