package demo.game;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseResponse {

    public List<Purchase> purchases;

    public static class Purchase {
        public String state;
        public String purchaseUuid;
        public String type;
        public String loginEmail;
        public String itemUuid;
        public String gameUuid;
        public String token;
        public BigDecimal price;
        public String purpose;
    }
}
