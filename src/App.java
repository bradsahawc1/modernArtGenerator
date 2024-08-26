import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class App extends Application {

    private String[] list = {"Circles", "Rectangles"};
    private boolean recir = true;
    private int numOfObjects = 50;
    private int maxSize = 100;

    @Override
    public void start(Stage primaryStage) {
        BorderPane canvas = new BorderPane();

        Pane ArtworkPane = new Pane();
        ArtworkPane.setPrefSize(600,300);

        ComboBox<String> cbox = new ComboBox<>();
        ObservableList<String> items =
                FXCollections.observableArrayList(list);
        cbox.getItems().addAll(items);
        cbox.setValue("Circles");
        cbox.setOnAction(e -> {
                    if (cbox.getValue().equals("Circles")) {
                        recir = true;
                    } else {
                        recir = false;
                    }
                }
        );

        TextField num = new TextField();
        num.setPrefWidth(50);
        num.setText("50");
        num.setOnAction(e -> numOfObjects = parseInt(num.getText()));

        TextField num2 = new TextField();
        num2.setPrefWidth(50);
        num2.setText("100");
        num2.setOnAction(e -> maxSize = parseInt(num2.getText()));

        Button clear = new Button();
        clear.setText("Clear");
        clear.setOnAction(e -> ArtworkPane.getChildren().clear());

        Button draw = new Button();
        draw.setText("Draw");
        draw.setOnAction(e -> {
            if(recir == true) {
                ArtworkPane.getChildren().clear();
                ArtworkPane.getChildren().addAll(randomCircles(numOfObjects, maxSize));
            }
            else {
                ArtworkPane.getChildren().clear();
                ArtworkPane.getChildren().addAll(randomRectangles(numOfObjects, maxSize));
            }
        });

        javafx.event.EventHandler<ActionEvent> eventHandler = (e -> {
            if(recir == true) {
                ArtworkPane.getChildren().clear();
                ArtworkPane.getChildren().addAll(randomCircles(numOfObjects, maxSize));
            }
            else {
                ArtworkPane.getChildren().clear();
                ArtworkPane.getChildren().addAll(randomRectangles(numOfObjects, maxSize));
            }
        });

        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(500), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);

        Button play = new Button("Play");
        play.setOnAction(e -> {
            if (play.getText().equals("Play")) {
                animation.play();
                play.setText("Pause");
            } else {
                animation.pause();
                play.setText("Play");
            }
        });

        Slider  speed = new Slider();
        speed.setMin(10);
        speed.setMax(100);
        speed.valueProperty().addListener(ov ->
                animation.setRate(speed.getValue()));

        HBox DrawingBarPane = new HBox();
        DrawingBarPane.setSpacing(10);
        DrawingBarPane.getChildren().add(cbox);
        DrawingBarPane.getChildren().add(new Label("Object Count:"));
        DrawingBarPane.getChildren().add(num);
        DrawingBarPane.getChildren().add(new Label("Max Size:"));
        DrawingBarPane.getChildren().add(num2);
        DrawingBarPane.getChildren().add(draw);
        DrawingBarPane.getChildren().add(clear);

        HBox AnimationPane = new HBox();
        AnimationPane.setPadding(new Insets(10,10,10,10));
        AnimationPane.setSpacing(10);
        AnimationPane.getChildren().add(new Label("Animation"));
        AnimationPane.getChildren().add(play);
        AnimationPane.getChildren().add(new Label("Speed: "));
        AnimationPane.getChildren().add(speed);

        VBox controlBarPane = new VBox();
        controlBarPane.setPadding(new Insets(10,10,10,10));
        controlBarPane.setSpacing(10);
        controlBarPane.getChildren().add(DrawingBarPane);
        controlBarPane.getChildren().add(AnimationPane);

        canvas.setCenter(ArtworkPane);
        canvas.setBottom(controlBarPane);

        Scene scene = new Scene(canvas, Color.WHITE);
        primaryStage.setTitle("Modern Art Generator 2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
        }

    public Paint randomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }
    public Rectangle[] randomRectangles(int objects, int maxsize) {
        Random random = new Random();
        Rectangle[] rec = new Rectangle[objects + 1];


        for (int i = 0; i <= objects; i++) {
            double x = (random.nextDouble() * (maxsize - 10)) + 10;
            double y = (random.nextDouble() * (maxsize - 10)) + 10;
            float z = (float) (Math.random() * 575);
            float t = (float) (Math.random() * 275);
            float v = (float) (Math.random());

            Rectangle r = new Rectangle();
            r.setHeight(x);
            r.setWidth(y);
            r.setFill(randomColor());
            r.setOpacity(v);
            r.setX(z);
            r.setY(t);

            rec[i] = r;
        }
        return rec;
    }
    private Circle[] randomCircles(int objects, int maxsize) {
        Random random = new Random();
        Circle[] cir = new Circle[objects + 1];

        for (int i = 0; i <= objects; i++) {
            double x = (random.nextDouble() * (maxsize - 10)) + 10;
            float z = (float) (Math.random() * 575);
            float t = (float) (Math.random() * 275);
            float v = (float) (Math.random());

            Circle c = new Circle();
            c.setRadius(x);
            c.setCenterX(z);
            c.setCenterY(t);
            c.setFill(randomColor());
            c.setOpacity(v);

            cir[i] = c;
        }
        return cir;
    }
}
