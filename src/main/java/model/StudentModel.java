package model;

import java.time.LocalDate;
import java.io.Serializable;
/**
 * This is a student model that represents a student object.
 * 
 * @author Sujal Khatiwada (np01cp4a220106@islingtoncollege.edu.np)
 */
public class StudentModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName, lastName;
	private LocalDate dob;
	private String gender, email, phoneNumber, subject, username, password;
	
	public StudentModel() {
		
	}
	
	public StudentModel(String firstName, String lastName, LocalDate dob, String gender, String email,
			String phoneNumber, String subject, String username, String password) {
		super();
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDob(dob);
		this.setGender(gender);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setSubject(subject);
		this.setUsername(username);
		this.setPassword(password);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
