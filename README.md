# Spring Boot Cache

## Overview
This system features a simple product domain where each product belongs to a specific category. 
While there are no strict limitations on the creation of categories, it is expected that the number of categories will remain relatively low. 
However, the system anticipates the creation of a large number of products and extensive filtering operations.

To ensure the system can handle high loads efficiently, caching mechanisms will be implemented.

## Docker Compose Setup
The project includes a `docker/docker-compose.yml` file that defines the necessary services for running the application:
* **PostgreSQL**: Used for persistent data storage. It is configured with the username `postgres_user`, password `123`, and database `postgres_db`.
* **Redis**: Used for caching, improving the performance of the application. It is secured with the password `123`.

**To Start Services**
```shell
docker-compose up
```

## Postman Collection
Postman Collection: [Spring Boot Cache.postman_collection.json](postman/Spring%20Boot%20Cache.postman_collection.json)

## Caching Strategy
To optimize the performance of the system, caching is utilized for both products and categories. 
Caching helps reduce the number of database queries, speeding up data retrieval and ensuring that the system can handle high traffic more efficiently.

### Caching for Categories
* **Get All Categories**: The list of all categories is cached with the key 'list'. 
When the getAll() method is called, it checks the cache first. 
If the cache contains the data, it returns the cached result; otherwise, it queries the database and stores the result in the cache.

```kotlin
  @Cacheable("categories", key = "'list'")
  fun getAll(): CategoriesResponse { ... }
```

* **Get Category by ID**: Each category's details are cached individually with the key based on the category’s id. 
When a specific category is requested, the system first checks the cache. 
If the data is not in the cache, it retrieves it from the database and stores it in the cache for future requests.

```kotlin
@Cacheable("categories", key = "#id")
fun getById(id: UUID): CategoryResponse { ... }
```

* **Creating or Updating Categories**: When a category is created or updated, the cache for the category list is evicted, 
and the updated category is then put into the cache with the appropriate key.

```kotlin
@Caching(
    evict = [CacheEvict("categories", key = "'list'")],
    put = [CachePut("categories", key = "#result.id")]
)
fun create(request: CreateCategoryRequest): CategoryResponse { ... }
```

* **Deleting Categories**: When a category is deleted, the category list cache, the cache for the specific category and all products are evicted.

```kotlin
@Caching(
    evict = [
        CacheEvict("products", allEntries = true),
        CacheEvict("categories", key = "'list'"),
        CacheEvict("categories", key = "#id")
    ]
)
fun delete(id: UUID) { ... }
```

### Caching for Products
* **Get Product by ID**: Each product is cached individually using its unique id. 
If the product is already cached, it will be retrieved from the cache instead of querying the database.

```kotlin
@Cacheable("products", key = "#id")
fun getById(id: UUID): ProductResponse { ... }
```

* **Filtering Products**: When filtering products based on various parameters (e.g., price range, pagination), 
the cache key is composed of the filter parameters (requestParams). This ensures that different filtered views of the product list have their own cache.

```kotlin
@Cacheable("products", key = "'list:' + #requestParams")
fun getBy(requestParams: ProductRequestParams): PagedModel<ProductResponse> { ... }
```

* **Creating or Updating Products**: Similar to categories, when a product is created or updated, 
the product list cache is evicted and the newly created or updated product is added to the cache.

```kotlin
@Caching(
    evict = [CacheEvict("products:list", allEntries = true)],
    put = [CachePut("products", key = "#result.id")]
)
fun create(request: CreateProductRequest): ProductResponse { ... }
```

* **Deleting Products**: When a product is deleted, both the product list cache and the cache for the specific product are evicted.

```kotlin
@Caching(
    evict = [
        CacheEvict("products:list", allEntries = true),
        CacheEvict("products", key = "#id")
    ]
)
fun delete(id: UUID) { ... }
```

## Benefits of Caching
* **Improved Performance**: By reducing the need for repeated database queries, caching ensures faster response times, 
especially for frequently accessed data like product lists and categories.
* **Reduced Database Load**: Caching helps offload the database by serving data directly from memory, making the system more efficient and scalable.
* **Enhanced User Experience**: With faster data retrieval, users can interact with the system more smoothly, even under heavy load.

## Resources
* https://www.baeldung.com/spring-cache-tutorial
* https://www.baeldung.com/spring-boot-redis-cache
* https://docs.spring.io/spring-data/redis/reference/redis/redis-cache.html