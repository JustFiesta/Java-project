public interface QueueOperations {
    void addProduct(Product newProduct);
    void removeProduct(Product product);
    void showQueue();
    void saveQueueToFile(String path);
    void loadQueueFromFile(String path);
}
