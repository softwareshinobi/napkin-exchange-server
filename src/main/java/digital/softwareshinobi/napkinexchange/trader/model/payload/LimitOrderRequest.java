package digital.softwareshinobi.napkinexchange.trader.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LimitOrderRequest {

    private String username;
    private String ticker;
    private int sharesToBuy;
    private double limitPrice;
}
