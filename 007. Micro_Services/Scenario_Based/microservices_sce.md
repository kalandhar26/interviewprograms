## How will handle Service failure in microservice chain?

- **Retry Mechanism:** If a service call fails due to a temporary issue (like a network glitch), automatically try again a few times. Use a delay that increases with each retry (exponential backoff) to avoid overwhelming the system. Don’t retry for errors that won’t resolve, like invalid requests.
- **Circuit Breaker Pattern:** Think of this like a fuse in an electrical system. If a service keeps failing, stop sending requests to it temporarily (using tools like Resilience4j). This prevents the failure from spreading and gives the service time to recover.
- **Fallback Strategies:** If a service is down, have a backup plan, like returning a cached response or a default message (e.g., “Service unavailable, try again later”). This keeps the user experience smooth.
- **Timeout Management:** Set a time limit for how long one service waits for another to respond. If it takes too long, move on to avoid slowing down the whole system or wasting resources.
- Bulkhead Isolation: Keep services independent by giving each one its own resources (like threads or memory). This way, if one service fails, it doesn’t drag others down with it.
- **Alerting and Monitoring:** Use tools like Prometheus, Grafana, or ELK Stack to watch your services in real-time. They track things like response times or failures, so you can spot and fix issues quickly.
- **Idempotency and Compensation:** For critical tasks like payments, ensure services can handle repeated requests without causing duplicates (idempotency). If something fails mid-process, use “undo” actions (compensation transactions) to keep data consistent.


## How will you implement Caching Techniques in your application?

### In-Memory Cache (Method-Level Cache)
- This stores data in the application’s memory (RAM) for quick access. Spring Boot provides method-level caching using annotations like @Cacheable, @CachePut, and @CacheEvict. 
- It’s simple and works well for single-instance applications with small datasets.
- Best for lightweight applications or when data doesn’t need to be shared across multiple instances of the service.

- Add dependencies to pom.xml
```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```
- Enable caching in your Spring Boot application
```java
- import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Enable caching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
- Use @Cacheable in your service

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Cacheable(value = "users", key = "#userId") // Cache result based on userId
    public User getUserById(Long userId) {
        // Simulate a slow database call
        System.out.println("Fetching user from DB for ID: " + userId);
        return new User(userId, "John Doe");
    }
}
```
- The first time getUserById(1) is called, it hits the database and caches the result in memory (using a cache named "users").
- Subsequent calls with the same userId return the cached result, skipping the database.
- Spring uses an in-memory cache (like ConcurrentMapCache) by default.

```java
@CacheEvict(value = "users", key = "#userId")
public void deleteUser(Long userId) {
    // Delete user logic
}
```
- To clear the cache when data changes

### Manual Cache (Using Cache Manager)
- Instead of relying on annotations, you manually interact with a cache using Spring’s CacheManager.
- This gives you more control over caching logic, like custom eviction or retrieval.
- When you need fine-grained control over caching (e.g., conditional caching or complex key generation).
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CacheManager cacheManager;

    public User getUserById(Long userId) {
        // Get cache instance
        Cache cache = cacheManager.getCache("users");

        // Check if data is in cache
        Cache.ValueWrapper cachedValue = cache.get(userId);
        if (cachedValue != null) {
            System.out.println("Returning from cache for ID: " + userId);
            return (User) cachedValue.get();
        }

        // Fetch from database if not in cache
        System.out.println("Fetching from DB for ID: " + userId);
        User user = new User(userId, "John Doe");

        // Store in cache
        cache.put(userId, user);
        return user;
    }

    public void clearCache(Long userId) {
        // Manually evict from cache
        Cache cache = cacheManager.getCache("users");
        cache.evict(userId);
    }
}
```
- You explicitly check the cache (cache.get) and store data (cache.put) using CacheManager.
- This approach is useful when you want to add custom logic, like caching only under certain conditions.

### Distributed Cache (Redis)
- Redis is an external, in-memory data store that acts as a distributed cache. It’s shared across multiple instances of your microservice, making it ideal for distributed systems.
- When your application runs on multiple servers or needs to share cached data across instances.
- Add dependencies to pom.xml
```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```
- Add redis properties into properties file
```properties
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.timeout=600ms
spring.cache.redis.time-to-live=3600000 # Cache TTL (1 hour in milliseconds)
```
- Enable caching and use @Cacheable (same as in-memory but backed by Redis)
```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Cacheable(value = "users", key = "#userId")
    public User getUserById(Long userId) {
        System.out.println("Fetching user from DB for ID: " + userId);
        return new User(userId, "John Doe");
    }
}
```
- Manually interact with Redis using RedisTemplate for more control

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public User getUserById(Long userId) {
        // Check Redis cache
        User user = (User) redisTemplate.opsForValue().get("user:" + userId);
        if (user != null) {
            System.out.println("Returning from Redis for ID: " + userId);
            return user;
        }

        // Fetch from DB
        System.out.println("Fetching from DB for ID: " + userId);
        user = new User(userId, "John Doe");

        // Store in Redis
        redisTemplate.opsForValue().set("user:" + userId, user);
        return user;
    }
}
```
- Spring Boot automatically uses Redis as the cache backend when configured.
- Data is stored in a central Redis server, accessible by all microservice instances.
- You can set a time-to-live (TTL) to automatically expire cached data.