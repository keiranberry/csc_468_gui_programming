package berry_keiran.catcafe;

public class Empty extends FloorArea {

    public Empty() {
        this.weeklyCost = 10;
        this.initialCost = 200;
        this.name = "-E-";
    }

    @Override
    public void nextWeek() {
        this.totalCost += this.weeklyCost;
        this.age++;
    }
}
