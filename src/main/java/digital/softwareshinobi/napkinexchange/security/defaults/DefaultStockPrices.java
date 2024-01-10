package digital.softwareshinobi.napkinexchange.security.defaults;

import static digital.softwareshinobi.napkinexchange.security.defaults.DefaultStockPrices.CurrencySizing.american;
import static digital.softwareshinobi.napkinexchange.security.defaults.DefaultStockPrices.CurrencySizing.european;
import static digital.softwareshinobi.napkinexchange.security.defaults.DefaultStockPrices.CurrencySizing.japanese;
import java.util.Random;

public class DefaultStockPrices {

    private static final Random random = new Random();

    public enum CurrencySizing {
        japanese(128.32),
        american(1.0033),
        european(0.8480),;

        private final Double initialValue;

        CurrencySizing(final Double initialValue) {

            this.initialValue = initialValue;

        }

        public Double getInitialValue() {
            return initialValue;
        }

    }

    private static double getDefaultPriceWithCap(final CurrencySizing currencySizing) {

        return switch (currencySizing) {

            case japanese ->

                japanese.initialValue + random.nextDouble(
                japanese.initialValue * .08,
                japanese.initialValue * 1.2
                );

            case american ->

                american.initialValue + random.nextDouble(
                american.initialValue * .08,
                american.initialValue * 1.2
                );

            case european ->

                european.initialValue + random.nextDouble(
                european.initialValue * .08,
                european.initialValue * 1.2
                );

        };

    }

}
