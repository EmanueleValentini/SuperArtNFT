package com.azienda.ecommerce.utilities;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;

public class EmailUtils {

	
	public static void sendEmail(String to,Ordine ordine, String uploadPath, HttpServletRequest request, BusinessLogic bl) throws Exception {
		Properties prop = new Properties(); // è una classe java che rappresenta una mappa
		//particolare perchè le chiavi valore sono tutte stringe, è perfetta per i file di properties
		//questo diventerà quindi l'oggetto che tiene le coppie
		
		InputStream is= EmailUtils.class.getClassLoader().getResourceAsStream("email.properties");
		//apre una risorsa associata alla radice delle classi, tutte le classi java hanno un attributo
		//.class mi fa accedere ad un oggetto chiamato Class che ha il metodo getClassLoader()
		//è la classe di java incapsula il caricamento di tutte le altre, questo classloader ha il metodo
		//getResourceAsStream che prende il file dalla radice, se lo mettevo in com "com/email.properties"
		
		prop.load(is);
		//tu gli dai un inputstream (un flusso dati in lettura) e lui in automatico
		//si carica tutte le coppie nel file a -> b 
		
		String emailEnabled = prop.getProperty("enabled");

		
		if ( emailEnabled != null && emailEnabled.equalsIgnoreCase("true") ) {
			prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //serve ad abilitare l'ssl sull'email
			//sennò non partono
	
			
			Utente u = ordine.getUtente();
			
			//la session prende 2 attributi, la prop e una classe concreta che implementa
			//Authenticator
			//
			
			
			Session session = Session.getInstance(prop,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(prop.getProperty("username"), prop.getProperty("password"));
				} //si autentica con l'username nelle properties e la password
			});
			
			
			
			
			Message message = new MimeMessage(session); //devo creare il messaggio vero e proprio, mi lego alla session appena creata
			message.setFrom(new InternetAddress(prop.getProperty("from"))); //set del mittente
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

			//specifico i destinatari, il primo parametro è che tipo di recipient, (TO, CC, BCC)
			//il 2 parametro è a chi sto mandando il messaggio
			message.setSubject("Ordine #" + ordine.getId() + " confermato"); //setto l'oggetto della mail

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Grazie per il tuo acquisto su SuperartNft" + "\n" +
			"Ciao " + u.getNomeUtente() + " confermiamo il tuo ordine! Grazie per averci concesso la tua fiducia!" + "\n" 
					+ "Il tuo articolo è allegato a questa mail!");			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();

			String filename = bl.aggiungiImmagineUno(ordine.getProdotto(), uploadPath, request);
			DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("ordine.jpg");
	         multipart.addBodyPart(messageBodyPart);
	         message.setContent(multipart );

			
			Transport.send(message);
		}
		
	}
	
	
	public static void sendGenericEmail(String oggetto, String testo, String to,  String codice) throws Exception {
		Properties prop = new Properties(); // è una classe java che rappresenta una mappa
		//particolare perchè le chiavi valore sono tutte stringe, è perfetta per i file di properties
		//questo diventerà quindi l'oggetto che tiene le coppie
		
		InputStream is= EmailUtils.class.getClassLoader().getResourceAsStream("email.properties");
		//apre una risorsa associata alla radice delle classi, tutte le classi java hanno un attributo
		//.class mi fa accedere ad un oggetto chiamato Class che ha il metodo getClassLoader()
		//è la classe di java incapsula il caricamento di tutte le altre, questo classloader ha il metodo
		//getResourceAsStream che prende il file dalla radice, se lo mettevo in com "com/email.properties"
		
		prop.load(is);
		//tu gli dai un inputstream (un flusso dati in lettura) e lui in automatico
		//si carica tutte le coppie nel file a -> b 
		
		String emailEnabled = prop.getProperty("enabled");

		
		if ( emailEnabled != null && emailEnabled.equalsIgnoreCase("true") ) {
			prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //serve ad abilitare l'ssl sull'email
			//sennò non partono
	
			
			
			//la session prende 2 attributi, la prop e una classe concreta che implementa
			//Authenticator
			//
			
			
			Session session = Session.getInstance(prop,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(prop.getProperty("username"), prop.getProperty("password"));
				} //si autentica con l'username nelle properties e la password
			});
			Message message = new MimeMessage(session); //devo creare il messaggio vero e proprio, mi lego alla session appena creata
			message.setFrom(new InternetAddress(prop.getProperty("from"))); //set del mittente
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

			//specifico i destinatari, il primo parametro è che tipo di recipient, (TO, CC, BCC)
			//il 2 parametro è a chi sto mandando il messaggio
			message.setSubject(oggetto); //setto l'oggetto della mail
			message.setText(testo + "\n\n\n" 
					+ "Per disiscriverti clicca su questo link: " + "http://localhost:8080/ecommerce/disiscriviti?"+Costanti.KEY_CODICE_SEGRETO + "=" + codice);
			
			Transport.send(message);
		}
		
	}

	
}
