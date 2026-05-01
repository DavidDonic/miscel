package dado.lab.large_spaces;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    // --- shopping cart ---
    // --- features ---
    // ->***addProduct()
    // ->oneMore()
    // ->oneLess()
    // ->***deleteProduct()
    // ->***checkAll()
    // ->checkSelected()
    // --- instances ---
    class Product {
        private String name;
        int price;

        Product(int pri, String name) {
            this.price = pri;
            this.name = name;
        }

    }
    class CartItem {
        //add-ons: Unit (kg/amount/length... ->enum)
        final Product product;
        private int quantity;

        CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        oneMore() {}
        oneLess(){}
    }
    class Cart {
        private Map<String, CartItem> items = new HashMap<>();
        addProduct(){}
        deleteProduct(){}
        checkAll(){}
        checkSelected(){}
    }

}
