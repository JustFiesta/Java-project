import java.util.ArrayList;

public class Order implements OrderOperations{
    int orderID;
    Status orderStatus = Status.In_progress;
    Client client;

    //Lista na produkty do zamówienia
    public static ArrayList<Product> productList = new ArrayList<>();



    @Override
    public void addProduct(Product newProduct) {
        productList.add(newProduct);
    }

    @Override
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    @Override
    public void showOrder() {
        System.out.println(productList);
    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
