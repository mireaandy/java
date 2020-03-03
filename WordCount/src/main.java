package WordCount;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class main extends Application
{
    private Scene selectFileScene, wordCountScene;
    private final Button selectFile = new Button("Select File");
    private final Button newFile = new Button("Try with a new file");
    private final Button exit = new Button("Exit");
    private final FileChooser fileChooser = new FileChooser();
    private final Label result = new Label("0");
    private  Label details;
    private File file;
    private Scanner fileScanner;

    private int getWordCount(File file)
    {
        int answer = 0;
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.toString());
        }
        fileScanner.useDelimiter(" ");
        while(fileScanner.hasNext())
        {
            answer++;
            System.out.println(fileScanner.next());
        }
        return answer;
    }

    private Scene setupScene(String scene)
    {
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setAlignment(Pos.CENTER);
        switch (scene)
        {
            case "selectFileScene":
                layout.getChildren().add(selectFile);
                return new Scene(layout, 175, 100);
            case "wordCountScene" :
                HBox buttons = new HBox(25);
                details = new Label("The number of words inside \n" + file.getName() + " is :");
                details.setTextAlignment(TextAlignment.CENTER);
                buttons.setPadding(new Insets(15, 15, 15, 15));
                buttons.setAlignment(Pos.CENTER);
                buttons.getChildren().addAll(newFile, exit);
                layout.getChildren().addAll(details, result, buttons);
                return new Scene(layout, 270, 220);
        }
        return null;
    }

    public static void main(String args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Word Count");
        selectFileScene = setupScene("selectFileScene");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
        selectFile.setOnAction(e ->
        {
            do {
                file = fileChooser.showOpenDialog(primaryStage);
            }while(file == null);
            wordCountScene = setupScene("wordCountScene");
            result.setText(String.valueOf(getWordCount(file)));
            primaryStage.setScene(wordCountScene);
        });
        newFile.setOnAction(e ->
        {
            primaryStage.setScene(selectFileScene);
        });
        exit.setOnAction(e -> primaryStage.close());
        primaryStage.setScene(selectFileScene);
        primaryStage.show();
    }
}
