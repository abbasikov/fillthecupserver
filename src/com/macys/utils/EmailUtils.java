/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macys.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailUtils {
    
    
    
    private static void sendEmail(String subject,String messageBody,String toEmail){
        Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("cleanestimatex@gmail.com","abcd9876");
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cleanestimatex@gmail.com","CleanEstimateX"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(messageBody);
			Transport.send(message);
            System.out.println("Email sent successully.");
 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void sendEmail(String recipentFullName,String recipentEmail,String recipentPassword, EmailTemplateEnum emailTemplate){
        
        switch(emailTemplate){
            case NEW_REGISTRATION:{
                String subject = "Registered Successfully";
                String messageBody = "Dear "+recipentFullName.toUpperCase()+"\n\n"+
                                     "You have been registered successfully. Following are your credentials.\n\n"+
                                     "Email: "+recipentEmail+"\n"+
                                     "Password: "+recipentPassword+"\n\n"+
                                     "Thank you for using CleanEstimateX System";
                sendEmail(subject, messageBody, recipentEmail);
                break;
            }
            
            case PASSWORD_RESET:{
            	String subject = "Password Reset";
            	String messageBody = "Dear "+recipentFullName.toUpperCase()+"\n\n"+
                        "Your password has been reset. Following is your new password.\n\n"+
                        "Password: "+recipentPassword+"\n\n"+
                        "Thank you for using CleanEstimateX System";
            	sendEmail(subject, messageBody, recipentEmail);
            	break;
            	
            }
            
            case PASSWORD_CHANGE:{
            	String subject = "Password Change";
            	String messageBody = "Dear "+recipentFullName.toUpperCase()+"\n\n"+
                        			 "Your password has been changed successfully. Following are your new credentials.\n\n"+
                        			 "Email: "+recipentEmail+"\n"+
                        			 "Password: "+recipentPassword+"\n\n"+
                        			 "Thank you for using CleanEstimateX System";
            	sendEmail(subject, messageBody, recipentEmail);
            	break;
            	
            }
            
        }
        
    }
    
    
}
