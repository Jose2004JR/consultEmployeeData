package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Scanner;

import db.DB;

public class App {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement consult = null;
		Scanner sc = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate now = LocalDate.now();
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter
				("C:\\Users\\bduhe\\Desktop\\employeeDB.txt"))) {
			
			sc = new Scanner(System.in);
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO employee"
					+"(name,yearsOld,salary,companyTime)"
					+ "VALUES"
					+ "(?,?,?,?)"
					);
			System.out.print("What's employees to add in database? ");
			int n =sc.nextInt();

			for(int i = 1;i <= n;i++ ) {
				System.out.println("EMPLOYEE #"+i);
				sc.nextLine();
				System.out.print("Name: ");
				st.setString(1, sc.nextLine());
				System.out.print("Years Old: ");
				st.setInt(2, sc.nextInt());
				System.out.print("Salary: ");
				st.setDouble(3, sc.nextDouble());
				sc.nextLine();
				System.out.print("Company Time: ");
				st.setObject(4, LocalDate.parse(sc.nextLine(),dtf));
				
			}
			consult = conn.prepareStatement("SELECT * FROM employee");
			ResultSet rs = consult.executeQuery();
			int Rs = st.executeUpdate();
			while(rs.next()) {
				bw.write(rs.getString("name") + " ,");
				bw.write(rs.getInt("yearsOld") + " ,");
				bw.write("R$ " + rs.getDouble("salary") + " ,");
				LocalDate timeCompany  = rs.getDate("companyTime").toLocalDate();
				Long diff = ChronoUnit.MONTHS.between(timeCompany,now);
				bw.write(diff +  " meses");
				bw.newLine();
			}
			System.out.println("SUCESS");
		}catch(SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}catch(IOException e) {
			System.out.println("ERROR :" + e.getMessage());
		}
	}

}
