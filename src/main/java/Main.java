import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static Basket basket;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] productName = {"Хлеб", "Яблоко", "Молоко", "Апельсин", "Торт"};
        int[] price = {48, 77, 89, 350, 500};
        Basket basket1 = new Basket(productName, price);
        ClientLog clientLog = new ClientLog();

        while (true) {
            System.out.println("List of possible items to buy");
            showStoreProducts(productName, price);

            System.out.println("\nSelect a product or enter `end`");
            String productNumber = reader.readLine();
            if (productNumber.equals("end")) {
                basket1.printCart();
                break;
            }

            System.out.println("\nEnter item quantity or enter `end`");
            String productCounter = reader.readLine();
            if (productCounter.equals("end")) {
                basket1.printCart();
                break;
            }
            clientLog.log(productNumber, productCounter);
            ClientLog.showArray();
            basket1.addToCart(Integer.parseInt(productNumber)-1, Integer.parseInt(productCounter));
        }

        File theFile = new File("basket1.cvs");
        clientLog.exportAsCVS(theFile);
    }

    private static void showStoreProducts(String[] strings, int[] price) {
        int x = 1;
        for (String str : strings) {
            System.out.println(x + ". " + str + " " + price[x-1] + "$/PC.");
            x++;
        }
    }
}