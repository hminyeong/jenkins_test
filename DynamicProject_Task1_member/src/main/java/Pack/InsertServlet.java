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

public class InsertServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 파라미터로 전송된 값을 얻어오기.
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");

		int n=0;
		PreparedStatement pstmt = null;
		Connection con = null;

		try{
//			// 2. 전송된 값을 db에 저장.
//			Class.forName("com.mysql.cj.jdbc.Driver"); // Mysql Driver 로드
//			String url="jdbc:mysql://43.201.67.143:3306/memberdb";
//			String user="root";
//			String password="1234";
//			
//			// mysql과 connection
//			con = DriverManager.getConnection(url, user, password); 
			
			con = DB_connector.connect();
			// sql 구문 작성
			String sql = "INSERT INTO members (id) VALUES (?)"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			//sql구문 실행하기
			n = pstmt.executeUpdate();
			
		}catch(SQLException se){
			System.out.println("Error 2");
			System.out.println(se.getMessage());
		}finally{
			System.out.println("Error 3");
			try{if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(SQLException se){
				System.out.println(se.getMessage());
			}
		}

		// 3. 사용자(클라이언트)에 결과를 응답하기.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head></head>");
		pw.println("<body>");
		
		if(n>0){
			pw.println( id + "님! 성공적으로 가입되었습니다.<br/>");
		}else{
			pw.println("오류로 인해 가입에 실패했습니다.<br/>");
			pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
		}
		pw.println("</body>");
		pw.println("</html>");
	}
}


