# Server Components

- Imagine you are at a restaurant 🍽️:
- Normally, you cook everything yourself → takes time and effort
- But now, the chef prepares some dishes in the kitchen 🧑‍🍳 and serves them ready-to-eat
- You just assemble or eat them
- In React: Server Components let the server pre-render parts of the UI
- Only the necessary minimal code goes to the browser
- Reduces JS bundle size, faster load, smoother UX

## Professional Definition

- Server Components are React components that are rendered on the server side, allowing developers to fetch data and
  render UI on the server, then stream it to the client.
  They help reduce bundle size, improve performance, and integrate server-side data fetching seamlessly.
- Cannot use client-only hooks (useState, useEffect)
- Can import and use other server or client components
- Ideal for data-heavy or static content
- Part of React 18+ architecture