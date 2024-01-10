package digital.softwareshinobi.napkinexchange.ticker.repository;

import digital.softwareshinobi.napkinexchange.security.quote.SecurityPricingQuote;
import digital.softwareshinobi.napkinexchange.ticker.entity.idclass.StockPriceHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SecurityPricingQuoteHistoryRepository extends JpaRepository<SecurityPricingQuote, StockPriceHistoryId> {

    @Modifying
    @Query(value = "truncate table stock_history", nativeQuery = true)
    void truncateTable();
}
