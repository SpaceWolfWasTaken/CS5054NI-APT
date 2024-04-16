package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.PasswordEncryptionWithAes;
import model.StudentModel;
import utils.StringUtils;
/**
 * This is a controller that manages database communication.
 * 
 * @author Sujal Khatiwada (np01cp4a220106@islingtoncollege.edu.np)
 */
public class DatabaseController {
	public Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/college_app";
		String user = "root";
		String pass = "";
		return DriverManager.getConnection(url, user, pass);
	}
	
	public int addStudent(StudentModel studentModel) {
		try(Connection con = getConnection()){
			
			PreparedStatement checkUsernameSt = con.prepareStatement(StringUtils.GET_USERNAME);
			checkUsernameSt.setString(1, studentModel.getUsername());
			ResultSet checkUsernameRs = checkUsernameSt.executeQuery();
			checkUsernameRs.next();
			if (checkUsernameRs.getInt(1) > 0) {
				return -2; //Username already exists.
			}
			
			PreparedStatement checkPhoneSt = con.prepareStatement(StringUtils.GET_PHONE);
			checkPhoneSt.setString(1, studentModel.getPhoneNumber());
			ResultSet checkPhoneRs = checkPhoneSt.executeQuery();
			checkPhoneRs.next();
			if (checkPhoneRs.getInt(1) > 0) {
				return -4; //Phone already exists.
			}
			
			PreparedStatement checkEmailSt = con.prepareStatement(StringUtils.GET_EMAIL);
			checkEmailSt.setString(1, studentModel.getEmail());
			ResultSet checkEmailRs = checkEmailSt.executeQuery();
			checkEmailRs.next();
			if (checkEmailRs.getInt(1) > 0) {
				return -3; //Email already exists.
			}
			
			
			
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_STUDENT);
			
			st.setString(1, studentModel.getUsername());
			st.setString(2, studentModel.getFirstName());
			st.setString(3, studentModel.getLastName());
			st.setDate(4, Date.valueOf(studentModel.getDob()));
			st.setString(5, studentModel.getGender());
			st.setString(6, studentModel.getEmail());
			st.setString(7, studentModel.getPhoneNumber());
			st.setString(8, studentModel.getSubject());
			st.setString(9, PasswordEncryptionWithAes.encrypt(studentModel.getUsername(), studentModel.getPassword()));
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
			
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	public int getStudentLoginInfo(String username, String password) {
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.GET_LOGIN_STUDENT_INFO);
			
			st.setString(1, username);
			//st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				//User name and password match in the database
				String userDb = rs.getString("user_name");
				String passwordDb = rs.getString("password");
				String decryptedPwd = PasswordEncryptionWithAes.decrypt(passwordDb, username);
				
				if (decryptedPwd != null && userDb.equals(username) && decryptedPwd.equals(password)) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	public ArrayList<StudentModel> getAllStudentsInfo(){
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.GET_ALL_STUDENT_INFO);
			ResultSet result = stmt.executeQuery();
			
			ArrayList<StudentModel> students = new ArrayList<StudentModel>();
			
			while(result.next()) {
				StudentModel student = new StudentModel();
				student.setFirstName(result.getString("first_name"));
				student.setLastName(result.getString("last_name"));
				student.setDob(result.getDate("dob").toLocalDate());
				student.setEmail(result.getString("email"));
				student.setGender(result.getString("gender"));
				student.setPhoneNumber(result.getString("phone_number"));
				student.setSubject(result.getString("subject"));
				
				students.add(student);
			}
			return students;
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
