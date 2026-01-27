# Jest

# React Testing Library

## Testing (Jest + React Testing Library / Cypress)

- Jest/RTL: Unit/integration (render, fire events). Cypress: E2E browser testing.

```jsx
import { render, screen, fireEvent } from '@testing-library/react';
import Counter from './Counter';

test('increments', () => {
  render(<Counter />);
  fireEvent.click(screen.getByText('Increment'));
  expect(screen.getByText('1')).toBeInTheDocument();
});
```