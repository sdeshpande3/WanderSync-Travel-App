import java.util.List;

/**
 * Boys, i Just took the things from there and put it in here from the other class
 * 
 */
public class OrderPrinter {

    public void printOrder(List<Item> items) {
        System.out.println("Order Details:");
        for (Item item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }
}
