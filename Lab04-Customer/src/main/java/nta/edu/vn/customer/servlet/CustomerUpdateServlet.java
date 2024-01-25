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

@WebServlet("/customerUpdate")
public class CustomerUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form chỉnh sửa
    	request.setCharacterEncoding("UTF-8");
        int cusID = Integer.parseInt(request.getParameter("cusID"));
        String cusUser = request.getParameter("cusUser");
        String cusName = request.getParameter("cusName");
        String cusEmail = request.getParameter("cusEmail");
        String cusPhone = request.getParameter("cusPhone");
        String cusPass = request.getParameter("cusPass");

        // Lấy thông tin khách hàng hiện tại để giữ giá trị cũ của CusPass
        Customer existingCustomer;
        try {
            existingCustomer = CustomerDAO.getCustomerByID(cusID);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Xử lý lỗi (ở đây bạn có thể chọn cách xử lý tùy thuộc vào yêu cầu cụ thể của ứng dụng)
            request.setAttribute("errorString", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customerEdit.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Tạo đối tượng Customer với dữ liệu mới
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCusID(cusID);
        updatedCustomer.setCusUser(cusUser);
        updatedCustomer.setCusName(cusName);
        updatedCustomer.setCusEmail(cusEmail);
        updatedCustomer.setCusPhone(cusPhone);

        // Kiểm tra xem trường CusPass có giá trị hay không trước khi cập nhật
        if (cusPass != null && !cusPass.isEmpty()) {
            updatedCustomer.setCusPass(cusPass);
        } else {
            // Giữ nguyên giá trị cũ của CusPass
            updatedCustomer.setCusPass(existingCustomer.getCusPass());
        }

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
