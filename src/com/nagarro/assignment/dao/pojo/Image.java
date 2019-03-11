package com.nagarro.assignment.dao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "Image_Table")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Image_id")
	int imageId;
	@Column(name = "Image_name")
	String Name;
	@Column(name = "Image_Size")
	double size;
	@Column(name = "Image_preview")
	String preview;
	@JoinColumn(name = "user_id")
	@ManyToOne
	User userId;
	public int getImageId() {
		return imageId;
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", Name=" + Name + ", size=" + size + ", preview=" + preview + ", userId="
				+ userId + "]";
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
