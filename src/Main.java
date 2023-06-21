import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    //kolejka FIFO zamowien
    static ArrayDeque<Order> Queue = new ArrayDeque<>();

    //Obiekt do łączenia z bazą
    private static DatabaseConnection databaseConnection;

    //pobieranie danych od uzytkownika
    private static Scanner input = new Scanner(System.in);

    //Zmienne globalne dla danych użytkownika
    private static String usrMail = null;
    private static String usrName = null;
    private static int usrPin = 0;
    private static int usrPhone = 0;
    private static Client usr;
    private static boolean loginSession = false;

    //zmienna sterująca działaniem logowania
    static boolean loginRunTime = true;
    public static void main(String[] args) {

        // początkowe zamowienie - każde kolejne będzie o jeden większe
        int orderID = 1;
        //Obiekt zawierający produkty użytkownika
        Order usrOrder = new Order();

        //Logowanie
        //zmienna przetrzymująca dane admina
        String adminMail = "admin@burgerhouse.com";

        while (loginRunTime){
            System.out.println("    ===== Burger House =====    ");
            System.out.println("Najszybszy burger w twoim mieście\n");
            System.out.println("Czy posiadasz już u nas konto? (T/N)");
            String usrAccChoice = input.next();

            //sprawdzenie poprawnego znaku
            if (usrAccChoice.equals("T") || usrAccChoice.equals("t") || usrAccChoice.equals("N") || usrAccChoice.equals("n")){

                //Zaloguj sie
                if (usrAccChoice.equals("T") ||usrAccChoice.equals("t")){

                    logIn();
                    if (loginSession){
                        usr = new Client(usrName, usrMail, usrPhone, getUsrID());
                    }
                //Dodaj konto
                }else {
                    addUser();
                    //Po dodaniu konta zaloguj sie
                    logIn();
                    if (loginSession){
                        usr = new Client(usrName, usrMail, usrPhone, getUsrID());
                    }
                }
            }else {
                System.out.println("Wpisano niepoprawny znak! Spróbuj ponownie");
            }
        }
        System.out.println();

        //menu
        System.out.println("Witaj "+usrName);
        System.out.println();

        //zmienna sterująca działaniem menu
        boolean menuRunTime = true;
        if (usrMail.equals(adminMail)){
            while (menuRunTime) {

                usrOrder.setOrderID(orderID);
                //zamówienia mają numerek jak w Mc - do 100, później lecą od początku

                if (orderID > 100){
                    orderID = 1;
                }

                System.out.println("1 - pokaz menu");
                System.out.println("2 - dodaj produkt do zamowienia");
                System.out.println("3 - usun produkt z zamowienia");
                System.out.println("4 - wyswietl zamowienie");
                System.out.println("5 - zatwierdz zamowienie");
                //chyba jednak nie
//                System.out.println("6 - Zapisz kolejke zamowien do pliku");
//                System.out.println("7 - Przywroc kolejke zamowien z pliku");
                System.out.println("8 - odbierz zamowienie");
                System.out.println("9 - anuluj (wyjdz)");
                System.out.println("Wybierz opcje");
                int usrInput = input.nextInt();

                switch (usrInput) {
                    case 1:
                        //wyswietlenie menu
                        viewFoodMenu();
                        break;
                    case 2:
                        //dodawanie produktow do zamowienia - case każdy do każdego produktu
                        System.out.println("Jaki produkt chcesz dodać do zamówienia? (wpisz numer z menu)");
                        int usrChoice = input.nextInt();
                        switch (usrChoice) {
                            case 1:
                                //wysyłanie kwerendy pobierającej dany posiłek z bazy
                                databaseConnection = new DatabaseConnection();
                                String query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=1;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 2:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=2;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 3:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=3;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;

                            case 4:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=4;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 5:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=5;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 6:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=6;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;

                            case 7:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=7;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 8:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `picie` WHERE drink_id=1;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 9:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `picie` WHERE drink_id=2;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 10:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `picie` WHERE drink_id=3;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 11:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE drink_id=4;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 12:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE drink_id=5;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 13:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id = 1;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 14:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=2;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 15:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=3;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 16:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=4;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 17:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=5;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 18:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=6;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                        }
                        break;
                    case 3:
                        //usuwanie produktow z zamowienia
                        System.out.println("Który produkt chcesz usunac z zamowienia? (Podaj nazwę)");
                        usrOrder.showOrder();
                        String usrInputS = input.next();

                        //przy foreach rzuca exception - trzeba użyć iteratora
                        Iterator<Product> iterator = usrOrder.getProductList().iterator();
                        while (iterator.hasNext()) {
                            Product product = iterator.next();
                            if (product.name.equals(usrInputS)){
                                iterator.remove();
                                break;
                            }else {
                                System.out.println("Nie ma takiego produktu w twoim zamówieniu");
                            }
                        }
                        break;
                    case 4:
                        //wyswietlenie zamowienia
                        usrOrder.showOrder();
                        break;
                    case 5:
                        //zatwierdzenie zamowienia (dodanie do kolejki)
                        Queue.add(usrOrder);
                        //test dla ID
                        System.out.println("Zamówienie dodane do kolejki. ID zamówienia: " + usrOrder.getOrderID());

                        //Utworzenie kolejnego pustego zamówienia
                        usrOrder = new Order();
                        orderID++;

                        break;
                    case 6:
                        //zapis kolejk do pliku
                        break;
                    case 8:
                        takeOrder();
                    case 9:
                        //wyjscie z pętli - zakończenie programu
                        System.out.println("Adios!");
                        menuRunTime = false;
                        break;
                }
                viewQueue();
            }
        }else{
            while (menuRunTime) {

                usrOrder.setOrderID(orderID);
                //zamówienia mają numerek jak w Mc - do 100, później lecą od początku
                if (orderID > 100){
                    orderID = 1;
                }

                System.out.println("1 - pokaz menu");
                System.out.println("2 - dodaj produkt do zamowienia");
                System.out.println("3 - usun produkt z zamowienia");
                System.out.println("4 - wyswietl zamowienie");
                System.out.println("5 - zatwierdz zamowienie");
                System.out.println("8 - odbierz zamowienie");
                System.out.println("9 - anuluj (wyjdz)");
                System.out.println("Wybierz opcje");
                int usrInput = input.nextInt();

                switch (usrInput) {
                    case 1:
                        //wyswietlenie menu
                        viewFoodMenu();
                        break;
                    case 2:
                        //dodawanie produktow do zamowienia - case każdy do każdego produktu
                        System.out.println("Jaki produkt chcesz dodać do zamówienia? (wpisz numer z menu)");
                        int usrChoice = input.nextInt();
                        switch (usrChoice) {
                            case 1:
                                //wysyłanie kwerendy pobierającej dany posiłek z bazy
                                databaseConnection = new DatabaseConnection();
                                String query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=1;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 2:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=2;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 3:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=3;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;

                            case 4:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=4;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 5:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=5;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 6:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=6;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;

                            case 7:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE food_id=7;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 8:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `picie` WHERE drink_id=1;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 9:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `picie` WHERE drink_id=2;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 10:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `picie` WHERE drink_id=3;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 11:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE drink_id=4;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 12:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `jedzenie` WHERE drink_id=5;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 13:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id = 1;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 14:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=2;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 15:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=3;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 16:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=4;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 17:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=5;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 18:
                                databaseConnection = new DatabaseConnection();
                                query = "SELECT `Nazwa`, `Cena` FROM `dodatki` WHERE extras_id=6;";
                                try {
                                    Statement statement = databaseConnection.getConnection().createStatement();
                                    ResultSet result = statement.executeQuery(query);
                                    result.next();
                                    Product usrProduct = new Dishes(result.getString(1), result.getInt(2));
                                    usrOrder.addProduct(usrProduct);
                                    databaseConnection.closeConnection();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                        }
                        break;
                    case 3:
                        //usuwanie produktow z zamowienia
                        System.out.println("Który produkt chcesz usunac z zamowienia? (Podaj nazwę)");
                        usrOrder.showOrder();
                        String usrInputS = input.next();

                        //przy foreach rzuca exception - trzeba użyć iteratora
                        Iterator<Product> iterator = usrOrder.getProductList().iterator();
                        while (iterator.hasNext()) {
                            Product product = iterator.next();
                            if (product.name.equals(usrInputS)){
                                iterator.remove();
                                break;
                            }else {
                                System.out.println("Nie ma takiego produktu w twoim zamówieniu");
                            }
                        }
                        break;
                    case 4:
                        //wyswietlenie zamowienia
                        usrOrder.showOrder();
                        break;
                    case 5:
                        //zatwierdzenie zamowienia (dodanie do kolejki)
                        Queue.add(usrOrder);
                        //test dla ID
                        System.out.println("Zamówienie dodane do kolejki. ID zamówienia: " + usrOrder.getOrderID());

                        //Utworzenie kolejnego pustego zamówienia
                        usrOrder = new Order();
                        orderID++;

                        viewQueue();
                        
                        //poczeka 5 sekund jeżeli jest zamowienie i będzie gotowe
                        if (!(Queue.isEmpty())){
                            changeOrderStatus();
                        }
                        break;
                    case 8:
                        if (takeOrder()){
                            System.out.println("Smacznego! Zapraszamy ponownie!");
                            menuRunTime = false;
                        }else {
                            System.out.println("Twoje zamowienie nie jest gotowe!");
                        }
                        break;
                    case 9:
                        //wyjscie z pętli - zakończenie programu
                        System.out.println("Adios!");
                        menuRunTime = false;
                        break;
                }
                if (menuRunTime){
                    viewQueue();
                }
            }
        }
    }
    private static int getUsrID(){
        int usrID;

        String getUsrIdQuery = "SELECT `id` FROM `klienci` WHERE mail='"+ usrMail +"' AND pin="+ usrPin +";";
        try{
            databaseConnection = new DatabaseConnection();
            Statement statement = databaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(getUsrIdQuery);
            if (result.next()){
                usrID = result.getInt(1);
                databaseConnection.closeConnection();
                return usrID;
            }
        }catch (SQLException e){
            System.out.println("Nie pobrano id użytkownika!");
        }
        return 0;
    }
    private static void logIn(){
        System.out.println("      ===== Zaloguj się =====      ");
        System.out.println("Podaj mail");
        usrMail = input.next();
        System.out.println("Podaj PIN");
        try {
            usrPin = input.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Podano błędne dane - wpisz liczby!");
        }

        //Połączenie z bazą i sprawdzenie maila oraz pinu
        String logInUserQuery = "SELECT `imie`, `mail`, `pin` FROM `klienci` WHERE `mail`='"+ usrMail +"' AND pin='"+ usrPin +"';";
        try {
            databaseConnection = new DatabaseConnection();
            Statement statement = databaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(logInUserQuery);
            result.next();
            usrName = result.getString(1);
            if ((result.getString(2).equals(usrMail)) && (result.getInt(3) == usrPin)){
                loginRunTime = false;
                loginSession = true;
            }
            databaseConnection.closeConnection();
        }catch (Exception e){
            e.getMessage();

            System.out.println("Błędne dane logowania! Spróbuj ponownie");
        }
    }
    private static void addUser(){
            System.out.println("     ===== Dodaj konto! =====    ");
            System.out.println("Podaj imie");
            usrName = input.next();
            System.out.println("Podaj mail");
            usrMail = input.next();

            try {
                System.out.println("Podaj numer telefonu");
                usrPhone = input.nextInt();

                System.out.println("Podaj PIN - będzie służył do logowania");
                usrPin = input.nextInt();
                loginRunTime = false;

            }catch (InputMismatchException e){
                System.out.println("Podano błędne dane! Dodawanie konta nie powiodło się!");
            }

            String addUserQuery = "INSERT INTO `klienci`(`mail`, `pin`, `imie`, `nr_telefonu`) VALUES ('"+usrMail+"','"+usrPin+"', '"+usrName+"', '"+usrPhone+"');";
            try {
                //dodanie do bazy użytkownika
                databaseConnection = new DatabaseConnection();
                Statement statement = databaseConnection.getConnection().createStatement();
                statement.executeUpdate(addUserQuery);
                databaseConnection.closeConnection();

                //Zmienne do odczekiwania 5 sekund (na zaktualizowanie bazy)
                long timer = System.currentTimeMillis();
                long endTimer = timer + 5000;

                System.out.println();
                //Odczekiwanie 3 sekund przed dodaniem do bazy
                System.out.print("Uaktualniam bazę danych");
                try {
                    while(System.currentTimeMillis() < endTimer) {
                        String loading = ".";
                        System.out.print(loading);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println();

            }catch (Exception e){
                System.out.println("Dodanie konta nie powiodło się");
            }
    }
    public static void viewFoodMenu(){
        System.out.println("Menu:\n" +
                "    1. Skrzydełka 12PLN," +
                "    2. Stripsy 15PLN,\n" +
                "    3. Burger wołowy 20PLN, " +
                "    4. Podwójny burger wołowy 23PLN, " +
                "    5. Triple godzilla beef burger 25PLN,\n" +
                "    6. Cheese burger 21PLN,\n" +
                "    7. Frytki 10PLN,\n" +
                "    8. Pepsi 6PLN, " +
                "    9. SevenUp 6PLN, " +
                "    10. Mirinda 6PLN, " +
                "    11. Woda 6PLN, " +
                "    12. Szejk 7PLN,\n" +
                "Dodatki:\n" +
                "    13. Frytki 5PLN, " +
                "    14. Ser 3PLN, " +
                "    15. Boczek 3PLN, " +
                "    16. Kotlet 3PLN, " +
                "    17. Sos 3PLN, " +
                "    18. Krążki cebulowe 1PLN,\n"
                );
    }
    public static void viewQueue(){
        System.out.println("    === Kolejka zamówień === ");
        System.out.println("In Progress:");
        //System.out.println(Queue); - wyświetla obiekt i jego miejsce w pamięci
        //wyświetla wszystkie zamówienia (id)
        for (Order order: Queue) {
            System.out.print(order.orderID + " ");
        }
        System.out.println();
        System.out.println("Ready: ");
        for (Order order: Queue) {
            if (order.getOrderStatus() == Status.Ready)
            System.out.print(order.orderID + " ");
        }
        System.out.println();
    }
    public static boolean takeOrder(){
        if (Queue.getFirst().getOrderStatus() == Status.Ready){
            Queue.removeFirst();
            return true;
        }else {
            return false;
        }
    }
    public static void changeOrderStatus(){
        //po 5 sekundach zamowienie bedzie gotowe
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Queue.getFirst().setOrderStatus(Status.Ready);
    }
    public static void info(ArrayDeque<Order> queue){
        System.out.println(queue.toString());
    }
}