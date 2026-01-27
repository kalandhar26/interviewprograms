# State

- Imagine you have a remote-control toy car. When you press forward, the car moves. When you press stop, it stops
- When you press reverse, it goes back. The car’s condition keeps changing. In React, this changing condition is called
  state.
- State is private memory of a component. When state changes, React automatically updates the screen so the user sees
  the latest situation.

- In React, state is a built-in object that is used to store and manage data in a component.
- When the state changes, React automatically re-renders the component to reflect the updated data in the UI.
- It allows a component to have memory, enabling it to keep track of certain values between renders.
- State is mutable, meaning that it can be changed over time in response to user actions or other events.

## useState

```jsx
import { useState } from "react";

function Counter() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(count + 1)}>
        Increase
      </button>
    </div>
  );
}

export default Counter;
```

## setState

- setState is a mechanism used to update the state of a React component. When state is updated using setState, React
  schedules a re-render of the component to reflect the new state.
- In class components, setState() is used.
- In function components, state updates are handled using setter functions returned by useState, which internally work
  like setState.

### Class Component (useState)

```jsx
class Counter extends React.Component {
  state = {
    count: 0
  };

  increment = () => {
    this.setState({ count: this.state.count + 1 });
  };

  render() {
    return (
      <div>
        <h2>Count: {this.state.count}</h2>
        <button onClick={this.increment}>Increase</button>
      </div>
    );
  }
}
```

### Function Component (useState Modern React)

```jsx
import { useState } from "react";

function Counter() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(count + 1)}>
        Increase
      </button>
    </div>
  );
}

export default Counter;
```

- setCount is modern React’s version of setState.

| Props              | State                    |
|--------------------|--------------------------|
| Passed from parent | Managed inside component |
| Read-only          | Mutable                  |
| External data      | Internal data            |

### Important setState Rules

1. setState does not always update the state immediately. Instead, it plans an update to the state and updates the
   component in the next rendering phase.
2. setState can be called inside the component or inside the functions called by the component.
3. setState can be called inside the component or inside the functions called by the component.
4. setState can be called inside the component or inside the functions called by the component.
5. State updates are asynchronous and may be batched, so functional updates should be used when the new state depends on
   the previous state.
