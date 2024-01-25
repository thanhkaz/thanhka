package nta.edu.vn.customer.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import nta.edu.vn.customer.Customer;
import nta.edu.vn.customer.list.CustomerDAO;

@WebServlet("/customerList")
public class CustomerListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        String errorString = null;
        List<Customer> customers = null;

        try {
            customers = CustomerDAO.getAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            errorString = e.getMessage();
        }

        
        request.setAttribute("errorString", errorString);
        request.setAttribute("customerList", customers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerList.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        doGet(request, response);
    }
}

