package com.example.portal;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class AnimationLibrary {

   public static TranslateTransition slideTransition(Node targetNode, double fromX, double toX, double fromY, double toY, double durationInMillis) {
      TranslateTransition transition = new TranslateTransition(Duration.millis(durationInMillis), targetNode);
      transition.setFromX(fromX);
      transition.setToX(toX);
      transition.setFromY(fromY);
      transition.setToY(toY);

      return transition;
   }

   public static FadeTransition fadeTransition(Node targetNode, double fromValue, double toValue, double durationInMillis) {
      FadeTransition fade = new FadeTransition(Duration.millis(durationInMillis), targetNode);
      fade.setFromValue(fromValue);
      fade.setToValue(toValue);

      return fade;
   }


}
