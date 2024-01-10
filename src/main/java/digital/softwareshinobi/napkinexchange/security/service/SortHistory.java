package digital.softwareshinobi.napkinexchange.security.service;

import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuote;
import digital.softwareshinobi.napkinexchange.trader.model.AccountHistory;
import java.util.List;

public class SortHistory {

    public static void sortStockHistoryByDate(List<BidAskQuote> stockPriceHistory) {
        stockPriceHistory.sort((history1, history2) -> {
            return history1.getId().getMarketDate().compareTo(history2.getId().getMarketDate());
        });
    }

    public static void sortAccountHistoryByDate(List<AccountHistory> accountHistory) {
        accountHistory.sort((history1, history2) -> {
            return history1.getDate().compareTo(history2.getDate());
        });
    }
}
