package digital.softwareshinobi.napkinexchange.security.service;

import digital.softwareshinobi.napkinexchange.market.Market;
import digital.softwareshinobi.napkinexchange.market.service.MarketService;
import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuote;
import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuoteId;
import digital.softwareshinobi.napkinexchange.security.quote.SecurityPricingQuoteHistoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SecurityPricingQuoteHistoryService {

    @Autowired
    private final SecurityPricingQuoteHistoryRepository pricingQuoteHistoryRepository;

    @Autowired
    private final StockService stockService;

    @Autowired
    private final MarketService marketService;

    public void publish() {

        Market market = marketService.findMarketEntity();

        System.out.println("number records / " + pricingQuoteHistoryRepository.count());

        BidAskQuote lastSecurityPricingQuote = null;

        //   int size = this.stockPriceHistoryRepository.findAll().size() - 1;
        //List<SecurityPricingQuote> l = this.stockPriceHistoryRepository.findAll();
        //Collections.reverse(l);
        //SecurityPricingQuote lastSecurityPricingQuote = l.get(0);
        //  System.out.println("last / " + lastSecurityPricingQuote);
        for (Security security : stockService.getAllStocks()) {

            Double lastPrice = null;
            if (findStockHistoryByTicker(security.toString()).size() > 0) {
                System.out.println("there is some history to process ");
                List<BidAskQuote> l = this.pricingQuoteHistoryRepository.findAll();
                Collections.reverse(l);
                lastSecurityPricingQuote = l.get(0);
                System.out.println("lastSecurityPricingQuote / " + lastSecurityPricingQuote);
                lastPrice = lastSecurityPricingQuote.getAsk();
            } else {
                lastPrice = security.getPrice();
                System.out.println("lastSecurityPricingQuote / " + lastPrice);
            }

            pricingQuoteHistoryRepository.save(new BidAskQuote(
                    new BidAskQuoteId(
                            market.getDate(),
                            security.getSymbol()),
                    security,
                    security.getPrice(),
                    lastPrice
            ));

        }
    }

    public List<BidAskQuote> findStockHistoryByTicker(String ticker) {

        List<BidAskQuote> stockPriceHistory
                = pricingQuoteHistoryRepository.findAll().stream()
                        .filter(history -> history.getStock().getSymbol().equalsIgnoreCase(ticker))
                        .collect(Collectors.toList());

        SortHistory.sortStockHistoryByDate(stockPriceHistory);

        return stockPriceHistory;

    }

    @Transactional
    public void truncateStockHistoryAtEndOfYear() {
        pricingQuoteHistoryRepository.truncateTable();
    }
}
