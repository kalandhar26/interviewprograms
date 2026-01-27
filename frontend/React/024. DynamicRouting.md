# Dynamic Routing

- Imagine a school notice board 🏫. Instead of writing: Student 1 details, Student 2 details, Student 3 details
- You write one template: “Details of Student ___”, And just change the roll number. That blank space is Dynamic
  Routing.
- Dynamic Routing allows React Router to render the same component for different URL paths by capturing dynamic segments
  of the URL using route parameters.

## Why Dynamic Routing Is Important

✅ Avoids duplicate routes
✅ Reusable components
✅ Cleaner URLs
✅ Real-world use cases (user, product, order pages)

## How It Works

- Use :paramName in route
- Read it using useParams()

###  Define Dynamic Route

```jsx
<Route path="/user/:id" element={<User />} />
```
### Read URL Parameter

```jsx
import { useParams } from "react-router-dom";

function User() {
  const { id } = useParams();

  return <h2>User ID: {id}</h2>;
}

```