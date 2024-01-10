package digital.softwareshinobi.napkinexchange.ticker.dto;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.trader.utils.CalculateCostBasisAndProfits;
import lombok.Getter;
import lombok.Setter;

//This class is used when getting a list of stocks, where fields such as earnings and price history is not needed
@Getter
@Setter
public class StockSummaryDTO {

    private String ticker;
    private String companyName;
    private double price;
    private double lastDayPrice;
    private double percentChange;

    public StockSummaryDTO(Security stock) {
        this.ticker = stock.getTicker();
        this.companyName = stock.getCompanyName();
        this.price = stock.getPrice();
        this.lastDayPrice = stock.getLastQuote();
        this.percentChange = getPercentChange();
    }

    public double getPercentChange() {
        if (this.lastDayPrice == 0) {
            return 0.0;
        }
        return CalculateCostBasisAndProfits.roundToTwoDecimalPlaces(
                (this.price - this.lastDayPrice) / this.lastDayPrice * 100);
    }
}
