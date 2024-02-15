package berry_keiran.catcafe;

public class FloorArea {

    public int totalCost;
    public int age = 0;
    public int weeklyCost;
    public int initialCost;
    public String name;

    public void nextWeek() {
        this.totalCost += this.weeklyCost;
        this.age += 1;
    }
}
