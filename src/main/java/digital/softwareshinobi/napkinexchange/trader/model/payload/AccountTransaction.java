package digital.softwareshinobi.napkinexchange.trader.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountTransaction {

    private String username;
    
    private Double amountToAdd;
    
}
