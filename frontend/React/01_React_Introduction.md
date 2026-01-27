# React Fundamentals

## 1. Create React App

- Create React App (CRA) is an official tool from the React team to bootstrap a new React project with zero
  configuration.
- It sets up a development environment with Babel for transpiling JSX/ES6+, Webpack for bundling, and tools for
  testing/hot reloading.
- As of React 18 (and into 2025), it's still viable but often supplemented or replaced by faster alternatives like Vite
  for new projects.

```bash
# Modern setup (recommended)
npm create vite@latest my-app -- --template react
cd my-app
npm install
npm run dev

# Legacy CRA (still works)
npx create-react-app my-app --template typescript
```

## 1. What is React and Why is React?

- React is an open-source JavaScript library used for building component-based user interfaces, primarily for Single
  Page Applications (SPAs).
- It uses a **Virtual DOM** to efficiently update the UI by re-rendering only the components whose state or props have
  changed. React follows a declarative programming model and unidirectional data flow, which improves application
  performance, maintainability, and scalability.

```jsx
function Greeting() {
  return <h2>Hello React!</h2>;
}

export default Greeting;
```

- Greeting is a React component
- It returns UI using JSX
- React renders it on the browser
- Only this component updates when its data changes

### Why React?

- Fast updates using Virtual DOM
- Component reusability
- One-way data flow (easy debugging)
- Great for dynamic, interactive UIs
- Huge community & ecosystem
- Works perfectly with REST APIs & microservices

## 2. Difference between MPA vs SPA?

- A Multi Page Application (MPA) loads a new HTML page from the server for every user action or navigation, resulting in
  full page reloads.

```html
<a href="/profile.html">Profile</a>
```

- A Single Page Application (SPA) loads a single HTML page initially and dynamically updates the content using
  JavaScript without reloading the entire page. SPAs rely heavily on client-side rendering and APIs for data exchange.

```jsx
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Link to="/profile">Profile</Link>

      <Routes>
        <Route path="/profile" element={<h2>Profile Page</h2>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

- React is commonly used to build SPAs.

## 3. React Environment SetUp?

- Create React App (CRA) is an official tool from the React team to bootstrap a new React project with zero
  configuration.
- It sets up a development environment with Babel for transpiling JSX/ES6+, Webpack for bundling, and tools for
  testing/hot reloading.
- As of React 18 (and into 2025), it's still viable but often supplemented or replaced by faster alternatives like Vite
  for new projects.
- React environment setup involves configuring the required tools and dependencies to develop, run, and build a React
  application. This typically includes installing Node.js, a package manager like npm, and a project scaffolding tool
  such as Vite or Create React App.
- Modern React projects prefer Vite due to faster startup and better performance.
