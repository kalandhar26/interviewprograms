### 2.6 Conditional Rendering

- Rendering JSX based on conditions (e.g., if/else, ternaries) to show/hide elements dynamically.
- Imagine your mother looks at the weather. If it’s raining → she gives you an umbrella .If it’s sunny → she gives you
  sunglasses.
- She doesn’t give both. She decides based on condition. React does the same.
- It shows different UI based on: Login status, Data availability, User role, Button Clicks.
- This is called Conditional Rendering.
- Conditional rendering in React refers to displaying different UI elements or components based on certain conditions.
  It leverages standard JavaScript conditional operators such as if, ternary, and logical && within JSX.
- Conditional rendering in React means displaying different UI elements based on application state or conditions using
  JavaScript logic.

```jsx
function UserStatus({ isLoggedIn, user }) {
  // 1. If-else (outside JSX)
  if (!isLoggedIn) {
    return <LoginButton />;
  }

  // 2. Ternary operator (in JSX)
  return (
    <div>
      <h1>Welcome back!</h1>
      {isLoggedIn ? <UserProfile user={user} /> : <GuestProfile />}

      {/* 3. Logical AND */}
      {isLoggedIn && <Dashboard />}

      {/* 4. Switch pattern */}
      {(() => {
        switch (user.role) {
          case "admin":
            return <AdminPanel />;
          case "user":
            return <UserPanel />;
          default:
            return <GuestPanel />;
        }
      })()}
    </div>
  );
}
```

### Using 'if' Statement

```jsx
function UserGreeting({ isLoggedIn }) {
  if (isLoggedIn) {
    return <h2>Welcome User</h2>;
  }
  return <h2>Please Login</h2>;
}
```

### Using Ternary Operator (Most Common)

```jsx
<h2>{isLoggedIn ? "Welcome User" : "Please Login"}</h2>
```

### Using Logical AND (&&)

```jsx
{isAdmin && <button>Delete User</button>}
```

### Conditional Component Rendering

```jsx
function App() {
  return isLoggedIn ? <Dashboard /> : <Login />;
}
```