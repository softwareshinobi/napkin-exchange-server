package digital.softwareshinobi.napkinexchange.ticker.repository;

import digital.softwareshinobi.napkinexchange.security.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Security, String> {
}
