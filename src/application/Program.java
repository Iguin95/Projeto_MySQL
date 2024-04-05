package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import db.DB;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement st = null;
		//ResultSet rs = null;

		try {
			conn = DB.getConnection();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate data = LocalDate.parse("02/10/1999", dtf);

			st = conn.prepareStatement("INSERT INTO seller" 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES" + "(?,?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, "Lindomar Rose");
			st.setString(2, "rose@gmail.com");

			ZoneId zI = ZoneId.systemDefault();
			long longData = data.atStartOfDay(zI).toEpochSecond();

			st.setDate(3, new java.sql.Date(longData));
			st.setDouble(4, 5000.0);
			st.setInt(5, 2);
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			}
			
			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}

}
