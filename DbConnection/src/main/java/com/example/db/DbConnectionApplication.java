package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbConnectionApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DbConnectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Connection conn;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://192.168.168.12:1433;databaseName=New_joinee_2022;encrypt=true;trustServerCertificate=true;",
					"sa1", "P@ssw0rd");
			
			Statement st = conn.createStatement();
			String query ="Select * from tutorials";
		     rs = st.executeQuery(query);
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getBoolean(4));
			}
			conn.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	
}
