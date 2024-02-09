package digital.softwareshinobi.napkinexchange.security.quote;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class BidAskQuote implements Serializable {

    @EmbeddedId
    private BidAskQuoteId id;

    protected final UUID uuid = UUID.randomUUID();

    @MapsId(value = "ticker")
    @ManyToOne(fetch = FetchType.LAZY)
    protected Security stock;

    @Column(name = "price")
    private Double stockPrice;

    @Column(name = "lastPrice")
    private Double lastPrice;

    @Column(name = "lastPrice1")
    private Double lastPrice1;

    @Column(name = "lastPrice2")
    private Double lastPrice2;

    @Column(name = "bid")
    protected Double bid;

    @Column(name = "ask")
    protected Double ask;

    public BidAskQuote(BidAskQuoteId id, Security stock, Double stockPrice, double lastPrice) {

        this.id = id;

        this.stock = stock;

        this.stockPrice = stockPrice;

        this.lastPrice = lastPrice;

        this.lastPrice1 = stockPrice - lastPrice;

        this.lastPrice2 = stockPrice / lastPrice;

    }

    public Double getBid() {

        return stockPrice;

    }

    public Double getAsk() {

        return stockPrice;

    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getLastPrice1() {
        return lastPrice1;
    }

    public void setLastPrice1(Double lastPrice1) {
        this.lastPrice1 = lastPrice1;
    }

    public Double getLastPrice2() {
        return lastPrice2;
    }

    public void setLastPrice2(Double lastPrice2) {
        this.lastPrice2 = lastPrice2;
    }

}
