package digital.softwareshinobi.napkinexchange.portfolio.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LimitOrderRequest {

    private String username;
    private String symbol;
    private int sharesToBuy;
    private double limitPrice;
}
