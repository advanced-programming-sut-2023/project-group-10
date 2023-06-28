package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Popup;
import org.example.controller.TradeMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.view.enums.messages.TradeMenuMessages;

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
    public Button submitButton;
    private User selectedUser;
    private Item selectedItem;
    private boolean requestSelected;


    @FXML
    public void initialize() {
        showUsers();
        prepareUserLabel();

        addPositionValidityListener(price);

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
            if (!item.getKey().isSellable())
                continue;
            HBox itemInfo = new HBox();
            Text itemName = new Text(item.getKey().getName());
            itemName.setFont(new Font("PT Mono", 9));
            itemInfo.getChildren().add(itemName);
            userResources.add(itemInfo);
            itemInfo.setId(item.getKey().getName());
            selectedUserResources.getItems().add(itemInfo);
        }

        itemAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Stronghold.getCurrentBattle().getGovernmentByOwnerId(selectedUser.getUsername()).getItemCount(selectedItem).intValue(), 0));

        selectedUserResources.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                itemNameLabel.setText(selectedUserResources.getSelectionModel().getSelectedItem().getId());
                selectedItem = Item.getItemByName(itemNameLabel.getText());


                itemStorage.setTextAlignment(TextAlignment.CENTER);
                itemStorage.setText(Stronghold.getCurrentBattle().getGovernmentByOwnerId
                        (selectedUser.getUsername()).getItemCount(selectedItem).toString());

                SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) itemAmount.getValueFactory();
                valueFactory.setMin(0);
                valueFactory.setMax(Stronghold.getCurrentBattle().getGovernmentByOwnerId(selectedUser.getUsername()).getItemCount(selectedItem).intValue());
            }
        });
    }


    public void cancel(MouseEvent mouseEvent) throws Exception {
        new TradeMenuGFX().start(ShopMenuGFX.stage);
    }

    public void submit(MouseEvent mouseEvent) {
        TradeMenuMessages message;
        System.out.println(Integer.parseInt(price.getText()));
        if (requestSelected)
            message = TradeMenuController.sendRequest(selectedItem.getName(), itemAmount.getValue(), Integer.parseInt(price.getText()), messageField.getText(), selectedUser.getUsername());
        else
            message = TradeMenuController.sendRequest(selectedItem.getName(), itemAmount.getValue(), 0, messageField.getText(), selectedUser.getUsername());
        Text text = new Text();
        System.out.println(message.name());
        switch (message) {
            //convert these to popups
            case INVALID_TYPE:
                text.setText("You should select an item");
                text.setFill(Color.ORANGERED);
                break;

            case TRADE_ADDED_TO_TRADELIST:
                text.setText("you've successfully purchased the item!");
                text.setFill(Color.LIMEGREEN);
                break;
        }
        text.setFont(new Font("Courier new", 10));
        Popup popup = new Popup();
        popup.getContent().add(text);
        popup.setAutoHide(true);
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (!popup.isShowing()) {
                            popup.show(ShopMenuGFX.stage);
                            popup.setX(0);
                        }
                    }
                };
        submitButton.setOnAction(event);
        if (message.equals(TradeMenuMessages.TRADE_ADDED_TO_TRADELIST)) {
            try {
                new TradeMenuGFX().start(ShopMenuGFX.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void donateSelected(MouseEvent mouseEvent) {
        donate.setBackground(Background.fill(Color.CORNFLOWERBLUE));
        request.setBackground(Background.fill(Color.LIGHTGRAY));
        requestSelected = false;

    }

    public void selectRequest(MouseEvent mouseEvent) {
        request.setBackground(Background.fill(Color.CORNFLOWERBLUE));
        donate.setBackground(Background.fill(Color.LIGHTGRAY));
    }

    public void addPositionValidityListener(TextField number) {
        number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!number.getText().matches("\\d*"))
                number.setText(oldValue);
            else
                number.setText(newValue);

        });
    }

}
