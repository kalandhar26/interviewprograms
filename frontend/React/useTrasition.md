# useTransition

- Imagine you are scrolling a huge photo album 📖.
- Normally, opening a new page takes time ⏳ and freezes your fingers 😵
- With useTransition, React says:
- “Don’t worry! You can keep scrolling while I load the new photos slowly”
- Urgent work (scrolling, clicking) happens immediately
- Non-urgent work (loading heavy content) happens in the background
- Result: smooth and responsive app even with large data

## Professional Definition

- The useTransition hook in React 18+ allows developers to mark state updates as non-urgent, letting React defer these
  updates and keep the UI responsive.
  It returns an array: [isPending, startTransition]
    1. isPending → boolean indicating pending state
    2. startTransition → wraps non-urgent updates

- Works with concurrent rendering
- Improves UX for heavy state updates
- Ideal for filtering, searching, or loading large lists

```jsx
import React, { useState, useTransition } from "react";

function App() {
  const [text, setText] = useState("");
  const [list, setList] = useState([]);
  const [isPending, startTransition] = useTransition();

  const handleChange = (e) => {
    const value = e.target.value;
    setText(value); // urgent update

    startTransition(() => {
      const newList = Array.from({ length: 10000 }, (_, i) => value + i);
      setList(newList); // non-urgent update
    });
  };

  return (
    <div>
      <input value={text} onChange={handleChange} placeholder="Type here..." />
      {isPending && <p>Loading list...</p>}
      <ul>
        {list.map((item, idx) => (
          <li key={idx}>{item}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;
```

- Typing stays smooth
- Loading indicator shows for deferred updates