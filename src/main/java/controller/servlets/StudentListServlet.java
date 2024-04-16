package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentModel;
import utils.StringUtils;
import controller.DatabaseController;

/**
 * Servlet implementation class StudentListServlet
 */
@WebServlet(asyncSupported = true, urlPatterns ="/StudentListServlet")
public class StudentListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    private DatabaseController databaseController = new DatabaseController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Getting all students");
		List<StudentModel> studentsList = databaseController.getAllStudentsInfo();
		request.setAttribute("studentList",studentsList);
		
		request.getRequestDispatcher(StringUtils.INDEX_PAGE).forward(request, response);
	} 	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
