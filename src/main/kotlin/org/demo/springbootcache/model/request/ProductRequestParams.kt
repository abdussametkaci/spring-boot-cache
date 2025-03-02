package org.demo.springbootcache.model.request

data class ProductRequestParams(
    val minPrice: Int = 0,
    val maxPrice: Int = Int.MAX_VALUE,
    val page: Int = 0,
    val size: Int = 10
)