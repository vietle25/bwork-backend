package com.evowork.viewmodel.user;

/**
 * Change password model
 */
public class ChangePasswordModel {

	private String oldPass;
	private String newPass;
	private Integer forgotPassword;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getForgotPassword() {
		return forgotPassword;
	}

	public void setForgotPassword(Integer forgotPassword) {
		this.forgotPassword = forgotPassword;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

}
