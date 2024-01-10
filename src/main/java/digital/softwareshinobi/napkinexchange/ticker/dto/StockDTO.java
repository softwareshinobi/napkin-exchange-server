package digital.softwareshinobi.napkinexchange.ticker.dto;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import digital.softwareshinobi.napkinexchange.security.quote.SecurityPricingQuoteWrapper;
import java.util.List;
import java.util.stream.Collectors;
import digital.softwareshinobi.napkinexchange.trader.utils.CalculateCostBasisAndProfits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockDTO {

    private String ticker;
    
    private String companyName;
    
    private String sector;
    
    private String marketCap;
    
    private double price;
    
    private double lastDayPrice;
    
    private int momentum;
    
    private int momentumStreakInDays;
    
    private String volatileStock;
    
    private String investorRating;

    private List<SecurityPricingQuoteWrapper> pricingHistory;

    private double priceChangePercentage;
    
    private double priceChangeDifferential;

    public StockDTO(Security stock) {
        
        this.ticker = stock.getTicker();
        this.companyName = stock.getCompanyName();
        this.sector = stock.getSector();
        this.marketCap = String.valueOf(stock.getMarketCap());
        this.price = stock.getPrice();
        this.lastDayPrice = stock.getLastQuote();
        this.momentum = stock.getMomentum();
        this.momentumStreakInDays = stock.getMomentumStreakInDays();
        this.volatileStock = String.valueOf(stock.getVolatileStock());
        this.investorRating = String.valueOf(stock.getInvestorRating());
        
        this.pricingHistory = stock.getPriceHistory().stream()
                .map(SecurityPricingQuoteWrapper::new)
                .collect(Collectors.toList());

        this.priceChangePercentage = getPercentChange(this.getPrice(), this.getLastDayPrice());
      
        this.priceChangeDifferential = (this.getLastDayPrice() - this.getPrice());

    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLastDayPrice() {
        return lastDayPrice;
    }

    public void setLastDayPrice(double lastDayPrice) {
        this.lastDayPrice = lastDayPrice;
    }

//    public double getPercentChange() {
//        return priceChangePercentage;
//    }
//    public void setPercentChange(double percentChange) {
//        this.priceChangePercentage = percentChange;
//    }
    public int getMomentum() {
        return momentum;
    }

    public void setMomentum(int momentum) {
        this.momentum = momentum;
    }

    public int getMomentumStreakInDays() {
        return momentumStreakInDays;
    }

    public void setMomentumStreakInDays(int momentumStreakInDays) {
        this.momentumStreakInDays = momentumStreakInDays;
    }

    public String getVolatileStock() {
        return volatileStock;
    }

    public void setVolatileStock(String volatileStock) {
        this.volatileStock = volatileStock;
    }

    public String getInvestorRating() {
        return investorRating;
    }

    public void setInvestorRating(String investorRating) {
        this.investorRating = investorRating;
    }

    public double getPercentChange(double currentPrice, double lastDayPrice) {

        return CalculateCostBasisAndProfits.roundToTwoDecimalPlaces(
                (currentPrice - lastDayPrice) / lastDayPrice * 100);

    }

    public List<SecurityPricingQuoteWrapper> getPricingHistory() {
        return pricingHistory;
    }

    public void setPricingHistory(List<SecurityPricingQuoteWrapper> pricingHistory) {
        this.pricingHistory = pricingHistory;
    }
}
