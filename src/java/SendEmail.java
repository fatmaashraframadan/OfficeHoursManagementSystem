
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nardin
 */
public class SendEmail {

    public String getRandom() {
        Random rand = new Random();
        int number = rand.nextInt(99999999);
        return String.format("%08d", number);
    }

    public boolean Sendemail(String toemail, String subject, String toName, String content) {
        boolean sended = false;
        String toEmail = toemail;
        String FromEmail = "nardinee311nabil@gmail.com";
        String password = "nardinenabil371*";
        try {
            /*Properties pr=new Properties();
        pr.setProperty("mail.smtp.host", "smtp.gmail.com");
        pr.setProperty("mail.smtp.port", "587");
        pr.setProperty("mail.smtp.auth", "true");
        pr.setProperty("mail.smtp.starttls.enable", "true");
        pr.put("mail.smtp.socketFactory.port", "587");
       pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");*/
            System.out.println("Preparing.. ");
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FromEmail, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText("Hi," + (toName + "\n") + content);
            Transport.send(message);
            sended = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sended;
    }
}
