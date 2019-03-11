package com.nagarro.assignment.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nagarro.assignment.constants.Constants;
import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.services.implementation.BaseServicesImplementation;

/**
 * Servlet implementation class LoginControler
 */
@WebServlet("/LoginControler")
public class LoginControler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginControler() {
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
		String userName = request.getParameter(Constants.UNAME);
		String password = request.getParameter(Constants.PASS);
		if (userName.isEmpty() || password.isEmpty()) {
			request.setAttribute(Constants.ENTER_DETAILS, Constants.ENTER_BOTH_USER_NAME_AND_PASSWORD_TRY_AGAIN);
			request.getRequestDispatcher(Constants.INDEX_JSP).include(request, response);
		} else
			aunthenticateUser(request, response, userName, password);
	}

	private void aunthenticateUser(HttpServletRequest request, HttpServletResponse response, String userName,
			String password) throws ServletException, IOException {
		{
			HttpSession session = request.getSession();
			String page = Constants.INDEX_JSP;
			if (userName.trim().length() >= 0 && userName != null && password.trim().length() >= 0
					&& password != null) {
				BaseServicesImplementation loginService = new BaseServicesImplementation();
				User flag = loginService.login(userName, password);
				if (flag != null) {
					session.setAttribute(Constants.USER_OBJECT, flag);
					request.setAttribute(Constants.USERNAME2, userName);
					request.setAttribute(Constants.IMAGES_LIST, flag.getImage());

					page = Constants.IMAGE_MANAGEMENT_UTILITY_JSP;
				} else {
					request.setAttribute(Constants.ENTER_DETAILS, Constants.WRONG_USERNAME_OR_PASSWORD_TRY_AGAIN);
				}
			}
			request.getRequestDispatcher(page).include(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
