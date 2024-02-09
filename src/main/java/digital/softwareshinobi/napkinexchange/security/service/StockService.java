package digital.softwareshinobi.napkinexchange.security.service;

import digital.softwareshinobi.napkinexchange.security.exception.StockNotFoundException;
import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.ticker.repository.StockRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockService {

    @Autowired
    private final StockRepository stockRepository;

    public List<Security> getAllStocks() {
        return stockRepository.findAll();
    }

    public List<Security> getAllStocksBySector(String sector) {
        return stockRepository.findAll().stream()
                .filter(stock -> stock.getSector()
                .equalsIgnoreCase(sector)).collect(Collectors.toList());
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
        if (stockTickerExists(stock.getSymbol())) {
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

        System.out.println("currentStocks / " + currentStocks);

        List<Security> unsavedStocks = new ArrayList<>();

        defaultStocks.forEach(singleDefaultStock -> {

            if (!stockTickerExistsInList(currentStocks, singleDefaultStock.getSymbol())) {

                unsavedStocks.add(singleDefaultStock);

                System.out.println("singleDefaultStock / " + singleDefaultStock);

            }

        });

        stockRepository.saveAll(unsavedStocks);

    }

    //Used for searching which default stocks do not exist and should be saved on startup
    public static boolean stockTickerExistsInList(List<Security> stocks, String ticker) {
        return stocks.stream()
                .map(Security::getSymbol)
                .anyMatch(stockTicker -> stockTicker.equals(ticker));
    }

    public boolean stockTickerExists(String ticker) {
        return stockRepository.findById(ticker).isPresent();
    }
}
