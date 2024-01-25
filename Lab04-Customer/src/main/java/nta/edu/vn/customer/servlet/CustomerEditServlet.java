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

@WebServlet("/customerEdit")
public class CustomerEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cusID = Integer.parseInt(request.getParameter("cusID"));

        try {
            Customer customer = CustomerDAO.getCustomerByID(cusID);
            request.setAttribute("customer", customer);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Xử lý lỗi và chuyển hướng lại trang danh sách khách hàng với thông báo lỗi
            request.setAttribute("errorString", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerList.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Chuyển hướng đến trang chỉnh sửa thông tin khách hàng
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerEdit.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form chỉnh sửa
    	request.setCharacterEncoding("UTF-8");
        int cusID = Integer.parseInt(request.getParameter("cusID"));
        String cusUser = request.getParameter("cusUser");
        String cusPass = request.getParameter("cusPass");
        String cusName = request.getParameter("cusName");
        String cusPhone = request.getParameter("cusPhone");
        String cusAdd = request.getParameter("cusAdd");
        String cusEmail = request.getParameter("cusEmail");
        String cusFacebook = request.getParameter("cusFacebook");
        String cusSkyper = request.getParameter("cusSkyper");
        int cusStatus = Integer.parseInt(request.getParameter("cusStatus"));

        // Tạo đối tượng Customer với dữ liệu mới
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCusID(cusID);
        updatedCustomer.setCusUser(cusUser);
        updatedCustomer.setCusPass(cusPass);
        updatedCustomer.setCusName(cusName);
        updatedCustomer.setCusPhone(cusPhone);
        updatedCustomer.setCusAdd(cusAdd);
        updatedCustomer.setCusEmail(cusEmail);
        updatedCustomer.setCusFacebook(cusFacebook);
        updatedCustomer.setCusSkyper(cusSkyper);
        updatedCustomer.setCusStatus(cusStatus);

        try {
            // Gọi DAO để cập nhật thông tin khách hàng
            CustomerDAO.updateCustomer(updatedCustomer);

            // Chuyển hướng đến trang danh sách khách hàng sau khi cập nhật thành công
            response.sendRedirect(request.getContextPath() + "/customerList");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Xử lý lỗi và chuyển hướng lại trang chỉnh sửa với thông báo lỗi
            request.setAttribute("errorString", e.getMessage());
            request.setAttribute("customer", updatedCustomer);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerEdit.jsp");
            dispatcher.forward(request, response);
        }
    }
}
