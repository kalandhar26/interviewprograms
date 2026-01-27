# React Router

- Imagine a big house 🏠. One room is Bedroom, another room is Kitchen, and another room is Study Room.
- You don’t build a new house every time you move rooms. You just walk to another room 🚶‍♂️. That’s how React apps work.
- Without routing: One page = one full reload, Website feels slow 😵
- With React Router: URL changes, Component changes, Page does not reload. React Router is the map of your house.

## Professional Definition

- React Router is a client-side routing library that enables navigation between different components in a single-page
  application (SPA) without reloading the page by syncing the UI with the browser URL.

### Why React Router is Needed?

- Without Router: Full page reload, State lost, Poor user experience
- With Router: SPA navigation, Fast transitions, Preserves state

### Core Concepts (v6+)

| Concept       | Purpose          |
|---------------|------------------|
| BrowserRouter | Wraps the app    |
| Routes        | Route container  |
| Route         | Path → Component |
| Link          | Navigation       |
| useParams     | URL params       |
| useNavigate   | Programmatic nav |

### Basic Routing

```jsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./Home";
import About from "./About";
import Contact from "./Contact";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

### Navigation Using Link

```jsx
import { Link } from "react-router-dom";

function Navbar() {
  return (
    <>
      <Link to="/">Home</Link> |{" "}
      <Link to="/about">About</Link> |{" "}
      <Link to="/contact">Contact</Link>
    </>
  );
}
```

### React Router v5 vs v6

| v5             | v6               |
|----------------|------------------|
| Switch         | Routes           |
| component prop | element prop     |
| exact needed   | exact by default |
| useHistory     | useNavigate      |
