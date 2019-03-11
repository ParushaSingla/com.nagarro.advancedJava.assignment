package com.nagarro.assignment.recover.password;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.util.hibernateutil;

/**
 * Servlet implementation class checkForRecoveringPassword
 */
@WebServlet("/checkForRecoveringPassword")
public class checkForRecoveringPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public checkForRecoveringPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String ans = request.getParameter("answer");

		User user = checkForValidation(ans);
		if (user != null) {
			request.getRequestDispatcher("forget.jsp").include(request, response);
			out.println("Your Password is = " + user.getUserPassword());
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Your Password is' <%= " + user.getUserPassword() + "%>);");
			out.println("</script>");
		} else {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Your Answer For Security Question is wrong Try Again!!');");
			out.println("</script>");
			request.getRequestDispatcher("forget.jsp").include(request, response);
		}
	}

	private User checkForValidation(String ans) {

		Session session = hibernateutil.getSession();
		if (session != null) {
			try {
				String sql = "from User where user_ans=:ans";
				Query<User> query = session.createQuery(sql, User.class);
				query.setParameter("ans", ans);
				User result = query.uniqueResult();
				if (result != null) {
					System.out.println("User: " + result.toString());
					return result;
				} else {
					System.out.println("Invalid");
					return null;
				}
			} catch (Exception exception) {
				System.out.println("Exception occred while reading user data: " + exception.getMessage());
				return null;
			}

		} else {
			System.out.println("DB server down.....");
		}
		return null;
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
