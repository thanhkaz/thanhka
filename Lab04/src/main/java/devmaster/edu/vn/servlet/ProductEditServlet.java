package devmaster.edu.vn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import devmaster.edu.vn.beans.Product;
import devmaster.edu.vn.conn.ConnectSQL;
import devmaster.edu.vn.conn.ConnectionUtils;
import devmaster.edu.vn.util.ProductUtils;
import devmaster.edu.vn.utils.ProductUtils;
import jakarta.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
@WebServlet("/productEdit")
public class ProductEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ProductEditServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    String errorString = null;
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productEdit.jsp");

	    // Lay du lieu ma san pham tu request
	    String code = (String) request.getParameter("code");
	    if (code == null || code.isEmpty()) {
	        errorString = "Bạn chưa chọn sản phẩm cần sửa.";
	        request.setAttribute("errorString", errorString);
	        dispatcher.forward(request, response);
	        return;
	    }

	    Connection conn = null;
	    Product product = null;

	    try {
	        conn = ConnectSQL.getMSSQLConnection();
	        product = ProductUtils.findProduct(conn, code);

	        if (product == null) {
	            errorString = "Không tìm thấy sản phẩm có mã " + code;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        errorString = e.getMessage();
	    } finally {
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Khi co loi
	    if (errorString != null || product == null) {
	        request.setAttribute("errorString", errorString);
	        dispatcher.forward(request, response);
	    } else {
	        request.setAttribute("product", product);
	        dispatcher.forward(request, response);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException , IOException{
		request.setCharacterEncoding("UTF-8");
		String errorString = null;
		//lay du lie tren form
		String code =(String) request.getParameter("code");
		String name = (String) request.getParameter("name");
		String priceStr = (String) request.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		}catch (Exception e) {
			errorString = e.getMessage();
			
		}
		Product product = new Product(code , name , price);
		if(errorString !=null) {
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
			dispatcher.forward(request, response);
		}
		Connection conn = null;
		try {
			conn =ConnectSQL.getMSSQLConnection();
			ProductUtils.updateProduct(conn, product);
			response.sendRedirect(request.getContextPath()+"/productList");
		
		}catch (Exception e) {
			e.printStackTrace();
			errorString = e.getMessage();
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
			dispatcher.forward(request, response);
		}
	}
}
