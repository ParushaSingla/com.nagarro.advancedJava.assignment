package com.nagarro.assignment.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.SendResult;

import org.apache.catalina.ha.backend.Sender;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;

import com.nagarro.assignment.constants.Constants;
import com.nagarro.assignment.dao.pojo.Image;
import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.util.hibernateutil;

/**
 * Servlet implementation class Submitting
 */

@WebServlet("/Submitting")
@MultipartConfig(maxFileSize = 1024000)
public class AddingNewBookController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(AddingNewBookController.class.getCanonicalName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddingNewBookController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(Constants.USER_OBJECT);
		Part filePart = request.getPart(Constants.FILE);
		try {
			if (filePart.getSize() <= 1024000) {
				String fileUrl = getFileName(filePart).replace(Constants.STRING2, Constants.STRING);
				String[] arr = fileUrl.split(Constants.STRING);
				String fileName = arr[arr.length - 1];
				String savePath = Constants.SAVE_DIR + fileName;
				File fileSaveDir = new File(savePath);
				filePart.write(savePath + File.separator);
				Image toBeSaved = new Image();
				if (fileName != null) {

					addBookToDataBase(user, filePart, fileName, savePath, toBeSaved);
				}
				request.setAttribute(Constants.IMAGES_LIST, user.getImage());
				request.getRequestDispatcher(Constants.IMAGE_MANAGEMENT_UTILITY_JSP).include(request, response);
			} else {
				request.setAttribute(Constants.MSG, Constants.SELECT_IMAGE_OF_SIZE_LESS_THAN_1_MB);
				request.setAttribute(Constants.IMAGES_LIST, user.getImage());
				request.getRequestDispatcher(Constants.IMAGE_MANAGEMENT_UTILITY_JSP).include(request, response);
			}
		} catch (FileNotFoundException fex) {
			request.setAttribute(Constants.MSG, Constants.SELECT_A_FILE);
		}

	}

	private void addBookToDataBase(User user, Part filePart, String fileName, String savePath, Image toBeSaved) {
		try (Session s = hibernateutil.getSession()) {
			s.beginTransaction();
			String GUID = fileName.substring(0, fileName.lastIndexOf('.'));
			toBeSaved.setName(GUID);
			toBeSaved.setSize(filePart.getSize());
			toBeSaved.setPreview(savePath);
			toBeSaved.setUserId(user);
			user.setImage(toBeSaved);
			s.save(toBeSaved);
			s.getTransaction().commit();

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
