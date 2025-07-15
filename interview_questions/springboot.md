# 5. How does spring manage thread safty of its beans?

- Spring manages bean thread safety based on their scope:
- Singleton (default): One instance shared across the app. Not thread-safe if stateful; developers must use
  synchronization or make them stateless for safety.
- Prototype: New instance per request, generally thread-safe as each thread gets its own copy.
- Request/Session: Web-specific scopes create instances per HTTP request or session, safe within their context.
- Spring encourages stateless beans, which are inherently thread-safe. For stateful beans, use prototype scope or
  synchronization. Spring's core container and tools like RequestContextHolder or @Transactional are thread-safe, but
  developers must ensure thread safety for mutable bean state.