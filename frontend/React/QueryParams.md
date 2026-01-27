# Query Params

- Imagine you go to a shopping mall 🛒 and ask: “Show me shirts, size M, color blue.”
- You’re still in the same shop, but you added filters. Those filters are Query Parameters.
- In URLs: Path = where you are, Query params = extra information

## Professional Definition

- Query Parameters are key-value pairs appended to a URL that provide additional data to a route without changing the
  route structure, commonly used for filtering, sorting, and pagination.

| Route Params      | Query Params       |
|-------------------|--------------------|
| Required          | Optional           |
| `/user/:id`       | `/user?id=10`      |
| useParams         | useSearchParams    |
| Identify resource | Filter/modify data |

### Reading Query Params (React Router v6)

```jsx
import { useSearchParams } from "react-router-dom";

function Products() {
  const [searchParams] = useSearchParams();

  const category = searchParams.get("category");
  const page = searchParams.get("page");

  return (
    <>
      <h3>Category: {category}</h3>
      <h3>Page: {page}</h3>
    </>
  );
}
```

### Setting Query Params

```jsx
const [searchParams, setSearchParams] = useSearchParams();

setSearchParams({ category: "books", page: 2 });
```