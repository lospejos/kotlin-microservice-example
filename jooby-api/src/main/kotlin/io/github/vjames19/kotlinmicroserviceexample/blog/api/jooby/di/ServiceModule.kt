package io.github.vjames19.kotlinmicroserviceexample.blog.api.jooby.di

import com.google.inject.Provides
import com.google.inject.Singleton
import io.github.vjames19.kotlinmicroserviceexample.blog.di.DbExecutorService
import io.github.vjames19.kotlinmicroserviceexample.blog.model.Models
import io.github.vjames19.kotlinmicroserviceexample.blog.service.*
import io.github.vjames19.kotlinmicroserviceexample.blog.util.KotlinCompletableEntityStore
import io.requery.Persistable
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import org.postgresql.ds.PGSimpleDataSource
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by victor.reventos on 6/12/17.
 */
object ServiceModule : Module() {
    override fun configure() {
        bind<UserService>().to<RequeryUserService>()
        bind<PostService>().to<RequeryPostService>()
        bind<CommentService>().to<RequeryCommentService>()
    }

    @Provides
    @Singleton
    fun providesEntityDataStore(@DbExecutorService executorService: ExecutorService): KotlinCompletableEntityStore<Persistable> {
        val dataSource = PGSimpleDataSource().apply {
            user = "postgres"
            password = "postgres"
            databaseName = "blog"
        }

        val configuration = KotlinConfiguration(
                dataSource = dataSource,
                model = Models.DEFAULT,
                statementCacheSize = 100,
                useDefaultLogging = true)

        return KotlinCompletableEntityStore(KotlinEntityDataStore(configuration), executorService)
    }

    @Provides
    @Singleton
    @DbExecutorService
    fun providesDbExecutorService(): ExecutorService {
        return Executors.newCachedThreadPool()
    }
}