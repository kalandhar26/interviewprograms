# Concurrant Features

- Imagine you are painting a big mural 🎨. You have limited time
- Instead of painting everything in one go, you: Paint the background first, Slowly add details
- If someone walks in, you pause, let them see progress, then continue
- In React: Concurrent features let React split work into small chunks
- UI updates happen without freezing the screen, Heavy rendering doesn’t block user interactions

## Professional Definition

- Concurrent Features in React 18+ are a set of capabilities that allow React to interrupt, pause, and resume rendering
  work to keep the UI responsive. Key features include automatic batching, transitions, and Suspense improvements.
- Prioritizes urgent updates (typing, clicks)
- Defers non-urgent updates (slow loading components)
- Works with startTransition, Suspense, and Concurrent rendering.

### StartTransition

```jsx
import React, { useState, startTransition } from "react";

function App() {
  const [text, setText] = useState("");
  const [list, setList] = useState([]);

  const handleChange = (e) => {
    const value = e.target.value;
    setText(value);

    // Non-urgent update
    startTransition(() => {
      const newList = Array.from({ length: 10000 }, (_, i) => value + i);
      setList(newList);
    });
  };

  return (
    <div>
      <input value={text} onChange={handleChange} placeholder="Type here..." />
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

- Typing in the input remains smooth
- Large list renders in the background