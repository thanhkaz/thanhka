package devmaster.edu.vn.servlet;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID =1L;
	public HomeServlet() {
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
		dispatcher.forward(request, response);
	
	}
	protected void doPost(HttpServletRequest request ,HttpServletResponse response)throws ServletException , IOException{
		doGet(request,response);
	}
	
}
