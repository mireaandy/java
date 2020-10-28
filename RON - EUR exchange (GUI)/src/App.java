import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application
{
    private Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setAlignment(Pos.CENTER);
        mainScene = new Scene(layout, 175, 200);
        primaryStage.setTitle("Exhange Rate Analyzer");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}