# Hooks

## Old Explanation

- Imagine earlier you had a remote toy that only adults could use. These toys are high speed and hard to use for kids.
- Kids had to ask: “Please change speed”, “Please stop it”.
- In old React: Only class components had special powers (state, lifecycle). Function components were simple and
  powerless
- Then React said: “Why not give these powers to everyone?” 😄So React created Hooks.
- Hooks are like magic switches 🪄 you can plug into function components to give them superpowers
    1. remember data
    2. React to changes
    3. Share logic
    4. Control Performance
- In Modern React, function components can do everything with the help of Hooks
- React Hooks are special functions introduced in React 16.8 that allow function components to use state, lifecycle
  features, and other React capabilities without writing class components.
- Hooks enable better code reuse, simpler components, and cleaner logic separation by extracting reusable behavior into
  custom hooks.
- Hooks are special functions that let function components use state and lifecycle features without classes.

## Problems with Class Components:

- Too much boilerplate
- this confusion
- Logic scattered across lifecycle methods
- Hard to reuse logic

## Hooks solved this by:

- Removing this
- Simplifying state management
- Sharing logic via custom hooks
- Encouraging functional programming.

## Rules of Hooks (Must Say in Interview)

- Only call hooks at the top level
- Only call hooks inside React function components or custom hooks
- Don’t call hooks inside loops or conditions