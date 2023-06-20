import java.util.ArrayDeque;
import java.util.Scanner;

public class Main implements QueueOperations{
    //...
    //trzeba dodać wczytywanie z pliku przed tym (if chcesz liste - wczytaj, else \/)

    //kolejka FIFO zamowien
    static ArrayDeque<Order> usrOrders = new ArrayDeque<>();

    public static void main(String[] args) {
        //pobieranie danych od uzytkownika
        Scanner input = new Scanner(System.in);

        //Obiekt zawierający podukty użytkownika
        Order usrOrder = new Order();
        // początkowy klient - każdy kolejny będzie o jeden większy
        int usrID = 1;
        // początkowe zamowienie - każde kolejne będzie o jeden większe
        int orderID = 1;


        //zmienna sterująca działaniem programu
        boolean programRunTime = true;


        //menu
        System.out.println("    ===== Burger House =====    ");
        System.out.println("Najszybszy burger w twoim mieście");

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
        System.out.println();

        while (programRunTime) {
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
                    //dodawanie produktow do zamowienia
                    System.out.println("Jaki produkt chcesz dodać do zamówienia? (wpisz numer z menu)");
                    int usrChoice = input.nextInt();
                    switch (usrChoice) {
                        case 1:
                            Boxes usrDish = new Boxes();
                            usrOrder.addProduct(usrDish);
                            break;
                        //                    case 2:
                        //                        Food usrDish = new Food();
                        //                        order1.addProduct(usrDish);
                        //                        break;
                    }
                    break;
                case 3:
                    //usuwanie produktow z zamowienia
                    System.out.println("Który produkt chcesz usunac z zamowienia?");
                    usrOrder.showOrder();
//                    usrInput = input.nextInt();
//                    usrOrder.removeProduct();
                    break;
                case 4:
                    //wyswietlenie zamowienia
                    usrOrder.showOrder();
                    break;
                case 5:
                    //zatwierdzenie zamowienia (dodanie do kolejki)
                    usrOrders.add(usrOrder);
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
        }
    }

    // po dodaniu bazy zmienie to na kwerendy
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
                "Extras:\n" +
                "    1. Frytki 5PLN, " +
                "    2. Ser 3PLN, " +
                "    3. Boczek 3PLN, " +
                "    4. Kotlet 3PLN, " +
                "    5. Sos 3PLN, " +
                "    6. Krążki cebulowe 1PLN,\n"
                );
    }
    public static void viewQueue(){
        System.out.println("        === Queue === ");
        System.out.print("In Progress   |");
        System.out.println(usrOrders);
        System.out.print("   Ready!");
    }

    @Override
    public void addToQueue(Order order) {

    }

    @Override
    public void removeFromQueue(Order order) {

    }


    //obsługa csv - to dodam na końcu
    @Override
    public void saveQueueToFile(String path) {

    }

    @Override
    public void loadQueueFromFile(String path) {

    }
}
