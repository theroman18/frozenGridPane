package com.example.frozengridpane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class HelloController implements Initializable {

    @FXML
    private ScrollPane headerScrollPane;
    @FXML
    private GridPane headerGridPane;

    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private GridPane mainGridPane;

    static int TOTAL_COLUMNS = 19;
    static int TOTAL_CONTROLS = TOTAL_COLUMNS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupHeaderGridPane();
        setupMainGridPane();
    }


    private void setupHeaderGridPane() {
        for (int i = 0; i < TOTAL_COLUMNS; i++) {
            Label label = new Label("Column " + String.valueOf(i+1));
            label.setFont(Font.font(12));
            label.setMinWidth(95);
            label.setAlignment(Pos.CENTER);
            headerGridPane.addColumn(i, label);

            ColumnConstraints columnConstraints = new ColumnConstraints();
//            columnConstraints.setPercentWidth(5.263);
//            columnConstraints.setMinWidth(95);
            headerGridPane.getColumnConstraints().add(columnConstraints);

        }

        headerGridPane.setGridLinesVisible(true);
        headerScrollPane.hvalueProperty().bind(mainScrollPane.hvalueProperty());
//        headerGridPane.prefWidthProperty().bind(mainGridPane.prefWidthProperty());
//        headerGridPane.minWidthProperty().bind(mainGridPane.minWidthProperty());
//        headerGridPane.maxWidthProperty().bind(mainGridPane.maxWidthProperty());

        headerGridPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("headerGridPane width = " + newValue);
        });

//        headerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        headerScrollPane.setStyle("-fx-font-size: 1;");

        String css = this.getClass().getResource("/com/example/frozengridpane/css.css").toExternalForm();
        headerScrollPane.getStylesheets().add(css);
        headerScrollPane.getStyleClass().add("headerScrollPane");
//        removeScrollBar(headerScrollPane);


        // https://stackoverflow.com/questions/12678317/how-to-access-the-scrollbars-of-a-scrollpane
        headerScrollPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("hello world");
            Set<Node> nodes = headerScrollPane.lookupAll(".scroll-bar");
            for (final Node node : nodes) {
                if (node instanceof ScrollBar) {
                    ScrollBar sb = (ScrollBar) node;
                    if (sb.getOrientation() == Orientation.HORIZONTAL) {
                        System.out.println("horizontal scrollbar visible = " + sb.isVisible());
                        sb.setManaged(false);
                    }
                }
            }
        });
    }

    public static <T extends Control> void removeScrollBar(T table) {
        ScrollBar scrollBar = (ScrollBar) table.queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);
        /*
         *This null-check is for safety reasons if you are using when the table's skin isn't yet initialized.
         * If you use this method in a custom skin you wrote, where you @Override the layoutChildren method,
         * use it there, and it should be always initialized, so null-check would be unnecessary.
         *
         */
        if (scrollBar != null) {
            scrollBar.setPrefHeight(0);
            scrollBar.setMaxHeight(0);
            scrollBar.setOpacity(1);
            scrollBar.setVisible(false); // If you want to keep the scrolling functionality then delete this row.
        }
    }


    private void setupMainGridPane() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Dog", "Cat", "Bird", "Hamster");

        for (int i = 0; i < TOTAL_COLUMNS; i++) {
            for (int j = 0; j < TOTAL_CONTROLS; j++) {
                ComboBox<String> comboBox = new ComboBox<>(observableList);
                comboBox.getSelectionModel().select(0);
                comboBox.setMinWidth(95);
                mainGridPane.addRow(i, comboBox);

                ColumnConstraints columnConstraints = new ColumnConstraints();
//                columnConstraints.setPercentWidth(5.263);
//                columnConstraints.setMinWidth(95);
                mainGridPane.getColumnConstraints().add(columnConstraints);
            }
        }
        mainGridPane.setGridLinesVisible(true);

        mainGridPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("mainGridPane width = " + newValue);
        });
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }
}