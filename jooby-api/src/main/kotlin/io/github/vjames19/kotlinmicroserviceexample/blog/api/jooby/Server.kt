package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di.ServiceModule
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.PostEndpoint
import io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.endpoint.UsersEndpoint
import org.jooby.AsyncMapper
import org.jooby.RequestLogger
import org.jooby.run
import org.jooby.json.Jackson


/**
 * Created by victor.reventos on 6/12/17.
 */
fun main(args: Array<String>) {
    run(*args) {
        use(ServiceModule)
        map(AsyncMapper())
        use(Jackson().module(KotlinModule()))

        use("*", RequestLogger().latency())
        use(UsersEndpoint::class)
        use(PostEndpoint::class)
    }
}