package digital.softwareshinobi.napkinexchange.trader.utils;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.security.exception.StockNotFoundException;
import digital.softwareshinobi.napkinexchange.security.service.StockService;
import digital.softwareshinobi.napkinexchange.trader.model.Account;
import digital.softwareshinobi.napkinexchange.trader.model.StockOwned;
import digital.softwareshinobi.napkinexchange.portfolio.order.BuyStockRequest;
import digital.softwareshinobi.napkinexchange.portfolio.order.SellStockRequest;

public class ValidateStockTransaction {

    public static boolean doesAccountHaveEnoughMoney(
            Account account,
            BuyStockRequest buyStockRequest,
            StockService stockService) {

        double balance = account.getAccountBalance();

        Security stock;

        try {

            stock = stockService.getStockByTickerSymbol(buyStockRequest.getSymbol());

        } catch (StockNotFoundException ex) {

            return false;

        }

        return balance > (stock.getPrice() * buyStockRequest.getSharesToBuy());

    }

    public static boolean doesAccountHaveEnoughStocks(
            Account account,
            SellStockRequest sellStock) {

        StockOwned stock = FindStockOwned.findOwnedStockByTicker(
                account.getStocksOwned(),
                sellStock.getSymbol());

        if (stock == null) {

            return false;

        }

        return stock.getAmountOwned() >= sellStock.getSharesToSell();

    }
}
