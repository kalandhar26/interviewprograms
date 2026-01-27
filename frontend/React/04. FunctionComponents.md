## 2. Core Concepts

- A React component is a reusable, independent piece of UI. Function components are JavaScript functions that return JSX
  and define how a part of the UI should appear based on given data.
- Modern React primarily uses function components with hooks instead of class components.
- A React component is a reusable UI building block, and function components are JavaScript functions that return JSX.

### 2.1 Components: The Building Blocks

- **Functional Components (Always Use These)**
- Functional components are JavaScript functions that return JSX (React elements). - They're the preferred way to define
  UI in modern React (post-hooks era), stateless by default but can manage state via hooks.
- In React 18, they support concurrent features like transitions.

#### Simple Functional Component

```jsx
function Header() {
  return <h1>Welcome to My App</h1>;
}

export default Header;
```

#### Using Component Inside Another Component

```jsx
function App() {
  return (
    <div>
      <Header />
      <p>This is the main content</p>
    </div>
  );
}

````

```jsx
// Modern arrow function style
const UserCard = ({ name, age }) => {
  return (
    <div className="user-card">
      <h2>{name}</h2>
      <p>Age: {age}</p>
    </div>
  );
};

// Usage
<UserCard name="John" age={25} />;
```

- **When to Use:** 99% of your components
- **Why:** Cleaner, hooks-compatible, better performance
- **Tip:** Use React.memo() for pure components that don't need re-rendering

- **Class Components (Legacy - Avoid for New Code)**
- Class components extend React.Component and use render() to return JSX.
- They support internal state and lifecycle methods.
- Are legacy in React 18 – hooks make functionals equivalent and simpler.
- Component name must start with Capital letter
- Returns JSX
- One component = one responsibility
- Can be reused anywhere

```jsx
// Only for maintaining old code
class UserCard extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}
```

- When Not to Use: New projects, refactoring old code gradually
- l̥Migration Path: Convert to functional components with hooks