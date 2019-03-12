package com.nagarro.assignment.validation;

import java.io.File;
import java.util.List;

import com.nagarro.assignment.dao.pojo.Image;

public class Validate {

	/**
	 * 
	 * @param source
	 *            -new file to be entered
	 * @param list
	 *            - containing list of all uploaded images by single user
	 * @param size
	 *            - size of 'source' file
	 * @return - true if all the checks are true
	 */
	public static boolean validateImageData(File source, List<Image> list, double size) {
		return validateExtension(source) && validateSingleFileSize(size) && validateTotalSize(list, size);
	}

	/**
	 * 
	 * @param source
	 * @return true if extension is of type image
	 */
	public static boolean validateExtension(File source) {
		String name = source.getName();
		String extensions[] = { ".gif", ".jpg", ".jpeg", ".png" };
		for (int i = 0; i < extensions.length; i++) {
			if (name.endsWith(extensions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param size
	 * @return true if size is upto 1 MB
	 */
	public static boolean validateSingleFileSize(double size) {
		boolean answer = false;
		if (size/1024 <1) {
			answer = true;
		}
		return answer;
	}

	/**
	 * 
	 * @param list
	 * @param size
	 * @return true if total size of list of images is upto 10 MB
	 */
	public static boolean validateTotalSize(List<Image> list, double size) {
		boolean answer = false;
		double totalSize = size;
		for (int i = 0; i < list.size(); i++) {
			totalSize += list.get(i).getSize();
		}
		if (totalSize/1024 <= 1024*10) {
			answer = true;
		}
		return answer;
	}

}
