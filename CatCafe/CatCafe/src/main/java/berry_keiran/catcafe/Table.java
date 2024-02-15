package berry_keiran.catcafe;

public class Table extends FloorArea {

    private int weeklyRevenue = 150;
    public int totalRevenue = 0;

    public Table() {
        this.weeklyCost = 50;
        this.initialCost = 300;
        this.name = "-T-";
    }

    public int getWeeklyRevenue() {
        return this.weeklyRevenue;
    }

    @Override
    public void nextWeek() {
        this.totalCost += this.weeklyCost;
        totalRevenue += this.weeklyRevenue;
        this.age++;
    }
}
