package finalProject;

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;

public class FlipCard extends Application {

    private Image backImage = new Image("image/card/b2fv.png");
    private int cardCnt = 0;
    private Card card1, card2; //the current cards that are showing
    private PlayerPane playerPane;  //an HBox that contains player controls
    private CardPane cardPane; //a GridPane that contains the Cards
    private Font myFontBold = javafx.scene.text.Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 20);
    private Font myFontLight = javafx.scene.text.Font.font("Courier", FontWeight.LIGHT, FontPosture.ITALIC, 20);
    static ArrayList<Card> cards = new ArrayList<>();
    int p1Score = 0;
    int p2Score = 0;
    int cardsFlipped = 0;
    private int currentPlayer = 1;
    //Create alerts
    Alert matchAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert mismatchAlert = new Alert(Alert.AlertType.INFORMATION);

    @Override // Override the start method in the Application class 
    public void start(Stage primaryStage) {

        BorderPane bPane = new BorderPane();
        playerPane = new PlayerPane();
        cardPane = new CardPane();
        bPane.setTop(playerPane);
        bPane.setCenter(cardPane);

        // Create a scene and place it in the stage
        Scene scene = new Scene(bPane);
        primaryStage.setTitle("Memory Game"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /*
    * This method process a mouse click on the card.  This is where
    * the game is played!
     */
    private void processCardClicked(Card c) {

        //flip the card
        c.flip();
        cards.add(c);
        cardsFlipped++;
        //On the flip of the second card check for match/mismatch
        if (cardsFlipped == 2) {
            if (cards.get(0).equals(cards.get(1))) {
                //if there is a match add one to the current players score and
                //display a match alert and turn play over to the other player
                if (currentPlayer == 1) {
                    p1Score++;
                } else {
                    p2Score++;
                }
                playerPane.showMatch(currentPlayer);
                matchAlert.setTitle("Game Information");
                matchAlert.setHeaderText("Match!");
                matchAlert.setContentText("You got a match.");
                matchAlert.showAndWait();
                //turn control over to other player
                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }
                //disable the cards
                cards.get(0).setDisable(true);
                cards.get(1).setDisable(true);

            } else {
                playerPane.showMismatch(currentPlayer);
                mismatchAlert.setTitle("Game Information");
                mismatchAlert.setHeaderText("Mismatch!");
                mismatchAlert.setContentText("You got a mismatch.");
                mismatchAlert.showAndWait();
                //turn control over to other player
                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }
                cards.get(0).flip();
                cards.get(1).flip();
            }
            cards.clear();
            cardsFlipped = 0;
        }

        //if there is a match add one to the current players score and display
        //a match alert and turn play over to the other player. disable 
        //the card so that it stays face-up.
        //if there is a mismatch, display mismatch alert and turn play 
        //over to the other player. Turn the cards face-down.
    }

    private class PlayerPane extends HBox {

        private Label[] lblPlayerScore = {new Label("0"), new Label("0")};
        private Label[] lblPlayerName = {new Label("Player 1:"), new Label("Player 2:")};
        private TextArea taResults = new TextArea();

        public PlayerPane() {
            //add the control to the pane, with proper defaults.  Use
            //the fonts declared in the outer class as appropriate.  You
            //can also use your own fonts and colors if you'd like.
            this.setPadding(new Insets(15, 15, 15, 15));
            this.setSpacing(20);
            this.getChildren().addAll(lblPlayerName[0]);
            this.getChildren().addAll(lblPlayerScore[0]);
            this.getChildren().addAll(lblPlayerName[1]);
            this.getChildren().addAll(lblPlayerScore[1]);
            this.getChildren().addAll(taResults);
        }

        public void showMismatch(int cPlayer) {
            taResults.setText("Player " + cPlayer + ": You have a mismatch.");
        }

        public void showMatch(int cPlayer) {
            taResults.setText("Player " + cPlayer + ": You have a match.");
        }
    }

    private class CardPane extends GridPane {

        Label p1Label = new Label();
        Label p2Label = new Label();

        public CardPane() {
            ArrayList<Card> deck = new ArrayList<Card>();
            int n = 23;
            for (int k = 1; k <= 12; k++) {
                Image aCard = new Image("image/card/" + k + ".png");
                //here is where I duplicate the card so that each card 
                //has a twin

                for (int i = 0; i < 2; i++) {
                    //create a card object
                    Card twinCard = new Card(aCard);
                    //set the front and back images on the card
                    twinCard.setFrontImage(aCard);
                    twinCard.setBackImage(twinCard.getBackImage());
                    //Set starting image
                    twinCard.setImage(backImage);

                    //set the cards value
                    twinCard.setValue("" + k);
                    //add the card to the deck
                    deck.add(twinCard);
                    //register the card with the processCardClicked method
                    twinCard.setOnMouseClicked((MouseEvent e) -> {
                        processCardClicked(twinCard);
                        p1Label.setText(String.valueOf(p1Score));
                        ((Label) playerPane.getChildren().get(1)).setText(p1Score + "");

                        ((Label) playerPane.getChildren().get(3)).setText(p2Score + "");
                        p2Label.setText(String.valueOf(p2Score));
                    });
                    //  in order for that method to execute when the 
                    //  mouse clicks on the card
                }
            }
            //shuffle the deck
            java.util.Collections.shuffle(deck);
            //set the padding attributes
            this.setPadding(new Insets(15, 15, 15, 15));
            this.setHgap(5);
            this.setVgap(5);
            //Add Labels
            this.add(p1Label, 9, 9);
            this.add(p2Label, 7, 9);
            this.add(playerPane, 5, 9);
            //add each card to the this container's grid in the 
            //  appropriate row and column
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    this.add(deck.get(n), i, j);
                    n--;
                }
            }
        }
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
