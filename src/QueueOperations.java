public interface QueueOperations{
    void addToQueue(Order order);
    void removeFromQueue(Order order);
    void saveQueueToFile(String path);
    void loadQueueFromFile(String path);
}
