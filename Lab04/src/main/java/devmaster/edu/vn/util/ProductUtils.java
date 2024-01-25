package devmaster.edu.vn.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import devmaster.edu.vn.beans.Product;

public class ProductUtils {
	//Doc danh sach san pham
	public static List<Product> queryProduct(Connection conn) throws SQLException{
		String sql = "Select a.Code, a.Name, a.Price from Product a";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Product> list =new ArrayList<Product>();
		while (rs.next()) {
			String code = rs.getString("Code");
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			list.add(product);
		}
		return list;
	}
	//Tim kiem theo ma san pham(code)
	public static Product findProduct(Connection conn , String code)throws SQLException{
		String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,  code);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()) {
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product(code, name , price);
			return product;
		}
		return null;
	}
	//them moi san pham
	public static void insertProduct(Connection conn, Product product) throws SQLException{
		String sql = "Insert into Product(Code , Name , Price) values (?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, product.getCode());
		pstm.setString(2, product.getName());
		pstm.setFloat(3, product.getPrice());
		pstm.executeUpdate();
		
	}
	//Cap nhat san pham
	public static void updateProduct(Connection conn, Product product)throws SQLException{
		String sql ="Update product set Name =? ,Price=? where Code=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		pstm.executeUpdate();
	}
	//Xoa mot san pham
	public static void deleteProduct(Connection conn, String code ) throws SQLException{
		String sql ="Delete From Pro duct where Code= ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);
		pstm.executeUpdate();
	}
}	
