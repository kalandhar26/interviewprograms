# useEffect

- Imagine you have a magical diary. Whenever you write something new, the diary automatically:
- Sends a message, Updates a chart, Rings a bell.
- You don’t have to do anything extra — it reacts automatically to changes.
- In React, useEffect is like that magical diary.
- It runs some code after your component renders or when certain values change.
- Fetching data when a page loads, Updating the title of the page, Listening for window size changes.

## Professional definition:

- useEffect is a React Hook that lets you perform side effects in functional components, such as data fetching,
  subscriptions, or manually changing the DOM.
- useEffect is a React Hook that lets you perform side effects in function components.

### Side effects include:

- Data fetching (API calls)
- DOM updates
- Subscriptions
- Timers
- **useEffect runs after the render and can be configured to run:**
- Every render
- Only once (on mount)
- When specific state or props change

### Run After Every Render

```jsx
import { useState, useEffect } from "react";

function Counter() {
  const [count, setCount] = useState(0);

  useEffect(() => {
    console.log("Component rendered or updated!");
  });

  return (
    <div>
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(count + 1)}>Increase</button>
    </div>
  );
}
```

### Run Only Once

```jsx
useEffect(() => {
  console.log("Component mounted!");
}, []);
```

### Run When Specific Values Changes

```jsx
useEffect(() => {
  console.log("Count changed to:", count);
}, [count]);
```

## Simple Code Example

```jsx
import React, { useState, useEffect } from 'react';

function Timer() {
  const [seconds, setSeconds] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setSeconds(s => s + 1);
    }, 1000);
    return () => clearInterval(interval); // Cleanup
  }, []); // Empty array: runs once

  return <p>Seconds: {seconds}</p>;
}
```

## Complex Code Example

```jsx
import React, { useState, useEffect } from 'react';

function WeatherApp() {
  const [weather, setWeather] = useState(null);
  const [city, setCity] = useState('New York');

  useEffect(() => {
    if (city) {
      fetch(`https://api.weather.com/v1/current?city=${city}`)
        .then(res => res.json())
        .then(data => setWeather(data))
        .catch(err => console.error('Weather fetch failed:', err));
    }
  }, [city]); // Runs when city changes

  useEffect(() => {
    const handleResize = () => console.log('Window resized');
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize); // Cleanup
  }, []); // Runs once, cleans up on unmount

  return (
    <div>
      <input value={city} onChange={e => setCity(e.target.value)} />
      {weather && <p>Temperature in {city}: {weather.temp}°C</p>}
    </div>
  );
}
```

## Important Rules (Interview Must-Know)

- Cleanup is needed for subscriptions or timers:

```jsx
useEffect(() => {
  const interval = setInterval(() => console.log("tick"), 1000);
  return () => clearInterval(interval); // cleanup
}, []);
```

- Never call Hooks conditionally.
- useEffect runs after rendering, not before.
- Dependencies array controls when effect runs.