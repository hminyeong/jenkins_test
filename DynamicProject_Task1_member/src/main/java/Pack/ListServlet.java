package Pack;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB_connector;


//@WebServlet("/list.do")
public class ListServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest resquest, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter pw = response.getWriter();
		
		pw.println("<html>");
		pw.println("<head></head>");
		pw.println("<body>");
		
		try { // jdbc connect j 라이브러리 로딩 예외 처리
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		
		try{ // drive 클래스를 이용해 커넥션 객체에 localhost:3306/DB 와 연동 및 예외처리
//			String url="jdbc:mysql://43.201.67.143:3306/memberdb";
//			String user="root";
//			String password="1234";
//			
//			// DB 로그인 정보로 연동
//			con = DriverManager.getConnection(url, user, password);
			
			con = DB_connector.connect();
			String sql = "select * from members";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			pw.println("<div>");
			pw.println("<table border='1' width='300'>");
			pw.println("<tr>");
			pw.println("<td>아이디</td>");
			pw.println("<td>삭제</td>");
			pw.println("<td>수정</td>");
			pw.println("</tr>");

			// 결과 출력
			while(rs.next()){
				String id = rs.getString("id");

				pw.println("<tr>");
				pw.println("<td>" + id + "</td>");
				pw.println("<td><a href='delete.do?id=" + id + "'>삭제</a></td>");
				pw.println("<td><a href='update.do?id=" + id + "'>수정</a></td>");
				pw.println("</tr>");
			}

			pw.println("</table>");
			pw.println("</div>");
			pw.println("<a href='main.html'>메인페이지로 이동</a>");

		}catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
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
	}
}


