## Refs
- Refs provide direct access to DOM nodes or component instances, bypassing React's render cycle. Use useRef hook in functionals.

```jsx
import { useRef } from 'react';

function TextInput() {
  const inputRef = useRef(null);

  const focusInput = () => inputRef.current.focus();

  return (
    <>
      <input ref={inputRef} />
      <button onClick={focusInput}>Focus</button>
    </>
  );
}
```