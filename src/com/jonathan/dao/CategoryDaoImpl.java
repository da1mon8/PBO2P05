package com.jonathan.dao;

import com.jonathan.entity.Category;
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
public class CategoryDaoImpl implements DaoService<Category> {

    @Override
    public int addData(Category object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "INSERT INTO category(id, name) VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(object.getId()));
        ps.setString(2, object.getName());

        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int updateData(Category object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteData(Category object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Category> getAllData() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        Connection conn = ConnUtil.createConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            categories.add(category);
        }
        rs.close();
        ps.close();
        return categories;
    }
}
