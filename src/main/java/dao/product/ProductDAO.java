package dao.product;

import config.SingletonConnection;
import dao.category.CategoryDAO;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {
    public ProductDAO() {
    }

    @Override
    public List<Product> selectAll() {
        List<Product> productList = new ArrayList<>();
        try(Connection connection = SingletonConnection.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product;");){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                int idcategory = resultSet.getInt("id_category");
                Category category = new CategoryDAO().getById(idcategory);
                productList.add(new Product(id, name, price, quantity, color, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public void insert(Product product) {
        try (Connection connection = SingletonConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into category(name, price, quantity, color, id_category) values (?,?,?,?,?)")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setInt(5, product.getCategory().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getById(int id) {
        Product product = null;
        try(Connection connection = SingletonConnection.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement("select product.id, product.name, price, quantity, color, id_category, c.name from\n" +
                    "product join category c on product.id_category = c.id where product.id = ?;")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("product.name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                int idCategory = resultSet.getInt("id_category");
                Category category = new CategoryDAO().getById(idCategory);

                product = new Product(id, name, price, quantity, color, category);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = SingletonConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from product where id = ?;")){
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = SingletonConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement("update product set name = ?, price = ?, quantity = ?, color = ?, id_category = ? where id = ?;")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.setInt(6, product.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
