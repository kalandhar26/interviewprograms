# 2. Props

- Imagine your mother gives you a lunch box 🍱. Inside the lunch box: Rice, Curry, Sweet
- You didn’t cook it, you just received it. In React, props are like that lunch box.
- A parent component sends data to a child component. The child can use it, but cannot change it. Props are read-only
  gifts.
- Props are inputs passed from a parent component to a child component. They allow components to
  be configured and reused with different data.
- Props are immutable, meaning a child component cannot modify the props it receives.

## 2.1 Render Props

- Pattern passing a function as prop to share code; component renders based on that function's output. Less common
  post-hooks, but useful for logic sharing.

### 2.2 Props: Passing Data Down

- Props (properties) are read-only data passed from parent to child components, like function arguments. They're
  immutable within the child.

#### Passing Props

```jsx
function App() {
  return <Welcome name="Baba" />;
}
```

#### Receiving Props

```jsx
function Welcome(props) {
  return <h2>Hello, {props.name}</h2>;
}
```

#### Destructing Props

```jsx
function Welcome({ name }) {
  return <h2>Hello, {name}</h2>;
}
```

```jsx
// Parent Component
function App() {
  const user = { name: "Alice", role: "Admin" };
  return <Profile user={user} showDetails={true} />;
}

// Child Component
function Profile({ user, showDetails }) {
  return (
    <div>
      <h2>{user.name}</h2>
      {showDetails && <p>Role: {user.role}</p>}
    </div>
  );
}

// Default props
Profile.defaultProps = {
  showDetails: false,
};

// OR using destructuring default
function Profile({ user, showDetails = false }) {
  /* ... */
}
```

#### **Best Practices:**

- Use destructuring for cleaner code
- Keep props read-only (immutable)
- Use TypeScript/PropTypes for validation
- Avoid prop drilling >3 levels (use Context)

#### Props Rules (Interview Must-Know)

- Props flow one-way (parent ➝ child)
- Props are read-only
- Used for communication between components
- Makes components reusable