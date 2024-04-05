package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
			LocalDate data = LocalDate.parse("22/04/1985", dtf);

			st = conn.prepareStatement("INSERT INTO seller" + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES" + "(?,?,?,?,?)");

			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");

			ZoneId zI = ZoneId.systemDefault();
			long longData = data.atStartOfDay(zI).toEpochSecond();

			st.setDate(3, new java.sql.Date(longData));
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
