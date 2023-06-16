import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    String[] productName = {"Хлеб", "Яблоко", "Молоко", "Апельсин", "Торт"};
    int[] price = {48, 77, 89, 350, 500};
    static Basket basket;

    public static Basket main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        XMLSettingsReader settings = new XMLSettingsReader(new File(shop.xml));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.loadFile);
        File logFile = new File(settings.loadFile);
        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);

        ClientLog clientLog = new ClientLog();

        while (true) {
            System.out.println("List of possible items to buy");
            showStoreProducts(productName, price);
            System.out.println("\nSelect a product or enter `end`");
            String productNumber = reader.readLine();
            if (productNumber.equals("end")) {
                if (settings.isLog) {
                    clientLog.exportAsCVS(logFile);
                }
                break;
            }

            System.out.println("\nEnter item quantity or enter `end`");
            String productCounter = reader.readLine();
            if (productCounter.equals("end")) {
                if (settings.isLog) {
                    clientLog.exportAsCVS(logFile);
                    basket.printCart();
                    break;
                }
                clientLog.log(productNumber, productCounter);
                ClientLog.showArray();
                basket.addToCart(Integer.parseInt(productNumber) - 1, Integer.parseInt(productCounter));
            }
            if (settings.isLog) {
                clientLog.log(productNumber, productCounter);
            }
            if (settings.isSave) {
                switch (settings.saveFormat) {
                    case "json" -> basket.saveJson(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }

            File theFile = new File("basket1.cvs");
            clientLog.exportAsCVS(theFile);
        }

        private static Basket createBasket (File loadFile,boolean isLoad, String loadFormat){
            Basket basket;
            if (isLoad && loadFile.exists()) {
                basket = switch (loadFormat) {
                    case "json" -> Basket.loadFromJSONFile(loadFile);
                    case "txt" -> Basket.loadFromTxtFile(loadFile);
                    default -> new Basket(productName, price);
                };
            } else {
                basket = new Basket(productName, price);
            }
            return basket;
        }


        private static void showStoreProducts (String[]strings,int[] price){
            int x = 1;
            for (String str : strings) {
                System.out.println(x + ". " + str + " " + price[x - 1] + "$/PC.");
                x++;
            }
        }
    }
}

