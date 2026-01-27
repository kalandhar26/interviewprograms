## HTTP Requests (GET and POST)

- Fetching/sending data via Fetch API or Axios. GET retrieves; POST submits. Integrate with useEffect/useState.

```jsx
import { useState, useEffect } from 'react';

function UserForm() {
  const [users, setUsers] = useState([]);
  const [name, setName] = useState('');

  useEffect(() => {
    fetch('/api/users') // GET
      .then(res => res.json())
      .then(setUsers);
  }, []);

  const handleSubmit = e => {
    e.preventDefault();
    fetch('/api/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name })
    }).then(() => setName(''));
  };

  return (
    <form onSubmit={handleSubmit}>
      <input value={name} onChange={e => setName(e.target.value)} />
      <button type="submit">Add</button>
      <ul>{users.map(u => <li key={u.id}>{u.name}</li>)}</ul>
    </form>
  );
}
```

- **New React 18 Features:**
- **Automatic Batching:** Multiple state updates batched
- **Transitions:** Mark updates as non-urgent
- **Suspense for Data Fetching:** Better loading states
- **New Hooks:** useId, useSyncExternalStore

