package com.jonathan.controller;

import com.jonathan.MainApp;
import com.jonathan.dao.CategoryDaoImpl;
import com.jonathan.dao.ItemDaoImpl;
import com.jonathan.entity.Category;
import com.jonathan.entity.Item;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 1772004 Jonathan Bernad
 */
public class MainFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDesc;
    @FXML
    private CheckBox recommendedCheckBox;
    @FXML
    private ComboBox<Category> comboCategory;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> col01;
    @FXML
    private TableColumn<Item, String> col02;
    @FXML
    private TableColumn<Item, String> col03;
    @FXML
    private TableColumn<Item, String> col04;
    @FXML
    private TableColumn<Item, String> col05;
    @FXML
    private BorderPane root;

    private Stage manageStage;

    private CategoryDaoImpl categoryDao;

    private ObservableList<Category> categories;

    private ItemDaoImpl itemDao;

    private ObservableList<Item> items;

    private Item selectedItem;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    private Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);

    public ObservableList<Item> getItems() throws SQLException {
        if (items == null) {
            items = FXCollections.observableArrayList();
            items.addAll(getItemDao().getAllData());
        }
        return items;
    }

    public ItemDaoImpl getItemDao() {
        if (itemDao == null) {
            itemDao = new ItemDaoImpl();
        }
        return itemDao;
    }

    public ObservableList<Category> getCategories() throws SQLException {
        if (categories == null) {
            categories = FXCollections.observableArrayList();
            categories.addAll(getCategoryDao().getAllData());
        }
        return categories;
    }

    public CategoryDaoImpl getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }
        return categoryDao;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert.setTitle("Error");
        try {
            comboCategory.setItems(getCategories());
            itemTable.setItems(getItems());

            col01.setCellValueFactory((data) -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.getId()));
            });

            col02.setCellValueFactory((data) -> {
                Item item = data.getValue();
                return new SimpleStringProperty(item.getName());
            });

            col03.setCellValueFactory((data) -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.getPrice()));
            });

            col04.setCellValueFactory((data) -> {
                Item item = data.getValue();
                return new SimpleStringProperty(item.getCategory().getName());
            });

            col05.setCellValueFactory((data) -> {
                Item item = data.getValue();
                String recommended = "false";
                if (item.isRecommended()) {
                    recommended = "true";
                }
                return new SimpleStringProperty(recommended);
            });

        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveAction(ActionEvent event) throws SQLException {
        boolean duplicate = false;

        if (txtId.getText().trim().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().trim().isEmpty()
                || comboCategory.getValue() == null) {
            alert.setContentText("Please fill id/ name/ price/ category");
            alert.showAndWait();
        } else {
            for (int i = 0; i < getItems().size(); i++) {
                if (getItems().get(i).getId() == Integer.parseInt(txtId.getText())
                        || getItems().get(i).getName().equals(txtName.getText())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                Item item = new Item();
                item.setId(Integer.parseInt(txtId.getText()));
                item.setName(txtName.getText());
                item.setPrice(Double.valueOf(txtPrice.getText()));
                item.setDescription(txtDesc.getText());
                item.setRecommended(recommendedCheckBox.isSelected());
                item.setCategory(comboCategory.getValue());

                try {
                    getItemDao().addData(item);
                    getItems().clear();
                    getItems().addAll(getItemDao().getAllData());
                } catch (SQLException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                itemTable.refresh();
            } else {
                alert.setContentText("Duplicate item id/ name");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void resetAction(ActionEvent event) throws SQLException {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtDesc.setText("");
        recommendedCheckBox.setSelected(false);
    }

    @FXML
    private void updateAction(ActionEvent event) {
        Item item = new Item();
        item.setId(Integer.parseInt(txtId.getText()));
        item.setName(txtName.getText());
        item.setPrice(Double.valueOf(txtPrice.getText()));
        item.setDescription(txtDesc.getText());
        item.setRecommended(recommendedCheckBox.isSelected());
        item.setCategory(comboCategory.getValue());

        try {
            getItemDao().updateData(item);
            getItems().clear();
            getItems().addAll(getItemDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemTable.refresh();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        selectedItem = itemTable.getSelectionModel().getSelectedItem();

        try {
            getItemDao().deleteData(selectedItem);
            getItems().clear();
            getItems().addAll(getItemDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void tableClick(MouseEvent event) {
        selectedItem = itemTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtId.setText(String.valueOf(selectedItem.getId()));
            txtName.setText(selectedItem.getName());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            txtDesc.setText(selectedItem.getDescription());
            recommendedCheckBox.setSelected(selectedItem.isRecommended());
            comboCategory.setValue(selectedItem.getCategory());
        }
    }

    @FXML
    private void showCategoryManagement(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CategoryForm.fxml"));
            BorderPane pane = loader.load();
            CategoryFormController controller = loader.getController();
            controller.setMainController(this);
            Scene scene = new Scene(pane);
            manageStage = new Stage();
            manageStage.setScene(scene);
            manageStage.setTitle("Category Management");
            manageStage.initModality(Modality.APPLICATION_MODAL);
            manageStage.initOwner(root.getScene().getWindow());
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        manageStage.show();
    }

    @FXML
    private void closeAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void aboutAction(ActionEvent event) {
        aboutAlert.setTitle("About");
        aboutAlert.setContentText("1772004 - Jonathan Bernad");
        aboutAlert.showAndWait();
    }
}
