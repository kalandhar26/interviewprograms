# Formik

- Formik is a popular React form library that simplifies form state management, validation, error handling, and
  submission by abstracting repetitive boilerplate code using hooks or components.

## What are Forms in React?

- Forms are used to collect user input like: Text (name, email), Numbers, Checkboxes, Radio buttons, Dropdowns
- In React, form elements don’t manage their own state — React does.
- Handling user input with uncontrolled (DOM-managed) or controlled (state-managed) components. Controlled is preferred
  for validation/sync.

```jsx
import { useState } from "react";

function SignupForm() {
  // Single state approach
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    agree: false,
  });

  // Handle all inputs with one function
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form data:", formData);
    // Submit to API
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleChange}
        required
      />

      <input
        type="password"
        name="password"
        value={formData.password}
        onChange={handleChange}
        minLength="8"
      />

      <label>
        <input
          type="checkbox"
          name="agree"
          checked={formData.agree}
          onChange={handleChange}
        />
        I agree to terms
      </label>

      <button type="submit">Sign Up</button>
    </form>
  );
}
```

- For complex forms: Use React Hook Form library

## Composition vs Inheritance

- Composition builds components by combining smaller ones (preferred); inheritance uses class extension (discouraged,
  leads to tight coupling).

```jsx
function Dialog({ title, children }) {
  return (
    <div className="dialog">
      <h2>{title}</h2>
      {children} {/* Slots for buttons, content */}
    </div>
  );
}
// Usage: <Dialog title="Hi"><p>Content</p><button>OK</button></Dialog>
```