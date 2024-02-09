package digital.softwareshinobi.napkinexchange.security.defaults;

import digital.softwareshinobi.napkinexchange.security.defaults.DefaultStockPrices.CurrencySizing;
import digital.softwareshinobi.napkinexchange.security.model.Security;
import java.util.List;

public class DefaultStocksList {

    private static final List<Security> allStocks = List.of(
            new Security(
                    "PANDORA",
                    "Pandora Holdings???",
                    "Pandora Holdings & Content Creation",
                    CurrencySizing.american),
            new Security("DIONE", "The Dione Group", "Content Creation", CurrencySizing.american),
            new Security("CALLISTO", "Callisto Industrials", "Content Creation", CurrencySizing.american),
            new Security("EUROPA", "Europa X", "Content Creation", CurrencySizing.american)
    );

    public static int getCountForDefaultStocks() {
        return allStocks.size();
    }

    public static List<Security> getAllDefaultStocks() {
        return allStocks;
    }
}
