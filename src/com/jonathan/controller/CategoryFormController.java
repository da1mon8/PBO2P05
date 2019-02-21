package com.jonathan.controller;

import com.jonathan.entity.Category;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 1772004 Jonathan Bernad
 */
public class CategoryFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, String> col01;
    @FXML
    private TableColumn<Category, String> col02;

    private MainFormController mainController;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    public void setMainController(MainFormController mainController) throws SQLException {
        this.mainController = mainController;
        categoryTable.setItems(mainController.getCategories());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert.setTitle("Error");

        col01.setCellValueFactory((data) -> {
            Category category = data.getValue();
            return new SimpleStringProperty(String.valueOf(category.getId()));
        });

        col02.setCellValueFactory((data) -> {
            Category category = data.getValue();
            return new SimpleStringProperty(category.getName());
        });

        categoryTable.refresh();
    }

    @FXML
    private void saveAction(ActionEvent event) throws SQLException {
        boolean duplicate = false;

        if (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty()) {
            alert.setContentText("Please fill id/ name");
            alert.showAndWait();
        } else {
            for (int i = 0; i < mainController.getCategories().size(); i++) {
                if (mainController.getCategories().get(i).getName().equals(txtName.getText())
                        || mainController.getCategories().get(i).getId() == Integer.parseInt(txtId.getText())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                Category category = new Category();
                category.setId(Integer.parseInt(txtId.getText()));
                category.setName(txtName.getText());

                try {
                    mainController.getCategoryDao().addData(category);
                    mainController.getCategories().clear();
                    mainController.getCategories().addAll(mainController.getCategoryDao().getAllData());
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                categoryTable.refresh();
            } else {
                alert.setContentText("Duplicate category id / category name");
                alert.showAndWait();
            }
        }
    }
}
