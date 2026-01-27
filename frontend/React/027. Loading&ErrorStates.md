# Loading and Error States

- Imagine you are waiting for pizza delivery 🍕. You don’t just sit silently 🫣
- Instead, the app shows: “Your pizza is on the way… 🍕” ,
- And if the delivery fails: “Oops! Pizza couldn’t be delivered.
- In React, Loading & Error States do the same:
- Loading → user knows data is coming
- Error → user knows something went wrong
- This makes your app friendly and professional.
- Loading and Error States are UI indicators that reflect the current status of asynchronous operations in React. They
  improve user experience by showing feedback while waiting for data or handling API failures gracefully.

## Why It Matters

### Without loading/error states:

- Blank screen
- Confused users
- Poor UX

### With proper handling:

- Smooth experience
- Professional feel
- Easier debugging

##  Loading & Error with fetch

```jsx
import { useState, useEffect } from "react";

function Users() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then(res => {
        if (!res.ok) throw new Error("Failed to fetch");
        return res.json();
      })
      .then(data => {
        setUsers(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading users...</p>;
  if (error) return <p>Error: {error}</p>;

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