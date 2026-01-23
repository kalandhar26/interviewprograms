# useRef

- useRef is like a secret drawer in your desk that holds a toy (or a note) forever, even if you clean your room a
  hundred times. You can peek in the drawer anytime to grab the toy without telling anyone or making the room messy
  again—perfect for remembering where your mouse cursor is without redrawing the whole picture.

## Professional definition:

- useRef is a React Hook that creates a mutable ref object whose .current property is initialized to the passed
  argument. It persists across re-renders without triggering them and is commonly used for accessing DOM nodes or
  storing mutable values like intervals.

## PseudoCode:

```text
textCreate ref with initial value (e.g., null for DOM)
If targeting DOM: Attach ref to element
Access/modify: ref.current = newValue or element.focus()
Ref survives re-renders without causing them
```

## Easy Code Example:

```jsx
jsximport React, { useRef } from 'react';

function TextInput() {
const inputRef = useRef(null);

const focusInput = () => {
inputRef.current.focus(); // Focus the input
};

return (
<div>
<input ref={inputRef} type="text" />
<button onClick={focusInput}>Focus the input</button>
</div>
);
}
```

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