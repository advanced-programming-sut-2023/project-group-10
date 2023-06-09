package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;
import org.example.model.game.Item;

import java.util.ArrayList;
import java.util.Map;

public class TradeRequestMenuController {
    public Button donate;
    public Button request;
    public Label selectedUserLabel;
    public ListView<HBox> selectedUserResources;
    public ListView<HBox> usersListView = new ListView<HBox>();
    public ImageView itemImage;
    public Label itemNameLabel;
    public Label itemStorage;
    public Spinner<Integer> itemAmount;
    public TextField price;
    public TextArea messageField;
    private User selectedUser;
    private Item selectedItem;


    @FXML
    public void initialize() {
        showUsers();
        prepareUserLabel();

    }

    private void prepareUserLabel() {
        selectedUserLabel.setFont(new Font("Courier new", 24));
        selectedUserLabel.setTextAlignment(TextAlignment.CENTER);
    }

    private void showUsers() {
        ArrayList<HBox> usersBox = new ArrayList<>();
        for (int length = Stronghold.getCurrentBattle().getGovernments().length; length > 0; length--) {
            usersBox.add(new HBox());
        }
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (government.getOwner().getUsername().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername()))
                continue;
            HBox userInfo = new HBox();
            Text username = new Text();
            username.setText(government.getOwner().getUsername());
//            username.setOnMouseClicked(this::selectUser);
            userInfo.getChildren().add(username);
            usersBox.add(userInfo);
            userInfo.setId(Stronghold.getCurrentBattle().getGovernmentByOwnerId(username.getText()).getOwner().getUsername());
            usersListView.getItems().add(userInfo);
        }
        usersListView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                selectedUserLabel.setText(usersListView.getSelectionModel().getSelectedItem().getId());
                selectedUser = Stronghold.getCurrentBattle().getGovernmentByOwnerId(selectedUserLabel.getText()).getOwner();
                showUsersResources();
            }
        });

    }


    private void showUsersResources() {
        ArrayList<HBox> userResources = new ArrayList<>();
        for (Map.Entry<Item, Double> item : Stronghold.getCurrentBattle().getGovernmentByOwnerId(selectedUser.getUsername()).getItemList().entrySet()) {
            userResources.add(new HBox());
        }

        for (Map.Entry<Item, Double> item : Stronghold.getCurrentBattle().getGovernmentByOwnerId(selectedUser.getUsername()).getItemList().entrySet()) {
            HBox itemInfo = new HBox();
            Text itemName = new Text(item.getKey().getName());
            itemName.setFont(new Font("PT Mono", 14));
            itemInfo.getChildren().add(itemName);
            userResources.add(itemInfo);
            itemInfo.setId(item.getKey().getName());
            selectedUserResources.getItems().add(itemInfo);

        }
        selectedUserResources.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                itemNameLabel.setText(selectedUserResources.getSelectionModel().getSelectedItem().getId());
                selectedItem = Item.getItemByName(itemNameLabel.getText());


                itemStorage.setTextAlignment(TextAlignment.CENTER);
                itemStorage.setText(Stronghold.getCurrentBattle().getGovernmentByOwnerId
                        (selectedUser.getUsername()).getItemCount(selectedItem).toString());


                SpinnerValueFactory<Integer> valueFactory = //
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Stronghold.getCurrentBattle().getGovernmentByOwnerId(selectedUser.getUsername()).getItemCount(selectedItem).intValue(), 1);
                itemAmount.setValueFactory(valueFactory);



            }
        });

    }


    public void cancel(MouseEvent mouseEvent) throws Exception {
        new TradeMenuGFX().start(SignupMenu.stage);
    }

    public void submit(MouseEvent mouseEvent) {


    }

    public void donateSelected(MouseEvent mouseEvent) {
        donate.setBackground(Background.fill(Color.CORNFLOWERBLUE));
        request.setBackground(Background.fill(Color.LIGHTGRAY));

    }

    public void selectRequest(MouseEvent mouseEvent) {
        request.setBackground(Background.fill(Color.CORNFLOWERBLUE));
        donate.setBackground(Background.fill(Color.LIGHTGRAY));
    }

}
