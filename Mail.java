import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

public class EmailApp extends JFrame {
    private static final long serialVersionUID = 1L;

    JPanel panel;
    JPanel panel2;
    JTextField toField;
    JTextField fromField;
    JTextField subjField;
    JTextArea messageArea;
    JPasswordField passwordField;
    JLabel label;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JButton button;

    public EmailApp() {
        panel = new JPanel();
        panel.setLayout(null);

        label = new JLabel("Recipient");
        label.setBounds(50, 10, 100, 40);
        panel.add(label);

        toField = new JTextField();
        toField.setBounds(120, 15, 200, 30);
        toField.setFocusable(true);
        panel.add(toField);

        button = new JButton("Submit");
        button.setBounds(150, 70, 100, 30);
        panel.add(button);
        button.addActionListener(e -> {
            String rec = toField.getText();

            if (rec.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter a recipient");
            } else {
                if (!validateEmail(rec)) {
                    JOptionPane.showMessageDialog(null, "The email you entered is not valid");
                } else {
                    sendEmail(rec);
                }
            }
        });

        this.setContentPane(panel);
        this.setTitle("Send an Email");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(400, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void sendEmail(String recipient) {
        panel2 = new JPanel();
        panel2.setLayout(null);

        System.out.println("Panel changed, waiting for your info & message");
        Properties prop = emailProperties();

        label = new JLabel("Enter your Email");
        label.setBounds(20, 10, 100, 40);
        panel2.add(label);

        fromField = new JTextField();
        fromField.setBounds(170, 15, 180, 30);
        fromField.setFocusable(true);
        panel2.add(fromField);

        label2 = new JLabel("Enter your Password");
        label2.setBounds(20, 60, 140, 40);
        panel2.add(label2);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(170, 65, 180, 30);
        panel2.add(passwordField);

        label3 = new JLabel("Subject");
        label3.setBounds(70, 120, 60, 40);
        panel2.add(label3);

        subjField = new JTextField();
        subjField.setBounds(130, 125, 260, 30);
        panel2.add(subjField);

        label4 = new JLabel("Message");
        label4.setBounds(70, 170, 60, 40);
        panel2.add(label4);

        messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(130, 175, 276, 230);
        panel2.add(scrollPane);

        button = new JButton("Send");
        button.setBounds(100, 420, 100, 30);
        panel2.add(button);

        button.addActionListener(e -> {
            String myEmail = fromField.getText();
            String myPassword = passwordField.getText();
            String mailSubject = subjField.getText();
            String mailMessage = messageArea.getText();

            if (myEmail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter your email");
            } else if (myPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter a password");
            } else if (myPassword.length() < 5) {
                JOptionPane.showMessageDialog(null, "Password must have 5 or more characters");
            } else if (mailMessage.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There must be a message!");
            } else {
                if (!validateEmail(myEmail)) {
                    JOptionPane.showMessageDialog(null, "The email you entered is not valid");
                } else {
                    Session session = Session.getInstance(prop, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(myEmail, myPassword);
                        }
                    });

                    Message message = createMessage(session, myEmail, recipient, mailSubject, mailMessage);

                    try {
                        Transport.send(message);
                        System.out.println("Message sent successfully");
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });

        button = new JButton("Close");
        button.setBounds(300, 420, 100, 30);
        panel2.add(button);
        button.addActionListener(e -> System.exit(0));

        this.setContentPane(panel2);
        this.setTitle("Send an Email");
        this.setLocation(0, 0);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    private static Properties emailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        return prop;
    }

    private static Message createMessage(Session session, String myEmail, String recipient, String mailSubject, String mailMessage) {
        try {
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(myEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(mailSubject);
            msg.setText(mailMessage);

            return msg;
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static void main(String[] args) {
        new EmailApp();
    }
}