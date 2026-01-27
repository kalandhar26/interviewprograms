# useRedux

- Imagine your app is a big school 🏫. Many classrooms (components) need the same information
- like school timing, holiday list, or student count.
- If every classroom keeps its own notebook (state), it becomes messy 😵‍💫.
- So the school keeps one big register in the office 📒.
- That big register = Redux Store
- useSelector + useDispatch = useRedux
- Redux is a predictable state management library used to manage global application state in a single immutable store.
- React-Redux provides hooks (useSelector, useDispatch) that allow React functional components to:
- Subscribe to the Redux store
- Dispatch actions to update the state
- This eliminates prop drilling, ensures single source of truth, and makes state changes predictable and traceable.

| Concept     | Meaning                                      |
|-------------|----------------------------------------------|
| Store       | Central state container                      |
| Action      | Plain object describing *what happened*      |
| Reducer     | Pure function describing *how state changes* |
| useSelector | Reads data from store                        |
| useDispatch | Sends actions to store                       |

## Store Setup

```jsx
// store.js
import { configureStore } from "@reduxjs/toolkit";
import counterReducer from "./counterSlice";

export const store = configureStore({
  reducer: {
    counter: counterReducer,
  },
});
```

## Reducer(Slice)

```jsx
// counterSlice.js
import { createSlice } from "@reduxjs/toolkit";

const counterSlice = createSlice({
  name: "counter",
  initialState: { value: 0 },
  reducers: {
    increment: (state) => {
      state.value += 1;
    },
    decrement: (state) => {
      state.value -= 1;
    },
  },
});

export const { increment, decrement } = counterSlice.actions;
export default counterSlice.reducer;
```

## Provide Store to React

```jsx
// main.jsx or index.js
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { store } from "./store";
import App from "./App";

ReactDOM.createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <App />
  </Provider>
);
```

## useRedux in Component

```jsx
// Counter.jsx
import { useSelector, useDispatch } from "react-redux";
import { increment, decrement } from "./counterSlice";

function Counter() {
  const count = useSelector((state) => state.counter.value);
  const dispatch = useDispatch();

  return (
    <div>
      <h2>Count: {count}</h2>
      <button onClick={() => dispatch(increment())}>+</button>
      <button onClick={() => dispatch(decrement())}>-</button>
    </div>
  );
}

export default Counter;
```