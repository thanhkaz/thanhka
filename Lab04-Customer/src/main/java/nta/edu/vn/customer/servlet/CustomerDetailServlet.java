package nta.edu.vn.customer.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import nta.edu.vn.customer.Customer;
import nta.edu.vn.customer.list.CustomerDAO;

@WebServlet("/customerDetail")
public class CustomerDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cusID = Integer.parseInt(request.getParameter("cusID"));

        try {
            Customer customer = CustomerDAO.getCustomerByID(cusID);
            request.setAttribute("customer", customer);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi và chuyển hướng lại trang danh sách khách hàng với thông báo lỗi
            request.setAttribute("errorString", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerList.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Chuyển hướng đến trang hiển thị thông tin chi tiết của khách hàng
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerDetail.jsp");
        dispatcher.forward(request, response);
    }
}
