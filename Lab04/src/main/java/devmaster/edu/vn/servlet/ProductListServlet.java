package devmaster.edu.vn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import devmaster.edu.vn.beans.Product;
import devmaster.edu.vn.conn.ConnectSQL;

import devmaster.edu.vn.util.ProductUtils;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errString = null;
        List<Product> list = null;

        try (Connection conn = ConnectSQL.getMSSQLConnection()) {
            // Retrieve product list
            list = ProductUtils.queryProduct(conn);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            errString = e.getMessage();
        }

        // Set attributes and forward to the appropriate view
        request.setAttribute("errorString", errString);
        request.setAttribute("productList", list);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productList.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
