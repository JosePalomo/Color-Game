package project3;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class ColorTrap extends Application
{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown = new Text(0,0,"");
    private Timeline timeline;
    private int count = 0;
    private final int TIMER = 15;
    private Timeline colorTimer;
    public final ColorsEnum [] colors = ColorsEnum.values();
    private Text scores = new Text(0,0, "Score: ");
  
    private Text trapWord = new Text(0,0,"");
    private  Text texts[] = new Text[7]; 
    private Text time = new Text(0,0,"Time: ");
    private int score;
    private int colorCount=0;
    
    HBox hb1 = new HBox(10);
    private FlowPane fPane =  new FlowPane();
    private HBox topHBOX = new HBox();
    
    Region bottomReg1 = new Region();
    Pane imageDisplay = new Pane();
    Region bottomReg3 = new Region();
    Region topReg1 = new Region();
    Region topReg2 = new Region();
    
   public final BackgroundColors[] bgColors = BackgroundColors.values();
    private String s = bgColors[colorCount]+ "";
    Image correct = new Image("project3/images/correct.png");
    ImageView correctAns = new ImageView(correct);
    Image wrong = new Image("project3/images/wrong.png");
    ImageView wrongAns = new ImageView(wrong);
   
    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
       primaryStage.setMinHeight(300);
       primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {


                    if(count >= 0)
                    {
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();

    }
    
    public void endOfGame()
    {
        //TODO complete this method as required.
        colorTimer.stop();
        borderPane.getChildren().removeAll(topHBOX,fPane,hb1);
        
        Button playAgain = new Button("Play again");
        VBox vb1 = new VBox(10);
        scores.setText("Your Score: " +score);
        vb1.getChildren().addAll(scores,playAgain);
        
        borderPane.setCenter(vb1);
        vb1.setAlignment(Pos.CENTER);
        
        playAgain.setOnMouseClicked(e ->{
            borderPane.getChildren().removeAll(vb1);
            score = 0;
          
            borderPane.setTop(topHBOX);
            borderPane.setAlignment(trapWord,Pos.CENTER);
   
            borderPane.setCenter(fPane);
            fPane.setAlignment(Pos.BASELINE_CENTER);
            scores.setText("Score: 0" );
            hb1.getChildren().removeAll(scores, bottomReg1,imageDisplay,bottomReg3, time, txtCountDown);
            imageDisplay.getChildren().removeAll(wrongAns,correctAns);
            hb1.getChildren().addAll(scores, bottomReg1,imageDisplay,bottomReg3, time, txtCountDown);
       
            hb1.setHgrow(bottomReg1,Priority.ALWAYS);
            hb1.setHgrow(bottomReg3,Priority.ALWAYS);
            borderPane.setBottom(hb1);
            hb1.setMaxHeight(scene.getHeight() * .1);
        
    
        borderPane.setMargin(trapWord,new Insets(0,0,25,0));
        borderPane.setMargin(hb1, new Insets(10));
        
         colorTimer = new Timeline(new KeyFrame(
                Duration.millis(250), f -> {
                
               s = bgColors[colorCount] +"";
                borderPane.setStyle("-fx-background-color: " + s);
                
                if(colorCount < 5)
                {
                    colorCount++;
                }
                else{
                    colorCount = 0;
                }
                    
            }));
         colorTimer.setCycleCount(Timeline.INDEFINITE);
        colorTimer.play();
            startPlay();
        });
        
       
        
    }


    public void checkChoice(Text choice)
    {
        
        //TODO complete this method as required.
        Color cor = Color.web(Color.valueOf(choice.getText()) +"");
                
        if(cor.equals(Color.valueOf(trapWord.getFill() +"")))
        {
            score++;
            scores.setText("Score: " + score);
            imageDisplay.getChildren().removeAll(wrongAns,correctAns);
           imageDisplay.getChildren().add(correctAns);
            
            
        }
        else{
            imageDisplay.getChildren().removeAll(correctAns,wrongAns);
            imageDisplay.getChildren().add(wrongAns);
            
        }
        


        //Do NOT add any code after this comment
        //Choose a new trap word and options list
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        //TODO complete this method as required.
        
        int ran =(int)(Math.random() * colors.length);
        int ran2 = (int)(Math.random() * colors.length);
        trapWord.setText(colors[ran2] +"");
        trapWord.setFill(Color.valueOf(colors[ran]+ ""));
        trapWord.setFont(Font.font("Times New Roman", FontWeight.NORMAL,60));
        


    }
    
    public void colorNameOptions()
    {
        //TODO complete this method as required.
      
       
       ArrayList<String> wordText = new ArrayList<String>();
       ArrayList<String> wordColor = new ArrayList<String>();
       for(int i = 0; i < colors.length; i++)
       {
           
           wordText.add(colors[i]+"");
           wordColor.add(colors[i]+"");
       }
        
        for(int i = wordText.size() -1 ; i >= 0; i--)
        {
            int rand1 = (int)(Math.random()* wordText.size());
            int rand2 = (int)(Math.random() * wordColor.size());
       
            texts[i].setText(wordText.get(rand1));
            texts[i].setFill(Color.valueOf(wordColor.get(rand2)));
            wordText.remove(wordText.get(rand1));
            wordColor.remove(wordColor.get(rand2));
            
            Text txt = texts[i];
            txt.setOnMouseClicked(e ->{
               checkChoice(txt);
            });
        }
       
       
    }

    public void initializeGame()
    {
        //TODO complete this method as required.
       
        
        
        colorTimer = new Timeline(new KeyFrame(
                Duration.millis(250), e -> {
                
               s = bgColors[colorCount] +"";
                borderPane.setStyle("-fx-background-color: " + s);
                
                if(colorCount < 5)
                {
                    colorCount++;
                }
                else{
                    colorCount = 0;
                }
                    
            }));
         colorTimer.setCycleCount(Timeline.INDEFINITE);
         colorTimer.play();
         
         
         trapWord.setFont(Font.font("Times New Roman",FontWeight.NORMAL,60));
         topHBOX.getChildren().addAll(topReg1, trapWord, topReg2);
         topHBOX.setHgrow(topReg1,Priority.ALWAYS);
         topHBOX.setHgrow(topReg2,Priority.ALWAYS);
         topHBOX.setPrefHeight(borderPane.getHeight() / 100 *.35);
          for(int i =0 ; i< colors.length; i++)
          {
           
            texts[i] = new Text(0,0, colors[i]+"");
            texts[i].setFont(Font.font("Times New Roman", FontWeight.NORMAL, 40));
            
            fPane.getChildren().add(texts[i]);
         
          }
          
          fPane.setHgap(10.0);

          fPane.setMaxWidth(450);
          fPane.setMaxHeight(borderPane.getHeight() /100 * .55 );
    
    
      
    
    
       correctAns.setFitHeight(scene.getHeight() * .05);
       correctAns.setFitWidth(scene.getWidth() *.05);
       wrongAns.setFitHeight(scene.getHeight() * .05);
       wrongAns.setFitWidth(scene.getWidth() * .05);
       
        
        borderPane.setTop(topHBOX);
              
   
       

        borderPane.setCenter(fPane);
        fPane.setAlignment(Pos.BASELINE_CENTER);
       
        scores.setText("Score: 0" );
        hb1.getChildren().addAll(scores, bottomReg1,imageDisplay,bottomReg3, time, txtCountDown);
       
        hb1.setHgrow(bottomReg1,Priority.ALWAYS);
        hb1.setHgrow(bottomReg3,Priority.ALWAYS);
        borderPane.setBottom(hb1);
        hb1.setPrefHeight(borderPane.getHeight() * .1 /100);
        
    
        borderPane.setMargin(trapWord,new Insets(0,0,25,0));
        borderPane.setMargin(hb1, new Insets(10));
       
     
       
       
   //  formatGame();
        
    
      

    }
    public void formatGame()
    {
         
        borderPane.setTop(trapWord);
        borderPane.setAlignment(trapWord,Pos.CENTER);
       

        borderPane.setCenter(fPane);
        fPane.setAlignment(Pos.BASELINE_CENTER);
       
        scores.setText("Score: 0" );
        hb1.getChildren().addAll(scores, bottomReg1,imageDisplay,bottomReg3, time, txtCountDown);
       
        hb1.setHgrow(bottomReg1,Priority.ALWAYS);
        hb1.setHgrow(bottomReg3,Priority.ALWAYS);
        borderPane.setBottom(hb1);
        
    
        borderPane.setMargin(trapWord,new Insets(0,0,25,0));
        borderPane.setMargin(hb1, new Insets(5));
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}