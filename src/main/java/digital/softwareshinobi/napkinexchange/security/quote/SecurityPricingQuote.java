package digital.softwareshinobi.napkinexchange.security.quote;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import digital.softwareshinobi.napkinexchange.ticker.entity.idclass.StockPriceHistoryId;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "stock_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityPricingQuote implements Serializable {

    public SecurityPricingQuote(StockPriceHistoryId id, Security stock, Double stockPrice) {

        this.id = id;

        this.stock = stock;

        this.stockPrice = stockPrice;

    }

    @EmbeddedId
    private StockPriceHistoryId id;

    protected final UUID uuid = UUID.randomUUID();

    @MapsId(value = "ticker")
    @ManyToOne(fetch = FetchType.LAZY)
    protected Security stock;

    @Column(name = "price")
    private Double stockPrice;

    @Column(name = "bid")
    protected Double bid;

    @Column(name = "ask")
    protected Double ask;

    public Double getBid() {

        return stockPrice;

    }

    public Double getAsk() {

        return stockPrice;

    }

}
