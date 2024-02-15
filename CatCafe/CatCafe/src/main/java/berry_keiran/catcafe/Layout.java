package berry_keiran.catcafe;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

public class Layout extends BorderPane {

    private CafeSim cafeSim = new CafeSim(3);
    private Controller controller = new Controller(cafeSim);
    public static ToggleGroup toggleGroup = new ToggleGroup();
    public static VBox tileDetails = new VBox();
    public static VBox cafeInfo = new VBox();

    public Layout() {
        BorderPane page = new BorderPane();
        CafeSimView cafe = new CafeSimView(cafeSim, controller);
        BorderPane actionCommands = new BorderPane();

        HBox radioButtons = new HBox();
        BorderPane lowerActionCommands = new BorderPane();
        HBox resizeArea = new HBox();

        //set up radio buttons
        RadioButton table = new RadioButton("Table");
        RadioButton cat = new RadioButton("Cat");
        RadioButton kitten = new RadioButton("Kitten");
        RadioButton empty = new RadioButton("Empty");
        RadioButton view = new RadioButton("View");

        table.setToggleGroup(toggleGroup);
        cat.setToggleGroup(toggleGroup);
        kitten.setToggleGroup(toggleGroup);
        empty.setToggleGroup(toggleGroup);
        view.setToggleGroup(toggleGroup);
        view.setSelected(true); //set view to be the default radio button selection

        radioButtons.getChildren().setAll(table, cat, kitten, empty, view);
        radioButtons.setAlignment(Pos.CENTER);

        //set up lower area of bottom panel
        Button nextWeek = new Button("Next Week");
        Label resize = new Label("Resize: ");
        Button threeByThree = new Button("3x3");
        Button fiveByFive = new Button("5x5");
        Button nineByNine = new Button("9x9");
        resizeArea.getChildren().setAll(resize, threeByThree, fiveByFive, nineByNine);
        lowerActionCommands.setLeft(nextWeek);
        lowerActionCommands.setRight(resizeArea);

        actionCommands.setTop(radioButtons);
        actionCommands.setBottom(lowerActionCommands);

        cafeInfo.setAlignment(Pos.CENTER);
        tileDetails.setAlignment(Pos.CENTER);

        //set functionality for the buttons

        nextWeek.setOnAction(ActionEvent -> { cafe.nextWeek(); });

        threeByThree.setOnAction(ActionEvent -> { cafe.resize(3); });

        fiveByFive.setOnAction(ActionEvent -> { cafe.resize(5); });

        nineByNine.setOnAction(ActionEvent -> { cafe.resize(9); });

        //set portions of the page in place
        page.setTop(cafeInfo);
        page.setLeft(tileDetails);
        page.setCenter(cafe);
        page.setBottom(actionCommands);

        setCenter(page);
    }
}
