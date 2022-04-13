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
    public static final String UPDATE_PRODUCT_SQL = "update product set name = ?, price = ?, quantity = ?, color = ?, id_category = ? where id = ?;";
    public static final String DELETE_PRODUCT_BY_ID_SQL = "delete from product where id = ?;";
    public static final String SELECT_PRODUCT_BY_ID_SQL = "select id, name, price, quantity, color, id_category from product where id = ?;";
    public static final String INSERT_PRODUCT_SQL = "insert into product(name, price, quantity, color, id_category) values (?,?,?,?,?)";
    public static final String SELECT_ALL_PRODUCT_SQL = "select * from product;";

    public ProductDAO() {
    }

    @Override
    public List<Product> selectAll() {
        List<Product> productList = new ArrayList<>();
        try(Connection connection = SingletonConnection.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT_SQL);){
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
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getById(int id) {
        Product product = null;
        try(Connection connection = SingletonConnection.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID_SQL)){
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = SingletonConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
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
