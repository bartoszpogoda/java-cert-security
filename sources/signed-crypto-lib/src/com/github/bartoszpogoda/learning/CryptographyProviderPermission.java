package com.github.bartoszpogoda.learning;

import java.security.BasicPermission;

public class CryptographyProviderPermission extends BasicPermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9121403582114265860L;

	public CryptographyProviderPermission(String name) {
		super(name);
	}

	public CryptographyProviderPermission(String name, String actions) {
		super(name, actions);
	}
}
