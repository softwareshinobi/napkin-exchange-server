package digital.softwareshinobi.napkinexchange.ticker.service;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import lombok.AllArgsConstructor;
import digital.softwareshinobi.napkinexchange.ticker.enums.MarketCap;
import digital.softwareshinobi.napkinexchange.ticker.enums.Volatility;
import digital.softwareshinobi.napkinexchange.ticker.exception.StockNotFoundException;
import digital.softwareshinobi.napkinexchange.ticker.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockService {

    @Autowired
    private final StockRepository stockRepository;

    public List<Security> getAllStocks() {
        return stockRepository.findAll();
    }

    //this method is used to generate random news events
    public Security getRandomStock() {
        List<Security> stocks = getAllStocks();
        Collections.shuffle(stocks);
        return stocks.get(0);
    }

    public List<Security> getAllStocksByMarketCap(MarketCap marketCap) {
        return stockRepository.findAll().stream()
                .filter(stock -> stock.getMarketCap()
                .equals(marketCap)).collect(Collectors.toList());
    }

    public List<Security> getAllStocksBySector(String sector) {
        return stockRepository.findAll().stream()
                .filter(stock -> stock.getSector()
                .equalsIgnoreCase(sector)).collect(Collectors.toList());
    }

    public List<Security> getAllStocksByVolatility(Volatility volatility) {
        return stockRepository.findAll().stream()
                .filter(stock -> stock.getVolatileStock().equals(volatility))
                .collect(Collectors.toList());
    }

    public Security getStockByTickerSymbol(String ticker) {
        return stockRepository.findById(ticker.toUpperCase())
                .orElseThrow(() -> new StockNotFoundException(
                "No stock with ticker symbol " + ticker + " exists"));
    }

    public double getStockPriceWithTickerSymbol(String ticker) {
        if (stockTickerExists(ticker)) {
            throw new StockNotFoundException("No stock with ticker symbol " + ticker + " exists");
        }
        return getStockByTickerSymbol(ticker).getPrice();
    }

    //Ignore any stocks that do not currently exist
    public void updateStockInDatabase(Security stock) {
        if (stockTickerExists(stock.getTicker())) {
            return;
        }
        stockRepository.save(stock);
    }

    //Ignore any stocks that do not currently exist
    public void updateAllStocksInDatabase(List<Security> stocks) {
        stockRepository.saveAll(stocks);
    }

    public int findStockRowCount() {
        return (int) stockRepository.count();
    }

    public void saveDefaultStockToDatabase(List<Security> defaultStocks) {
        List<Security> currentStocks = stockRepository.findAll();
        List<Security> unsavedStocks = new ArrayList<>();
        defaultStocks.forEach(stock -> {
            if (!stockTickerExistsInList(currentStocks, stock.getTicker())) {
                unsavedStocks.add(stock);
            }
        });
        stockRepository.saveAll(unsavedStocks);
    }

    //Used for searching which default stocks do not exist and should be saved on startup
    public static boolean stockTickerExistsInList(List<Security> stocks, String ticker) {
        return stocks.stream()
                .map(Security::getTicker)
                .anyMatch(stockTicker -> stockTicker.equals(ticker));
    }

    public boolean stockTickerExists(String ticker) {
        return stockRepository.findById(ticker).isPresent();
    }
}
