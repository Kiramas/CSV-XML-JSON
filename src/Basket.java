import java.io.*;
import java.util.Arrays;

import static java.lang.System.out;

public class Basket {
    private String[] products;
    private int[] prices;
    private static int[] quantities;
    int sumProducts = 0;

    public Basket() {

    }


    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.quantities = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        quantities[productNum] += amount;
    }

    public void printCart() {
        int totalPrice = 0;
        out.println("Ваша корзина");
        for (int i = 0; i < products.length; i++) {
            if (quantities[i] > 0) {
                int SumProducts = quantities[i] * prices[i];
                totalPrice += SumProducts;
                out.printf("%15s%4d р/шт%4d шт%6d руб%n", products[i], prices[i], quantities[i], SumProducts);
            }
        }
        out.printf("Итого: %d " + "рублей", totalPrice);
    }

    public void saveTxt(File txtFile) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(txtFile)) {
            for (var product : products) {
                out.print(product + " ");
            }
            out.println();

            for (var price : prices) {
                out.println(price + " ");
            }
            out.println();
            for (var quantity : quantities) {
                out.println(quantity + " ");
            }
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String goodsStr = bufferedReader.readLine();
            String pricesStr = bufferedReader.readLine();
            String quantitiesStr = bufferedReader.readLine();

            basket.products = goodsStr.split(" ");
            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.quantities = Arrays.stream(quantitiesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}