package digital.softwareshinobi.napkinexchange.ticker.service;

import digital.softwareshinobi.napkinexchange.market.entity.Market;
import digital.softwareshinobi.napkinexchange.market.service.MarketService;
import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.security.quote.SecurityPricingQuote;
import digital.softwareshinobi.napkinexchange.ticker.entity.idclass.StockPriceHistoryId;
import digital.softwareshinobi.napkinexchange.ticker.repository.SecurityPricingQuoteHistoryRepository;
import digital.softwareshinobi.napkinexchange.ticker.utils.SortHistory;
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
    private final SecurityPricingQuoteHistoryRepository stockPriceHistoryRepository;

    @Autowired
    private final StockService stockService;

    @Autowired
    private final MarketService marketService;

    public void publish() {

        Market market = marketService.findMarketEntity();

        System.out.println("number records / " + stockPriceHistoryRepository.count());

        SecurityPricingQuote lastSecurityPricingQuote = null;

        //   int size = this.stockPriceHistoryRepository.findAll().size() - 1;
        //List<SecurityPricingQuote> l = this.stockPriceHistoryRepository.findAll();
        //Collections.reverse(l);
        //SecurityPricingQuote lastSecurityPricingQuote = l.get(0);
        //  System.out.println("last / " + lastSecurityPricingQuote);
        for (Security security : stockService.getAllStocks()) {

            Double lastPrice = null;
            if (findStockHistoryByTicker(security.toString()).size() > 0) {
                System.out.println("there is some history to process ");
                List<SecurityPricingQuote> l = this.stockPriceHistoryRepository.findAll();
                Collections.reverse(l);
                lastSecurityPricingQuote = l.get(0);
                System.out.println("lastSecurityPricingQuote / " + lastSecurityPricingQuote);
                lastPrice = lastSecurityPricingQuote.getAsk();
            } else {
                lastPrice = security.getPrice();
                System.out.println("lastSecurityPricingQuote / " + lastPrice);
            }

            stockPriceHistoryRepository.save(
                    new SecurityPricingQuote(
                            new StockPriceHistoryId(
                                    market.getDate(),
                                    security.getTicker()),
                            security,
                            security.getPrice(),
                            lastPrice
                    ));

        }
    }

    public List<SecurityPricingQuote> findStockHistoryByTicker(String ticker) {
        List<SecurityPricingQuote> stockPriceHistory = stockPriceHistoryRepository.findAll().stream()
                .filter(history -> history.getStock().getTicker().equalsIgnoreCase(ticker))
                .collect(Collectors.toList());
        SortHistory.sortStockHistoryByDate(stockPriceHistory);
        return stockPriceHistory;
    }

    @Transactional
    public void truncateStockHistoryAtEndOfYear() {
        stockPriceHistoryRepository.truncateTable();
    }
}
