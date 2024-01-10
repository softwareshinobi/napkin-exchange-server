package digital.softwareshinobi.napkinexchange.ticker.controller;

import lombok.AllArgsConstructor;
import digital.softwareshinobi.napkinexchange.ticker.dto.StockSummaryDTO;
import digital.softwareshinobi.napkinexchange.ticker.exception.StockNotFoundException;
import digital.softwareshinobi.napkinexchange.ticker.dto.StockDTO;
import digital.softwareshinobi.napkinexchange.security.quote.SecurityPricingQuoteWrapper;
import digital.softwareshinobi.napkinexchange.ticker.service.SecurityPricingQuoteHistoryService;
import digital.softwareshinobi.napkinexchange.ticker.service.StockService;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/{ticker}")
    public StockDTO getIndividualStockData(@PathVariable String ticker) {

        return new StockDTO(stockService.getStockByTickerSymbol(ticker));

    }

    @GetMapping(value = "/history/{ticker}")
    public List<SecurityPricingQuoteWrapper> getIndividualStockDatahistorty(@PathVariable String ticker) {

        StockDTO dto = new StockDTO(stockService.getStockByTickerSymbol(ticker));

        List<SecurityPricingQuoteWrapper> trimmedCandleStickList = new ArrayList();

        List<SecurityPricingQuoteWrapper> stockPriceHistoryList = dto.getPricingHistory();

        Collections.reverse(stockPriceHistoryList); // numbers now contains [5, 4, 3, 2, 1]

        for (SecurityPricingQuoteWrapper stockPriceHistory : stockPriceHistoryList) {

            if (trimmedCandleStickList.size() < TARGET_OUTPUT_LIST_SIZE) {

                trimmedCandleStickList.add(stockPriceHistory);

            } else {

                break;

            }

        }

        Collections.reverse(trimmedCandleStickList); // numbers now contains [5, 4, 3, 2, 1]

        return trimmedCandleStickList;

    }

    @GetMapping
    public List<StockSummaryDTO> getAllStockData() {
        return stockService.getAllStocks().stream()
                .map(StockSummaryDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/detailed")
    public List<StockDTO> getAllDetailedStockData() {
        return stockService.getAllStocks().stream()
                .map(StockDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/price/{ticker}")
    public double getStockPrice(@PathVariable String ticker) throws StockNotFoundException {
        return stockService.getStockPriceWithTickerSymbol(ticker);
    }

}
