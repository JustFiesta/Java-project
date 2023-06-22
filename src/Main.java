import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    //kolejka FIFO zamowien
    static ArrayDeque<Order> Queue = new ArrayDeque<>();

    //Obiekt do łączenia z bazą
    private static DatabaseConnection databaseConnection;

    //pobieranie danych od uzytkownika
    public static Scanner input = new Scanner(System.in);

    //Zmienne globalne dla danych użytkownika
    private static String usrMail = null;
    private static String usrName = null;
    private static int usrPin = 0;
    private static int usrPhone = 0;
    public static Client usr;
    //Obiekt zawierający produkty użytkownika
    private static Order usrOrder;
    private static boolean loginSession = false;

    //zmienna sterująca działaniem logowania
    static boolean loginRunTime = true;
    public static void main(String[] args) {

        // początkowe zamowienie - każde kolejne będzie o jeden większe
        int orderID = 1;

        usrOrder = new Order();

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
                        usrOrder.setClient(usr);
                    }
                //Dodaj konto
                }else {
                    addUser();
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

                    //Po dodaniu konta zaloguj sie
                    logIn();
                    if (loginSession){
                        usr = new Client(usrName, usrMail, usrPhone, getUsrID());
                        usrOrder.setClient(usr);
                    }
                }
            }else {
                System.out.println("Wpisano niepoprawny znak! Spróbuj ponownie");
            }
        }
        System.out.println();

        //menu
        System.out.println("Witaj " + usrName);
        System.out.println();

        //zmienna sterująca działaniem menu
        boolean menuRunTime = true;
        if (usrMail.equals(adminMail)) {
                //wczytywanie kolejki z pliku
//            System.out.println("Wczytać poprzednią kolejkę z pliku? (T/N)");
//            String adminInput = null;
//            try {
//                adminInput = input.next();
//            } catch (InputMismatchException e) {
//
//            }
//            String loadFilePath = null;
//            if (adminInput.equals("T") || adminInput.equals("t") || adminInput.equals("N") || adminInput.equals("n")){
//                if (adminInput.equals("T") || adminInput.equals("t")){
//                    System.out.println("Podaj ścieżkę pliku: ");
//                    try {
//                        loadFilePath = input.next();
//                    }catch (InputMismatchException e){
//                        System.out.println("Błąd w ścieżce/nazwie pliku!");
//                    }
//                    loadQueueFromFile(loadFilePath);
//                }
//            }else {
//                System.out.println("Kontynuuje bez wczytywania - wpisano niepoprawny znak");
//            }

                //Panel dla administratora
                while (menuRunTime) {

                    //zamówienia mają numerek jak w Mc - do 100, później lecą od początku
                    usrOrder.setOrderID(orderID);
                    if (orderID > 100) {
                        orderID = 1;
                    }

                    System.out.println("1 - pokaz menu");
                    System.out.println("2 - dodaj produkt do zamowienia");
                    System.out.println("3 - usun produkt z zamowienia");
                    System.out.println("4 - wyswietl zamowienie");
                    System.out.println("5 - zatwierdz zamowienie");
                    System.out.println("6 - zapisz kolejke zamowien do pliku");
                    System.out.println("7 - usuń konto klienta");
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
                                if (product.name.equals(usrInputS)) {
                                    iterator.remove();
                                    break;
                                } else {
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
                            //sprawdzam czy jest puste
                            if (!(usrOrder.productList.isEmpty())){
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
                            }else {
                                System.out.println("Nie mogę dodać pustego zamówienia!");
                            }
                            break;
                        case 6:
                            //zapis kolejki do pliku
                            //sprawdzam czy jest pusta
                            if (!Queue.isEmpty()){
                                System.out.println("Gdzie chcesz zapisać kolejkę?");
                                try {
                                    String usrPath = input.next();
                                    saveQueueToFile(usrPath);
                                } catch (InputMismatchException e) {
                                    System.out.println("Podaj poprawną ścieżkę!");
                                }
                            }else {
                                System.out.println("Nie mogę zapisać pustej kolejki!");
                            }

                            break;
                        case 7:
                            //usuwanie klientów
                            showAllUsers();
                            System.out.println("Którego klienta chcesz usunąć? (wpisz mail)");
                            try {
                                String usrToRemove = input.next();
                                removeUser(usrToRemove);
                            } catch (InputMismatchException e) {
                                System.out.println("Wpisano błędny mail!");
                            }
                            System.out.println();
                            break;
                        case 8:
                            //odbierz zamowienie - po 5 sekundach będzie gotowe
                            if (takeOrder()){
                                System.out.println("Smacznego! Zapraszamy ponownie!");
                                System.exit(0);
                            }else {
                                System.out.println("Twoje zamowienie nie jest gotowe!");
                            }
                            break;
                        case 9:
                            //wyjscie z pętli - zakończenie programu
                            System.out.println("Adios!");
                            System.exit(0);
                            break;
                    }
                    viewQueue();
                }
        }else{
            //Panel dla użytkownika
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
                        //sprawdzam czy jest puste
                        if (!(usrOrder.productList.isEmpty())){
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
                        }else {
                            System.out.println("Nie mogę dodać pustego zamówienia!");
                        }
                        break;
                    case 8:
                        if (takeOrder()){
                            System.out.println("Smacznego! Zapraszamy ponownie!");
                            System.exit(0);
                        }else {
                            System.out.println("Twoje zamowienie nie jest gotowe!");
                        }
                        break;
                    case 9:
                        //wyjscie z pętli - zakończenie programu
                        System.out.println("Adios!");
                        System.exit(0);
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

        //Sprawdzenie czy taki mail jest w bazie
        String searchForMail = "SELECT * FROM `klienci` WHERE mail=" + usrMail + ";";
        boolean mailFound = false;
        try {
            databaseConnection = new DatabaseConnection();
            Statement statement = databaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(searchForMail);
            if (resultSet.next()){
                mailFound = true;
                System.out.println("Ten mail już jest w naszej bazie! Spróbuj ponownie!");
            }
            databaseConnection.closeConnection();
        }catch (SQLException e){

        }
        if (!mailFound){
            try {
                System.out.println("Podaj numer telefonu");
                usrPhone = input.nextInt();

                System.out.println("Podaj PIN - będzie służył do logowania");
                usrPin = input.nextInt();
                loginRunTime = false;

            }catch (InputMismatchException e){
                System.out.println("Podano błędne dane! Dodawanie konta nie powiodło się!");
            }


            //dodanie do bazy użytkownika
            String addUserQuery = "INSERT INTO `klienci`(`mail`, `pin`, `imie`, `nr_telefonu`) VALUES ('"+usrMail+"','"+usrPin+"', '"+usrName+"', '"+usrPhone+"');";
            try {
                databaseConnection = new DatabaseConnection();
                Statement statement = databaseConnection.getConnection().createStatement();
                statement.executeUpdate(addUserQuery);
                databaseConnection.closeConnection();
            }catch (Exception e){
                System.out.println("Dodanie konta nie powiodło się");
            }
        }
    }
    private static void viewFoodMenu(){
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
    private static void viewQueue(){
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
    private static boolean takeOrder(){
        if (Queue.getFirst().getOrderStatus() == Status.Ready){
            Queue.removeFirst();
            return true;
        }else {
            return false;
        }
    }
    private static void changeOrderStatus(){
        //po 5 sekundach zamowienie bedzie gotowe
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Queue.getFirst().setOrderStatus(Status.Ready);
    }
    private static void showAllUsers() {
        try {
            String fetchAllClientsQuery = "SELECT * FROM `klienci`;";
            databaseConnection = new DatabaseConnection();
            Statement statement = databaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(fetchAllClientsQuery);

            while (resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5)+ "\n");
            }
            databaseConnection.closeConnection();
        } catch (SQLException e) {
            System.out.println("Nie udało się pobrać listy klientów!");
            throw new RuntimeException(e);
        }
    }
    private static void removeUser(String mailToRemove) {
        try {
            String deleteUser = "DELETE FROM `klienci` WHERE mail='"+mailToRemove+"';";
            databaseConnection = new DatabaseConnection();
            Statement statement = databaseConnection.getConnection().createStatement();
            statement.executeUpdate(deleteUser);
            databaseConnection.closeConnection();
            System.out.println("Usunięto klienta!");
        } catch (SQLException e) {
            System.out.println("Nie udało się usunąć klienta!");
            throw new RuntimeException(e);
        }
    }
    private static void saveQueueToFile(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (Order usrOrders : Queue) {
                String orderData = usrOrders.toString();
                writer.write(orderData);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    private static void loadQueueFromFile(String path) {
//
//    }
}