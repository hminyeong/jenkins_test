package Pack;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB_connector;


public class UpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. parameter로 전송된 id얻기.
		String id=req.getParameter("id");
		String new_id = "";

		// 2. id에 해당하는 정보를 db에서 조회해서 출력.
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println("<html>");
		pw.println("<head></head>");
		pw.println("<body>");

		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs=null;
		try{
			// 2. 전송된 값을 db에 저장.
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url="jdbc:mysql://43.201.67.143:3306/memberdb";
//			String user="root";
//			String password="1234";
//			
//			con = DriverManager.getConnection(url, user, password);
			
			con = DB_connector.connect();
			
			String sql = "SELECT * FROM members WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			//sql구문 실행하기
			rs = pstmt.executeQuery();
			rs.next();

			pw.println("<form method='post' action='updateok.do'>");
			pw.println("<input type='hidden' name='id' value='" + id + "'/>");
			pw.println("아이디<input type='text' name='new_id' value='" + new_id + "'/><br/>");
			pw.println("<input type='submit' value='저장'/><br/>");
			pw.println("</form>");
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}finally{
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(SQLException se){
				System.out.println(se.getMessage());
			}
		}

		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}

