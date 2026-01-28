# Key Differences (React 18 vs React 19)

## Server Components & Actions
- React18 introduced Server Components as a future feature.
- React19 makes Server Components more stable and powerful, letting server code do more work before sending UI to the client.
- React19 also introduces Actions — functions that can be called directly by components (like form handlers) without separate client API routes.

## New Async APIs & Hooks

### React19 includes new hooks and APIs:
- useActionState — manages the state of a server action (e.g., pending/error) automatically.
- useFormStatus — tracks the submission status of a form without extra context.
- useOptimistic — makes optimistic UI updates easier (instant UI before server response).
- use API — lets you consume promises directly in a component and suspend rendering until they resolve.
- React18 did not include these built‑in hooks; developers had to build custom solutions or use libraries for similar behavior.

## Improved Automatic Batching & Performance
- React18 introduced automatic batching for many state updates inside events.
- React19 extends automatic batching even to async work (like setTimeout, promises), reducing unnecessary renders and improving performance.

## Better Suspense & Data Fetching Support
- React18 supported Suspense for lazy components.
- React19 improves Suspense handling and integrates it more deeply with async data fetching, making loading states smoother and SSR more powerful.

## Compiler & Optimization Improvements

- React19 is expected to introduce a new compiler that can optimize code better at build time:
- Automatically memoizes where appropriate
- Reduces need for manual performance patterns like useMemo or React.memo in many cases
- Delivers faster runtime performance with less boilerplate.

## Simplified Form Handling & Directives
- React19 expands form support:
- <form action={fn}> can now call an Action function directly
- Built‑in mechanisms for pending/error UI
- Less manual state handling required for forms.