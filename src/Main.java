import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Scanner;
public class Main implements QueueOperations{
    //...
    //trzeba dodać wczytywanie z pliku przed tym (if chcesz liste - wczytaj, else \/)

    //kolejka FIFO zamowien
    static ArrayDeque<Order> Queue = new ArrayDeque<>();

    //Obiekt to łączenia z bazą
    private static DatabaseConnection databaseConnection;

    public static void main(String[] args) {
        //pobieranie danych od uzytkownika
        Scanner input = new Scanner(System.in);

        // ID pobierane z bazy
        int usrID = 0;
        // początkowe zamowienie - każde kolejne będzie o jeden większe
        int orderID = 1;
        //Obiekt zawierający produkty użytkownika
        Order usrOrder = new Order();
        //zmienna sterująca działaniem programu
        boolean programRunTime = true;

        //Logowanie - po skończonym menu i bazie
//
//            //sprawdzenie czy użytkownik istnieje - to do oddzielnej metody
//        System.out.println("Czy masz konto użytkownika? T/N");
//        String usrAcc = input.next();
//        if (usrAcc == "N"){
//            System.out.println("Musisz stworzyć konto użytkownika do składania zamówień!");
//            System.out.println("Podaj imie:");
//            String usrName = input.next();
//            System.out.println("Podaj adres email:");
//            String usrMail = input.next();
//            System.out.println("Podaj adres numer telefonu:");
//            double usrPhone = input.nextDouble();
//
//            Client newCustomer = new Client(usrName, usrMail, usrPhone,usrID);
//        }else{
//            System.out.println("Wspaniale! Podaj swoje dane");
//
//            // tak dla testu - tutaj wczytanie z pliku użytkownika, sprawdzenie czy istnieje, jeżeli nie to do początku
//            System.out.println("Podaj imie:");
//            String usrName = input.next();
//            System.out.println("Podaj adres email:");
//            String usrMail = input.next();
//            System.out.println("Podaj adres numer telefonu:");
//            double usrPhone = input.nextDouble();
//
//            Client loggedCustomer = new Client(usrName, usrMail, usrPhone, usrID);
//        }
        //menu
        System.out.println("    ===== Burger House =====    ");
        System.out.println("Najszybszy burger w twoim mieście");
        System.out.println();

        while (programRunTime) {

            usrOrder.setOrderID(orderID);
            orderID++;
            //zamówienia mają numerek jak w Mc - do 100, później lecą od początku
            if (orderID > 100){
                orderID = 1;
            }

            System.out.println("1 - pokaz menu");
            System.out.println("2 - dodaj produkt do zamowienia");
            System.out.println("3 - usun produkt z zamowienia");
            System.out.println("4 - wyswietl zamowienie");
            System.out.println("5 - zatwierdz zamowienie");
            System.out.println("6 - Zapisz kolejke zamowien do pliku");
            System.out.println("9 - anuluj (wyjdz)");
            System.out.println("Wybierz opcje");
            int usrInput = input.nextInt();

            switch (usrInput) {
                case 1:
                    //wyswietlenie menu
                    viewMenu();
                    break;
                case 2:
                    //dodawanie produktow do zamowienia - case każdy do każdego produktu
                    System.out.println("Jaki produkt chcesz dodać do zamówienia? (wpisz numer z menu)");
                    int usrChoice = input.nextInt();
                    switch (usrChoice) {
                        case 1:
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
                    // do poprawy!!!
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

                    break;
                case 6:
                    //zatwierdzenie zamowienia
                    //                orders;
                    break;
                case 9:
                    //wyjscie z pętli - zakończenie programu
                    System.out.println("Adios!");
                    programRunTime = false;
                    break;
            }
            viewQueue();
        }
    }
    public static void viewMenu(){
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
        System.out.println("        === Kolejka zamówień === ");
        System.out.print("In Progress   |   Ready!\n");
        //System.out.println(Queue); - wyświetla obietk i jego miejsce w pamięci
        //wyświetla wszystkie zamówienia (id)
        for (Order order: Queue) {
            System.out.println(order.orderID);
        }
        System.out.println();
    }
    public static void info(ArrayDeque<Order> queue){

        System.out.println(queue.toString());
    }
    @Override
    public void addToQueue(Order order) {
        Queue.addLast(order);
    }

    @Override
    public void removeFromQueue(Order order) {
        Queue.remove(order);
    }


    //obsługa csv - to dodam na końcu
    @Override
    public void saveQueueToFile(String path) {

    }

    @Override
    public void loadQueueFromFile(String path) {

    }
}