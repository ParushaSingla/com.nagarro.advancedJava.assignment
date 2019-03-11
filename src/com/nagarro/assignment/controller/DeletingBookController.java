package com.nagarro.assignment.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.nagarro.assignment.constants.Constants;
import com.nagarro.assignment.dao.pojo.Image;
import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.services.BaseServicesImplementation;
import com.nagarro.assignment.util.hibernateutil;

/**
 * Servlet implementation class DeleteAndEditBook
 */
@WebServlet("/DeleteAndEditBook")
public class DeletingBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeletingBookController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (deleteBook(Integer.parseInt(request.getParameter("imageId")), request)) {
			User user = (User) session.getAttribute(Constants.USER_OBJECT);
			BaseServicesImplementation loginService = new BaseServicesImplementation();
			User flag = loginService.login(user.getUserName(), user.getUserPassword());
			request.setAttribute(Constants.IMAGES_LIST, flag.getImage());
			session.setAttribute(Constants.USER_OBJECT, flag);
			request.getRequestDispatcher(Constants.IMAGE_MANAGEMENT_UTILITY_JSP).include(request, response);

		} else

		{
			User user = (User) request.getSession().getAttribute("UserObject");
			request.setAttribute(Constants.IMAGES_LIST, user.getImage());
			request.getRequestDispatcher(Constants.IMAGE_MANAGEMENT_UTILITY_JSP).forward(request, response);

		}
	}

	public boolean deleteBook(int id, HttpServletRequest request) {

		try (Session session = hibernateutil.getSession()) {

			Image image = session.find(Image.class, id);

			session.beginTransaction();

			session.delete(image);

			session.getTransaction().commit();

		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
