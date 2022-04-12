package dao.category;

import config.SingletonConnection;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO {

    public CategoryDAO() {
    }

    @Override
    public List<Category> selectAll() {
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = SingletonConnection.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category;")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                categoryList.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    @Override
    public void insert(Category category) {
        try (Connection connection = SingletonConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into category(name) values (?)")) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getById(int id) {
        Category category = null;
        try (Connection connection = SingletonConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from category where id = ?;")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                category = new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Category category) throws SQLException {
        return false;
    }
}
