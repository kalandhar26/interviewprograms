# Event Handling

- Imagine you have a doorbell. You press the bell, Sound comes, Someone opens the door.
- Pressing the bell is an event. The sound and door opening is the response.
- In React, event handling is the process of responding to user actions such as clicks, key presses, and form
  submissions.
- Event handling in React allows developers to capture and respond to user interactions such as clicks, form inputs, and
  keyboard actions. React uses synthetic events, which provide a consistent event interface across browsers.
- Event handlers are passed as functions, not strings.
- Event handling in React allows responding to user interactions using synthetic events and function-based handlers.

## Button Click Event

```jsx
function ButtonClick() {
  function handleClick() {
    alert("Button clicked!");
  }

  return <button onClick={handleClick}>Click Me</button>;
}

export default ButtonClick;
```

## Inline Event Handling

```jsx
<button onClick={() => console.log("Clicked")}>
  Click
</button>
```

## Passing Arguments to Events

```jsx
function greet(name) {
  alert(`Hello ${name}`);
}

<button onClick={() => greet("Baba")}>
  Greet
</button>
```

## Event Handling Rules (Interview Focus)

- Use camelCase (onClick, not onclick)
- Pass function reference, not function call
- Uses SyntheticEvent
- this binding not needed in function components