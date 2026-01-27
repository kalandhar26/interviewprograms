# API Calls

- Imagine you are sitting in a restaurant 🍽️. You don’t cook food yourself.
- You: Give order to waiter 🧑‍🍳, Wait ⏳, Food comes.
- In React: UI = you, API = kitchen, fetch / axios = waiter
- You ask for data, wait for response, then show it on screen.

- API calls in React are used to fetch data from external servers using HTTP methods, commonly implemented using fetch
  or third-party libraries like axios, usually inside useEffect.

| fetch               | axios            |
|---------------------|------------------|
| Built-in            | Third-party      |
| Manual JSON parsing | Auto JSON        |
| No interceptors     | Has interceptors |
| More boilerplate    | Cleaner syntax   |


### Use Fetch

```jsx
import { useEffect, useState } from "react";

function Users() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then(res => res.json())
      .then(data => setUsers(data));
  }, []);

  return (
    <ul>
      {users.map(user => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}

export default Users;
```

### Using Axios

```jsx
import axios from "axios";
import { useEffect, useState } from "react";

function Users() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/users")
      .then(res => setUsers(res.data));
  }, []);

  return (
    <ul>
      {users.map(user => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}
```