package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.Date;

import db.DB;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement st = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			conn = DB.getConnection();

			st = conn.prepareStatement("INSERT INTO seller " 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?)");

			System.out.print("Quantos vendedores quer adicionar? ");
			int op = sc.nextInt();
			
			for (int i = 0; i < op; i++) {
				sc.nextLine();
				System.out.print("Digite o nome: ");
				st.setString(1, sc.nextLine());
				System.out.print("Digite o email: ");
				st.setString(2, sc.nextLine());
				System.out.print("Digite o aniversário: ");

				st.setDate(3, new Date(sdf.parse(sc.nextLine()).getTime()));
				
				System.out.print("Digite o salário base: ");
				st.setDouble(4, sc.nextDouble());
				System.out.print("Digite o ID do Departamento: ");
				st.setDouble(5, sc.nextInt());
			}
			
			int rowsAffected = st.executeUpdate();

			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			e.printStackTrace();
		}catch(ParseException e) {
			e.getMessage();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
			sc.close();
		}

	}

}
