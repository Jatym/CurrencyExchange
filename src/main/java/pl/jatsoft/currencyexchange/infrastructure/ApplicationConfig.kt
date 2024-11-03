package pl.jatsoft.currencyexchange.infrastructure


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient


@Configuration
class ApplicationConfig {

    @Bean
    fun restClient(): RestClient {
        return RestClient.builder()
            .requestFactory(HttpComponentsClientHttpRequestFactory())
            .baseUrl("https://api.nbp.pl/api")
            .defaultHeader("Accept", "application/json")
            .build()
    }
}
