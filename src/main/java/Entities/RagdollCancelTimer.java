package Entities;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;



public class RagdollCancelTimer extends Application implements NativeKeyListener {
    private int seconds = 0;
    private int RAGDOLL_CANCEL_TIME = 5;
    private Timeline timeline;
    private Label timerLabel;
    private Button startButton = new Button("Start");

    @Override
    public void start(Stage stage) throws Exception {
        timerLabel = new Label("0 seconds");

        startButton.setOnAction(e -> startTimer());

        VBox vBox = new VBox(timerLabel, startButton);
        vBox.setAlignment(Pos.CENTER); // Center alignment

        Scene scene = new Scene(vBox, 200, 100);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DIGIT0 || e.getCode() == KeyCode.NUMPAD0) {
                // Fire the button's action
                startButton.fire();
            }
        });

        stage.setScene(scene);
        stage.setTitle("TSB counter");

        // Set stage always on top of windows or apps   
        stage.setAlwaysOnTop(true);

        stage.show();

        // Register the NativeKeyListener
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }

        // Add the NativeKeyListener to GlobalScreen
        GlobalScreen.addNativeKeyListener(this);
    }

    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timerLabel.setText(seconds + " seconds");

            if (seconds >= RAGDOLL_CANCEL_TIME) {
                timeline.stop();
                seconds = 0;
                startButton.setStyle("-fx-background-color: green;");

            } else if (seconds > 0) {
                startButton.setStyle("-fx-background-color: red;");
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_0) {
            startButton.fire();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Not used
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Not used
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// @Override
// public void start(Stage stage) throws Exception {
// timerLabel = new Label("0 seconds");

// startButton.setOnAction(e -> startTimer());

// if (this.seconds == 30) {
// timeline.stop();
// seconds = 0;
// startButton.setStyle("-fx-background-color: green;");

// } else if (this.seconds > 0) {
// startButton.setStyle("-fx-background-color: red;");
// }

// VBox vBox = new VBox(timerLabel, startButton);
// vBox.setAlignment(Pos.CENTER); // Center alignment

// Scene scene = new Scene(vBox, 200, 100);

// scene.setOnKeyPressed(e -> {
// if (e.getCode() == KeyCode.DIGIT0 || e.getCode() == KeyCode.NUMPAD0) {
// // Fire the button's action
// startButton.fire();
// }
// });

// stage.setScene(scene);
// stage.setTitle("TSB counter");
// stage.show();
// }

// private void startTimer() {
// if (timeline != null) {
// timeline.stop();
// }

// timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
// seconds++;
// timerLabel.setText(seconds + " seconds");

// if (seconds >= RAGDOLL_CANCEL_TIME) {
// timeline.stop();
// seconds = 0;
// startButton.setStyle("-fx-background-color: green;");

// } else if (seconds > 0) {
// startButton.setStyle("-fx-background-color: red;");
// }
// }));

// timeline.setCycleCount(Animation.INDEFINITE);
// timeline.play();
// }
// }
