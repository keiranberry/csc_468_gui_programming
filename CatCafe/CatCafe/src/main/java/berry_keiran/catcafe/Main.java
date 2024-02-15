/*
_X__ Followed the class OOP diagram
_X__ Observer pattern (ignores tiers)


_X__ 1.	Tier: Views and tile area
_X__ a. All objects (ignoring the sim area) (-1 for each missing)
_X__ b. Have a starting number of tiles in sim area
_X__ c. Able to add/remove a tile area properly (-33% for each error)
_X__ d. Info bar listed correctly with all the required elements (-25% for each error)
_X__ f. Tile Text correct in tile area (-25% per error)
_X__ g. Radio buttons update properly
_X__ h. Selecting a rectangle with “view” updates the tile area info (-50% per error)


_X__ 2a Tier: Advanced functionality
_X__ a. Next week button has some noticeable effect*
_X__ b. Tile areas updated properly on “next” (-33% per error)*
_X__ c. Sim info bar updated properly (-25% per error)
_X__ d. Selecting a tile after an update shows the new information


_X__ 2b: Layout
_X__ a. Location of all items in correct spot (-20% per error)
_X__ b. Layout still correct on window resize (-20%  for minor error)
_X__ c. Resize grid at minimum resets the grid and info (-50% if minor error)
_X__ d. Everything still working that is listed above with resize (-50% if minor error)



Final Tier: Extensions 30
Extension 1: <1c> <15 points> <Adoption Observer Pattern (additional approved extension)>:
    Grading tags placed in CafeSim, Kitten, and Cat classes as detailed on D2L. Grading tags
    are as follows: GRADING: 1.B OBSERVER-MODEL, GRADING: 1.B SUBJECT- MODEL, GRADING: 1.B TRIGGER-MODEL.
    Upon a pet's countdown reaching zero, it fires a change to the adoption count of the sim.
Extension 2: <2a> <5 points> <Outline tile being shown in information area>:
    When a tile is selected to have its information displayed, it gets an outline in the application. This
    outline persists as long as the tile is being displayed, and is removed if another tile is being
    displayed. If the tile changes, its current information will still be displayed and the outline will
    stay. (For what it's worth, the tile details staying current is another extension- I'm just not sure
    it is done the right way to get the points so I am not claiming it for my additional points needed)
Extension 3: <2d> <10 points> <Add colors to indicate a tile area's type>:
    Each tile area type has its own unique color. The colors are all light, unsaturated pastel colors so that
    the text on the tile remains accessible to be read. The selections for colors are as follows:
    Empty- Yellow; Table- Red/pink; Kitten- blue; Cat- green
*/
package berry_keiran.catcafe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Layout layout = new Layout();
        Scene scene = new Scene(layout, 600, 300);

        stage.setScene(scene);
        stage.setTitle("Cat Cafe Simulator");
        stage.show();
    }
}