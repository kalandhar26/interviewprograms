# useRef

- Imagine you have a sticky note on your desk. You can write something on it. You can look at it anytime
- You don’t need to tell anyone else In React, useRef is like that sticky note.
- It stores a value or a reference that persists across renders, without causing the component to re-render.
- It’s also used to directly access a DOM element, like focusing an input box.

- useRef is a React Hook that returns a mutable object whose .current property can store a value or a DOM reference.
- useRef is a hook that provides a mutable object to persist values across renders or access DOM elements directly.
- It is commonly used for:
- Accessing DOM elements directly
- Persisting values across renders without causing re-renders
- Storing timers, previous state, or counters

## Access DOM Element

```jsx
import { useRef } from "react";

function InputFocus() {
  const inputRef = useRef(null);

  const focusInput = () => {
    inputRef.current.focus();
  };

  return (
    <div>
      <input ref={inputRef} type="text" placeholder="Type here..." />
      <button onClick={focusInput}>Focus Input</button>
    </div>
  );
}

export default InputFocus;

```

## Persist Value Across Renders

```jsx
import { useRef, useState, useEffect } from "react";

function Timer() {
  const countRef = useRef(0);
  const [count, setCount] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      countRef.current += 1; // persists without re-render
      setCount(countRef.current); // triggers re-render
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  return <h2>Timer: {count}</h2>;
}
```

## Important Rules (Interview Must-Know)

- useRef does not cause re-render when .current changes
- Can be used for DOM manipulation
- Ideal for storing mutable values across renders
- Avoid using it for state that affects UI directly

## Complex Code Example:

```jsx
jsximport React, { useRef, useState } from 'react';

function VideoPlayer() {
const videoRef = useRef(null);
const [isPlaying, setIsPlaying] = useState(false);

const handlePlay = () => {
videoRef.current.play();
setIsPlaying(true);
};

const handleStop = () => {
videoRef.current.pause();
videoRef.current.currentTime = 0; // Reset to start
setIsPlaying(false);
};

const prevVolume = useRef(1); // Store previous volume without re-render

const changeVolume = (newVol) => {
prevVolume.current = videoRef.current.volume; // Save old
videoRef.current.volume = newVol;
};

return (
<div>
<video ref={videoRef} width="400" src="video.mp4" />
<button onClick={handlePlay} disabled={isPlaying}>Play</button>
<button onClick={handleStop}>Stop</button>
<input
type="range"
min="0"
max="1"
step="0.1"
defaultValue="1"
onChange={e => changeVolume(e.target.value)}
/>
<p>Last volume: {prevVolume.current}</p>
</div>
);
}
```