package com.nagarro.assignment.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.Session;

import com.nagarro.assignment.constants.Constants;
import com.nagarro.assignment.dao.pojo.Image;
import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.services.implementation.BaseServicesImplementation;
import com.nagarro.assignment.util.hibernateutil;

/**
 * Servlet implementation class EditBook
 */
@WebServlet("/EditBook")
@MultipartConfig
public class EditBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(AddingNewBookController.class.getCanonicalName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditBookController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter(Constants.SUBMIT));
		System.out.println("image id is"+id);
		String name = request.getParameter(Constants.NAME2);
		Part filePart = request.getPart(Constants.FILE);
		String fileUrl = getFileName(filePart).replace(Constants.STRING2, Constants.STRING);
		String[] arr = fileUrl.split(Constants.STRING);
		String savePath = Constants.SAVE_DIR + name;
		File fileSaveDir = new File(savePath);
		filePart.write(savePath + File.separator);
		editBook(request, response, session, id, name, filePart, savePath);

	}

	private void editBook(HttpServletRequest request, HttpServletResponse response, HttpSession session, int id,
			String name, Part filePart, String savePath) {
		try (Session s = hibernateutil.getSession()) {
			s.beginTransaction();
			Image image = s.find(Image.class, id);
			image.setName(name);
			image.setPreview(savePath);
			image.setSize(filePart.getSize());
			s.save(image);
			s.getTransaction().commit();
			User user = (User) session.getAttribute(Constants.USER_OBJECT);
			BaseServicesImplementation loginService = new BaseServicesImplementation();
			User flag = loginService.login(user.getUserName(), user.getUserPassword());
			request.setAttribute(Constants.IMAGES_LIST, flag.getImage());
			session.setAttribute(Constants.USER_OBJECT, flag);
			request.getRequestDispatcher(Constants.IMAGE_MANAGEMENT_UTILITY_JSP).include(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader(Constants.CONTENT_DISPOSITION);
		LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
		for (String content : part.getHeader(Constants.CONTENT_DISPOSITION).split(Constants.STRING4)) {
			if (content.trim().startsWith(Constants.FILENAME)) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
