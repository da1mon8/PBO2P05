package com.jonathan.dao;

import com.jonathan.entity.Category;
import com.jonathan.entity.Item;
import com.jonathan.util.ConnUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 1772004 Jonathan Bernad
 */
public class ItemDaoImpl implements DaoService<Item> {

    @Override
    public int addData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "INSERT INTO menu(menu_id, menu_name, price, description, recommended, category_id) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(object.getId()));
        ps.setString(2, object.getName());
        ps.setString(3, String.valueOf(object.getPrice()));
        ps.setString(4, object.getDescription());

        String recommended = "0";
        if (object.isRecommended()) {
            recommended = "1";
        }

        ps.setString(5, recommended);
        ps.setString(6, String.valueOf(object.getCategory().getId()));
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int updateData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "UPDATE menu SET menu_name = ?, price = ?, description = ?, recommended = ?, category_id = ? WHERE menu_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, object.getName());
        ps.setString(2, String.valueOf(object.getPrice()));
        ps.setString(3, object.getDescription());

        String recommended = "0";
        if (object.isRecommended()) {
            recommended = "1";
        }

        ps.setString(4, recommended);
        ps.setString(5, String.valueOf(object.getCategory().getId()));
        ps.setString(6, String.valueOf(object.getId()));
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int deleteData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query = "DELETE FROM menu WHERE menu_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(object.getId()));

        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public List<Item> getAllData() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM menu m JOIN category c ON m.category_id = c.id";
        Connection conn = ConnUtil.createConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("menu_id"));
            item.setName(rs.getString("menu_name"));
            item.setPrice(rs.getDouble("price"));

            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));

            item.setCategory(category);
            item.setRecommended(rs.getBoolean("recommended"));
            item.setDescription(rs.getString("description"));

            items.add(item);
        }
        rs.close();
        ps.close();
        return items;
    }
}
