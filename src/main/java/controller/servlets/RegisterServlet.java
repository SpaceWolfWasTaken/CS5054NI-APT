package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DatabaseController;
import model.StudentModel;
import utils.StringUtils;

/**
 * Servlet implementation class MyFirstServlet
 */
@WebServlet(asyncSupported = true, description = "my first servlet", urlPatterns = { "/RegisterServlet" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DatabaseController dbController = new DatabaseController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter printOut = response.getWriter();
		response.setContentType("text/html");
		response.sendRedirect(request.getContextPath() + StringUtils.REGISTER_PAGE);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter(StringUtils.USER_NAME);
		String firstName = request.getParameter(StringUtils.FIRST_NAME);
		String lastName = request.getParameter(StringUtils.LAST_NAME);
		String dobString = request.getParameter(StringUtils.BIRTHDAY);
		LocalDate dob = LocalDate.parse(dobString);
		String gender = request.getParameter(StringUtils.GENDER);
		String email = request.getParameter(StringUtils.EMAIL);
		String phoneNumber = request.getParameter(StringUtils.PHONE_NUMBER);
		String subject = request.getParameter(StringUtils.SUBJECT);
		String password = request.getParameter(StringUtils.PASSWORD);
		String retypePassword = request.getParameter(StringUtils.RETYPE_PASSWORD);
		
		System.out.println(gender);
		if (this.hasOnlyAlphabets(userName)) {
			System.out.println("Only has alphabets");
		} else {
			System.out.println("Has something more");
		}
		
		StudentModel studentModel = new StudentModel(firstName, lastName, dob,
				gender, email, phoneNumber, subject, userName, password);
		
		int result = dbController.addStudent(studentModel);
		
		
		if(this.passwordMatch(password, retypePassword)) {
			switch(result) {
			case 1 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SUCCESS_REGISTER_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.LOGIN_PAGE);
				//no need break?
				break;
			}
			case 0 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_REGISTER_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
				break;
			}
			case -1 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
				break;
			}
			case -2 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.USERNAME_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
				break;
			}
			case -3 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.EMAIL_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
				break;
			}
			case -4 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
				break;
			}
			default -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
				break;
			}
			}
		} else {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PASSWORD_UNMATCHED_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
		}
		/*
		if(result ==1) {
			//if succesfully added to db
			String successRegisterMessage = "Successfully Registered!";
			request.setAttribute("errorMessage", successRegisterMessage);
			response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
		} else if (result ==0) {
			//Redirect to the same register page with form data mistake
			String errorMessage = "Please correct the form data.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
		} else {
			//Redirect to the same register page with server error
			String errorMessage = "An unexpected server error occurred.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
					
		}
		*/
		
		
	}
	
	private boolean hasOnlyAlphabets(String data) {
		Pattern pattern = Pattern.compile("[^a-zA-z]");
		//If finds only alphabets, find() returns false, which we inverse
		return !pattern.matcher(data).find();
	}
	
	private boolean hasProperUsername(String username) {
		if (username.length() < 6){return false;}
		Pattern pattern = Pattern.compile("[^a-zA-z0-9]");
		//If finds only alphanumbers, find() returns false, which we inverse
		return !pattern.matcher(username).find();
	}
	
	private boolean hasSecurePassword(String password) {
		if (password.length() < 6){return false;}
		Pattern pattern = Pattern.compile("[^a-zA-z0-9]");
		//If finds only alphanumerics, find() returns false
		//we need to find more than alphanumerics
		return pattern.matcher(password).find();
	}
	
	private boolean passwordMatch(String password, String repassword) {
		if (password.equals(repassword)){
			return true;
		} else {
			return false;
		}
	}

}
