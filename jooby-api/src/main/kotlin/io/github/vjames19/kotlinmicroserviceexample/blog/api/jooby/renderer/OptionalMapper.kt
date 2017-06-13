package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.renderer

import org.jooby.*
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Created by victor.reventos on 6/13/17.
 */
class OptionalMapper : Route.Mapper<Any> {

    override fun map(value: Any?): Any? {
        return when(value) {
            is CompletableFuture<*> -> map(value)
            is Optional<*> -> map(value)
            else -> value
        }
    }

    private fun map(value: CompletableFuture<*>): CompletableFuture<Any>? {
        return value.thenApply {
            when (it) {
                is Optional<*> -> map(it)
                else -> it
            }
        }
    }

    fun map(value: Optional<*>): Result {
        return if (value.isPresent) {
            Results.with(value.get())
        } else {
            Results.with(ApiError(Status.NOT_FOUND), Status.NOT_FOUND)
        }
    }
}
