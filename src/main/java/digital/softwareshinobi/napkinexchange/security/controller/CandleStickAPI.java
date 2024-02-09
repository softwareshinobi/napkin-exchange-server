package digital.softwareshinobi.napkinexchange.security.controller;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuote;
import digital.softwareshinobi.napkinexchange.security.service.SecurityPricingQuoteHistoryService;
import digital.softwareshinobi.napkinexchange.security.service.StockService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "candlestick")
@CrossOrigin
@AllArgsConstructor
@SuppressWarnings("unused")
public class CandleStickAPI {

    @Autowired
    private StockService stockService;

    @Autowired
    private SecurityPricingQuoteHistoryService stockPriceHistoryService;

    private static final int TARGET_OUTPUT_LIST_SIZE = 100;

    public CandleStickAPI() {

        System.out.println("CandleStickAPI / CandleStickAPICandleStickAPICandleStickAPICandleStickAPICandleStickAPICandleStickAPI");

    }

    @GetMapping(value = "/")
    public String health() {

        return "OK";

    }

    @GetMapping(value = "/health")
    public String h22ealth() {

        return "OK";

    }

    @GetMapping(value = "/{ticker}")
    public Security getIndividualStockData(@PathVariable String ticker) {

        System.out.println("getIndividualStockData getIndividualStockData ");
        System.out.println("ticker / " + ticker);
        System.out.println("tickers / " + stockService.getAllStocks());
        return stockService.getStockByTickerSymbol(ticker);

    }

    @GetMapping(value = "/history/{ticker}")
    public List<BidAskQuote> getIndividualStockDatahistorty(@PathVariable String ticker) {

        Security security = stockService.getStockByTickerSymbol(ticker);

        List<BidAskQuote> trimmedCandleStickList = new ArrayList();

        List<BidAskQuote> bidAskQuoteHistory = security.getPricingHistory();

        Collections.reverse(bidAskQuoteHistory);

        for (BidAskQuote bidAskQuote : bidAskQuoteHistory) {

            if (trimmedCandleStickList.size() < TARGET_OUTPUT_LIST_SIZE) {

                trimmedCandleStickList.add(bidAskQuote);

            } else {

                break;

            }

        }

        Collections.reverse(trimmedCandleStickList); // numbers now contains [5, 4, 3, 2, 1]

        return trimmedCandleStickList;

    }

}
