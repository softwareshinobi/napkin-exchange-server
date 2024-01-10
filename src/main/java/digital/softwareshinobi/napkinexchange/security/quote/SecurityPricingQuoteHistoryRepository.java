package digital.softwareshinobi.napkinexchange.security.quote;

import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuote;
import digital.softwareshinobi.napkinexchange.security.quote.BidAskQuoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SecurityPricingQuoteHistoryRepository extends JpaRepository<BidAskQuote, BidAskQuoteId> {

    @Modifying
    @Query(value = "truncate table stock_history", nativeQuery = true)
    void truncateTable();

}
