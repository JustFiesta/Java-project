public class Drinks extends Product{
    public class Dishes extends Product{
        public Dishes(String name, int price){
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + price;
        }
    }
}
