package com.nagarro.assignment.dao.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "user_table")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int Id;
	@Column(name = "user_name", length = 100, nullable = false)
	String userName;
	@Column(name = "user_pwd")
	String userPassword;
	@Column(name = "user_ques", length = 100, nullable = false)
	String question;
	@Column(name = "user_ans", length = 100, nullable = false)
	String answer;
	@OneToMany(mappedBy="userId")
	@Cascade(CascadeType.REMOVE)
	private List<Image> image;

	public int getId() {
		return Id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = "Where are you from?";
	}

	public List<Image> getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image.add(image);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "login_table [userName=" + userName + ", userPassword=" + userPassword + "]";
	}

}
