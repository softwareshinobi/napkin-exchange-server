package digital.softwareshinobi.napkinexchange.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import digital.softwareshinobi.napkinexchange.security.defaults.DefaultStockPrices.CurrencySizing;
import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuote;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "stock")
@Table(name = "stock")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Transactional

public class Security {

    @Id
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @Column(name = "price")
    private Double price;

    @Column(name = "last_quote")
    private Double lastQuote;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BidAskQuote> pricingHistory;

    public Security(String symbol, String name, String sector, CurrencySizing american) {

        this.symbol = symbol;

        this.name = name;

        this.sector = sector;

        this.price = 0d;//DefaultStockPrices.getDefaultPriceWithCap(DefaultStockPrices.CurrencySizing.american);

        this.lastQuote = this.price;

    }

    public void updatePriceWithFormula() {

        double randomNumber = Math.random();

        //    double randomPositiveNumber = GetRandomNumber.getRandomPositiveNumberForStocks(this.marketCap);
        double stockPrice = this.getPrice();

        double newPrice = Math.round(stockPrice
                + (stockPrice * randomNumber));

//+ (stockPrice * (randomNumber * this.getVolatileStock().ordinal()))
        //+ (this.getInvestorRating().investorRatingMultiplier() * randomPositiveNumber)
        //+ (this.getMomentum() * randomPositiveNumber)) * 100.00) / 100.00;
        this.setPrice(newPrice);
//        setPrice(newPrice + 4.0);

    }

    @Override
    public String toString() {
        return "Security{" + "symbol=" + symbol + ", name=" + name + ", sector=" + sector + ", price=" + price + ", lastQuote=" + lastQuote + ", pricingHistory=" + pricingHistory + '}';
    }

}
