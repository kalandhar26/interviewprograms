# How Spring Security Works Internally in a Spring Boot Microservice Application

- Spring Security is a powerful and customizable authentication and authorization framework for Spring applications.
- It works by intercepting HTTP requests through a filter chain and performing security checks before a request reaches
  your controller.
- Internally, Spring Security configures a chain of servlet filters called the SecurityFilterChain.
- Each filter handles a specific aspect of security like authentication, authorization, session management, CORS, etc.
- The central component is the **FilterChainProxy**, which manages a list of **SecurityFilterChain** objects.
- Each **SecurityFilterChain** defines a set of filters for specific URL patterns.
- Filters are executed in a specific order, and each filter can modify the request, response, or pass control to the
  next filter.

# Key Components and WorkFlow

## Filter Chain:

- Spring Security uses a chain of servlet filters to process incoming HTTP requests. Each filter has a specific
  responsibility, such as authentication, authorization, or session management.
- The central component is the **FilterChainProxy**, which manages a list of **SecurityFilterChain** objects.
- Each **SecurityFilterChain** defines a set of filters for specific URL patterns.
- Filters are executed in a specific order, and each filter can modify the request, response, or pass control to the
  next filter.

## SecurityContext:

- The **SecurityContext** holds the authentication information (e.g., the authenticated user’s details) for the current
  request.
- It is stored in a **SecurityContextHolder**, typically using a thread-local strategy **(ThreadLocal)** to ensure
  thread safety
  in a multi-threaded environment like a microservice.

## Authentication Process:

- When a request arrives, the **AuthenticationManager** (often implemented by **ProviderManager**) delegates to one or
  more
  **AuthenticationProvider** instances to authenticate the user.
- Authentication providers validate credentials (e.g., username/password, JWT, OAuth tokens) and return an
  **Authentication** object if successful.
- The **Authentication** object is stored in the SecurityContext.

## Authorization:

- After authentication, Spring Security uses **AccessDecisionManager** to determine if the authenticated user has access
  to the requested resource.
- Authorization rules are defined using annotations (e.g., **@PreAuthorize**), roles, or expressions in the security
  configuration.

## Integration with Spring Boot:

- In a Spring Boot microservice, Spring Security is auto-configured when the **spring-boot-starter-security** dependency
  is added.
- The default configuration secures all endpoints, provides a login form, and generates a random password for a default
  user (user).
- We can customize this behavior by defining a **SecurityFilterChain** bean.

## Internal Workflow Example

- A client sends an HTTP request to a secured endpoint (e.g., /api/resource).
- The **FilterChainProxy** intercepts the request and delegates it to the appropriate **SecurityFilterChain** based on
  the URL pattern.
- Filters like **UsernamePasswordAuthenticationFilter** or **JwtAuthenticationFilter (for JWT)** process the request to
  authenticate the user.
- If authentication succeeds, the **SecurityContextHolder** stores the **Authentication** object.
- **Authorization filters** (e.g., FilterSecurityInterceptor) check if the user has the required permissions.
- If all checks pass, the request reaches the controller; otherwise, an HTTP 401/403 is returned.

# High Level Flow

Client → Servlet Filter Chain → DispatcherServlet → Controller

# Key Filters in Spring Security

## SecurityContextPersistenceFilter

- Loads and saves the SecurityContext between requests.
- It helps maintain the user’s authentication state (e.g., from session or request).

## UsernamePasswordAuthenticationFilter

- Handles form-based login.
- Captures login credentials from the request and triggers authentication.
- extends : AbstractAuthenticationProcessingFilter

## BasicAuthenticationFilter

- Handles HTTP Basic authentication (Authorization: Basic headers). Extracts credentials from the Authorization: Basic
  header.
- extends : OncePerRequestFilter

## BearerTokenAuthenticationFilter (used for OAuth2 and JWT)

-Extracts the bearer token from Authorization: Bearer <token> and authenticates. It is Used in microservices for
stateless authentication.

- extends: OncePerRequestFilter

## OAuth2LoginAuthenticationFilter:

- Handles OAuth 2.0 login flows, including redirecting to the authorization server and processing the callback. Used for
  OAuth 2.0 authentication with providers like Google.
- extends: AbstractAuthenticationProcessingFilter

## OncePerRequestFilter

- Ensures a filter is executed exactly once per request, avoiding duplicate execution in scenarios like request
  forwarding or async processing.
- Custom filters (e.g., JWT validation) extend OncePerRequestFilter to process tokens or headers once per request.

## CsrfFilter

- Protects against Cross-Site Request Forgery (CSRF) attacks by validating CSRF tokens for state-changing requests (
  e.g., POST). It Ensures POST requests include a valid CSRF token.
- extends: OncePerRequestFilter.

## LogoutFilter

- Handles logout requests by invalidating the session and clearing the SecurityContext.
- extends : GenericFilterBean

# Integrating JWT in a Spring Boot Microservice

- JSON Web Tokens (JWT) are commonly used in microservices for stateless authentication.
- A JWT consists of a **header**,**payload**, and **signature**, encoded in Base64 and signed with a **secret key** or *
  *public/private** key pair.

## How It Works

- The client sends a username/password to /auth/login to get a JWT.
- For subsequent requests, the client includes the JWT in the Authorization: Bearer <token> header.
- The JwtAuthenticationFilter validates the token and sets the Authentication in the SecurityContext.
- Secured endpoints are protected based on the SecurityFilterChain configuration.

# Integrating OAuth 2.0 in a Spring Boot Microservice

- OAuth 2.0 is used for delegated authorization, allowing users to authenticate via third-party providers (e.g., Google,
  GitHub). Spring Security provides built-in support for OAuth 2.0.

## How It Works

- The client accesses a secured endpoint, triggering a redirect to the OAuth provider (e.g., Google).
- After authentication, the provider redirects back to the application with an authorization code.
- Spring Security’s OAuth2LoginAuthenticationFilter exchanges the code for an access token and retrieves user details.
- The OAuth2User is stored in the SecurityContext, and the user is redirected to /home.

## Key Differences Between JWT and OAuth

- **JWT:** A token format used for authentication. It’s stateless and self-contained, ideal for microservices. You
  implement the token issuance and validation logic.
- **OAuth 2.0:** An authorization framework that delegates authentication to a third-party provider. It uses access
  tokens (which may or may not be JWTs) and is suited for scenarios requiring external authentication.
