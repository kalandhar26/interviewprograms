# useEffect

- Think of useEffect like a reminder note on your fridge. Every time you open the fridge (your app loads or changes), it
  checks the note and does something, like calling your mom to say you're home safe (like fetching new game levels from
  the internet). You can set it to only remind you once or every time.

## Professional definition:

- useEffect is a React Hook that lets you perform side effects in functional components, such as data fetching,
  subscriptions, or manually changing the DOM. It runs after every render by default but can be controlled with a
  dependency array. Cleanup functions can be returned for unmounting.

## PseudoCode:

```text
  textAfter component renders:
  If dependencies changed (or empty array for once):
  Run the effect code (e.g., fetch data)
  Optional: Return a cleanup function to run before next effect or unmount
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