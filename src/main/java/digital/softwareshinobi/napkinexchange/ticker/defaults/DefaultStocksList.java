package digital.softwareshinobi.napkinexchange.ticker.defaults;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.ticker.enums.InvestorRating;
import digital.softwareshinobi.napkinexchange.ticker.enums.MarketCap;
import digital.softwareshinobi.napkinexchange.ticker.enums.Volatility;
import java.util.List;

public class DefaultStocksList {

    private static final List<Security> allStocks = List.of(
            new Security("PANDORA", "Pandora Holdings", "Content Creation", MarketCap.Small, Volatility.VOLATILE, InvestorRating.Buy),
            new Security("DIONE", "The Dione Group", "Content Creation", MarketCap.Mid, Volatility.VOLATILE, InvestorRating.Buy),
            new Security("CALLISTO", "Callisto Industrials", "Content Creation", MarketCap.Large, Volatility.VOLATILE, InvestorRating.Buy),
            new Security("EUROPA", "Europa X", "Content Creation", MarketCap.Mid, Volatility.VOLATILE, InvestorRating.Buy)
    );

    public static int getCountForDefaultStocks() {
        return allStocks.size();
    }

    public static List<Security> getAllDefaultStocks() {
        return allStocks;
    }
}
