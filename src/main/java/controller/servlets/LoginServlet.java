package controller.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.DatabaseController;
import utils.StringUtils;

/**
 * This is a servlet that manages login features.
 * 
 * @author Sujal Khatiwada (np01cp4a220106@islingtoncollege.edu.np)
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseController dbController = new DatabaseController();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.sendRedirect(request.getContextPath() + StringUtils.LOGIN_PAGE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter(StringUtils.USER_NAME);
		String password = request.getParameter(StringUtils.PASSWORD);
		
		int loginResult = dbController.getStudentLoginInfo(userName, password);
		
		if (loginResult == 1) {
			//Successfully login
			
			HttpSession userSession = request.getSession();
			userSession.setAttribute("username", userName);
			userSession.setMaxInactiveInterval(30*30);
			userSession.setAttribute("creation", userSession.getCreationTime());
			
			Cookie userCookie= new Cookie("user", userName);
			userCookie.setMaxAge(30*60);
			response.addCookie(userCookie);
			
			Cookie helloCookie = new Cookie("hello", "Hellofromtheotherside");
			helloCookie.setMaxAge(60);
			response.addCookie(helloCookie);
			
			//String successRegisterMessage = "Successfully Registered!";
			//request.setAttribute("firstName", successRegisterMessage);
			request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_LOGIN_MESSAGE);
			response.sendRedirect(request.getContextPath() + StringUtils.WELCOME_PAGE);
		} else if (loginResult ==0) {
			//Redirect to the same register page with form data mistake
			String errorMessage = "Please correct the form data.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
		} else if (loginResult == -1) {
            // Username not found
            request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.MESSAGE_ERROR_CREATE_ACCOUNT);
            request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
		} else {
			//Redirect to the same register page with server error
			String errorMessage = "An unexpected server error occurred.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
					
		}
	}

}
