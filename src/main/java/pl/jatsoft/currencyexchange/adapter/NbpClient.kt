package pl.jatsoft.currencyexchange.adapter

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import pl.jatsoft.currencyexchange.entity.Currency

@Component
class NbpClient(
    private val restClient: RestClient
) {

    fun exchangeRates(currency: Currency) : ExchangeRatesDto? {
        return restClient
            .get()
            .uri("/exchangerates/rates/c/${currency.name.lowercase()}/2024-10-30/") // actual rates URI not working on weekend
            .retrieve()
            .body(ExchangeRatesDto::class.java)
    }

}


data class ExchangeRatesDto(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<RatesDto>
)


data class RatesDto(
    val no: String,
    val effectiveDate: String,
    val bid: Double,
    val ask: Double
)