/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all_bean;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;


@MessageDriven(activationConfig = {
	@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Logging")
	,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LoggingBean implements MessageListener {


	private static final Logger LOGGER = Logger.getLogger("Information: ");
	// This isn't logging a Class just the information

	private final FileHandler fileHandler; 


	public LoggingBean() throws IOException {
		
		String fullPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
//		
		System.out.println("\n\n\n\n\n\n\n" + getClass().getResourceAsStream("/"));
//		File file = new File(fullPath); 
//		if(!file.exists()){
//			file.createNewFile();
//		}
		this.fileHandler = new FileHandler("/home/will/logfile.txt", true);
		
	}
	
	@Override
	public void onMessage(Message message) {

		TextMessage textMessage;
		textMessage = (TextMessage) message;
		
		System.out.println("\n\n\n\n\n\n\n" + getClass().getResourceAsStream("/resources/styles/"));
		SimpleFormatter sf = new SimpleFormatter();
		this.fileHandler.setFormatter(sf);
			
		LOGGER.addHandler(this.fileHandler);
		
		try {	
			LOGGER.info(textMessage.getText());
		} catch (JMSException ex) {
			Logger.getLogger(LoggingBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		fileHandler.close();
	}
	
}
