package nta.edu.vn.customer.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;

import nta.edu.vn.customer.Customer;
import nta.edu.vn.customer.list.CustomerDAO;

@WebServlet("/customerCreate")
public class CustomerCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerCreate.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        // Lấy dữ liệu từ form tạo mới khách hàng
        String cusUser = request.getParameter("cusUser");
        String cusPass = request.getParameter("cusPass");
        String cusName = request.getParameter("cusName");
        String cusPhone = request.getParameter("cusPhone");
        String cusAdd = request.getParameter("cusAdd");
        String cusEmail = request.getParameter("cusEmail");
        String cusFacebook = request.getParameter("cusFacebook");
        String cusSkyper = request.getParameter("cusSkyper");
        String cusStatusParam = request.getParameter("cusStatus");
        int cusStatus = cusStatusParam != null && !cusStatusParam.isEmpty() ? Integer.parseInt(cusStatusParam) : 0;


        // Tạo đối tượng Customer từ dữ liệu
        Customer newCustomer = new Customer();
        newCustomer.setCusUser(cusUser);
        newCustomer.setCusPass(cusPass);
        newCustomer.setCusName(cusName);
        newCustomer.setCusPhone(cusPhone);
        newCustomer.setCusAdd(cusAdd);
        newCustomer.setCusEmail(cusEmail);
        newCustomer.setCusFacebook(cusFacebook);
        newCustomer.setCusSkyper(cusSkyper);
        newCustomer.setCusStatus(cusStatus);

        try {
            // Gọi DAO để thêm mới khách hàng
            CustomerDAO.createCustomer(newCustomer);

            // Chuyển hướng đến trang danh sách khách hàng sau khi thêm thành công
            response.sendRedirect(request.getContextPath() + "/customerList");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Xử lý lỗi và chuyển hướng lại trang tạo mới khách hàng với thông báo lỗi
            request.setAttribute("errorString", e.getMessage());
            request.setAttribute("customer", newCustomer);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerCreate.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
