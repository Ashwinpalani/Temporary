package com.example.db.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.example.db.dao.TutorialRepository;
import com.example.db.model.Tutorial;

@Controller
public class Database {
	@Autowired
	TutorialRepository repo;

	@Autowired
	private Environment env;
	
	Logger log = LogManager.getLogger("DbConnection");

	@GetMapping("/connect")
	public ResponseEntity<?> Connect() throws SQLException {
		Connection conn;
		ResultSet rs = null;
		List<String> t = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://192.168.168.12:1433;databaseName=New_joinee_2022;encrypt=true;trustServerCertificate=true;",
					"sa1", "P@ssw0rd");

			Statement st = conn.createStatement();
			String query = "Select * from tutorials";
			rs = st.executeQuery(query);

			while (rs.next()) {
				t.add(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getBoolean(4));
				System.out.println(t);
				
				log.info("List: "+ t);
				log.debug("List: "+ t);

			}
			conn.close();

		} catch (SQLException e) {
			log.trace("error"+ e);

			e.printStackTrace();
		}
		return new ResponseEntity<>(t, HttpStatus.OK);

	}

	@GetMapping("/get")
	public ResponseEntity<?> getAll() {
		try {
		List<Tutorial> tut = repo.findAll();
		return new ResponseEntity<>(tut, HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>("issue arised", HttpStatus.CONFLICT);

	}

	@GetMapping("/getid/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		Tutorial tut = null;
		try {
		tut = repo.findById(id);
		System.out.println(tut);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(tut, HttpStatus.OK);

	}

	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<?> deleteId(@PathVariable int id) {
		List<Tutorial> tut = null;
		try {
		repo.deleteById(id);
	    tut = repo.findAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(tut, HttpStatus.OK);

	}

	@PutMapping("/updatebyid/{id}")
	public ResponseEntity<?> updateByid(@PathVariable int id) { 
		int t = 0;
    try {
	    t = repo.updateById(id);
		System.out.println(t);
    }
    catch (Exception e) {
		e.printStackTrace();
	}
		return new ResponseEntity<>(t, HttpStatus.OK);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleted() {
		repo.deleteAll();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody Tutorial tutorial) {
		try {
			repo.insert(tutorial);
			System.out.println(tutorial);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return new ResponseEntity<>("Inserted Successful" + tutorial, HttpStatus.OK);

	}

	@GetMapping("/print")
	public String print() {
		try
		{
			System.out.println("Ashwin");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "Ashwin";
	}

	
}
