package controller;

import dao.category.CategoryDAO;
import dao.product.ProductDAO;
import model.Category;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;

    public void init(){
        categoryDAO = new CategoryDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try{
            switch (action) {
                case "create":
                    showNewForm(request,response);
                    break;
                case "edit":
                    showEditForm(request,response);
                    break;
                case "delete":
                    deleteProduct(request,response);
                    break;
                default:
                    listProduct(request,response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try{
            switch (action) {
                case "create":
                    insertProduct(request,response);
                    break;
                case "edit":
                    updateProduct(request,response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Product> listProduct = productDAO.selectAll();
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,ServletException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        int idCategory = Integer.parseInt(request.getParameter("category"));
        Category category = categoryDAO.getById(idCategory);
        Product newProduct = new Product(name, price,quantity, color, category);
        productDAO.insert(newProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(request,response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        request.setAttribute("listProduct", productDAO.selectAll());
        request.setAttribute("listCategory", categoryDAO.selectAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(request,response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        int idCategory = Integer.parseInt(request.getParameter("category"));
        Category category = categoryDAO.getById(idCategory);

        Product updatedProduct = new Product(id, name, price, quantity, color, category);
        productDAO.update(updatedProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
        dispatcher.forward(request,response);
    }

    private void showEditForm (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
        request.setAttribute("product", product);
        request.setAttribute("listProduct", productDAO.selectAll());
        request.setAttribute("listCategory", categoryDAO.selectAll());
        dispatcher.forward(request,response);
    }

    private void deleteProduct (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.delete(id);
        List<Product> listProduct = productDAO.selectAll();
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request,response);
    }
}
