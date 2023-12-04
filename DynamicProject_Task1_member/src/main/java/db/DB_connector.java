package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_connector {
	
	private static Connection con = null;
	
	public static Connection connect() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/memberdb";
			String user="root";
			String password="1234";
			con = DriverManager.getConnection(url, user, password);
			
			if(con!= null) {
				System.out.println(con);
				System.out.println("DB 연결 성공!");
			}
		} catch(ClassNotFoundException ce){
			System.out.println(ce.getMessage());
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}
		
		return con;
	}
}
