package com.macys.utils;

import org.jasypt.util.password.*;

public class EncryptionUtils {
	
	
	public static String encryptPassword(String plainPassword){
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		return passwordEncryptor.encryptPassword(plainPassword);
	}
	
	public static Boolean checkPassword(String plainPassword, String encryptedPassword){
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		return passwordEncryptor.checkPassword(plainPassword, encryptedPassword);
	}

}
