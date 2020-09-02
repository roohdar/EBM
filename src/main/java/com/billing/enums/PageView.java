package com.billing.enums;

public enum PageView {

	INDEX("/view/index"),
	ELECTRICITY_PROVIDER_REGISRATION_PAGE ("view/provider/registration"),
	ELECTRICITY_PROVIDER_LOGIN_PAGE ("view/provider/login"),
	ELECTRICITY_USER_REGISRATION_PAGE ("view/user/registration"),
	ELECTRICITY_USER_LIST_PAGE ("view/user/userList");

	private String view;

	private PageView(String view) {

		this.view = view;
	}

	public String getView() {
		return view;
	}

}
