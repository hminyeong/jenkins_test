package Pack;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB_connector;


public class UpdateOkServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String id=req.getParameter("id");
		String new_id=req.getParameter("new_id");

		PreparedStatement pstmt = null;
		Connection con = null;
		int n=0;
		
		try{
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url="jdbc:mysql://43.201.67.143:3306/memberdb";
//			String user="root";
//			String password="1234";
//			
//			con = DriverManager.getConnection(url, user, password);

			con = DB_connector.connect();
			
			String sql = "UPDATE members SET id=? WHERE id=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_id);
			pstmt.setString(2, id);
			
			n = pstmt.executeUpdate();

			if(n>0){
				resp.sendRedirect("list.do");
			}else{
				PrintWriter pw = resp.getWriter();
				pw.println("<html><head></head>");
				pw.println("<body>실패</body>");
				pw.println("</heal>");
				pw.close();
			}
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}
}

