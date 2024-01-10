package digital.softwareshinobi.napkinexchange.security.quote;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityPricingQuoteWrapper {

    private ZonedDateTime id;

    private final String ticker;

    private double ask;

    private double bid;

    private final String code = "with ♥";

    private final double priceChangeAmount;

    private final double priceChangePercentage;

    public SecurityPricingQuoteWrapper(SecurityPricingQuote securityPricingQuote) {

        this.id = securityPricingQuote.getId().getMarketDate();

        this.ask = securityPricingQuote.getAsk();

        this.bid = securityPricingQuote.getAsk();

        this.ticker = securityPricingQuote.stock.getTicker();

        this.priceChangeAmount = securityPricingQuote.stock.getPriceChangeAmount();

        this.priceChangePercentage = securityPricingQuote.stock.getPriceChangePercentage();

    }

}
