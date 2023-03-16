Java Email Client
A user-friendly and secure email client application developed using Java Swing for the user interface and JavaMail API for email communication.

Features:
Email Composition: Compose emails with ease, including recipient address, subject line, and message body.
Recipient Validation: Validate recipient email addresses using regular expressions to prevent sending emails to invalid addresses.
User Authentication: Authenticate the user with their email provider using secure TLS connections and SMTP protocol.
Error Handling: Display informative error messages in case of missing or incorrect user input.
Cross-platform: Compatible with various platforms and operating systems, thanks to the flexibility of Java.

Usage:
1. Ensure you have Java installed on your system. If not, download and install the appropriate Java version for your operating system from the official website.
2. Download the Java Email Client source code and compile it.
3. Run the compiled Java application.
4. Enter the recipient's email address and click "Submit."
5. Fill in your email address, password, subject line, and message body.
6. Click "Send" to send the email. You will receive a confirmation message if the email was sent successfully.

Requirements:
Java Development Kit (JDK) version 15 or later
JavaMail API (javax.mail.jar)

Configuration:
If you are using Gmail, you must enable "Allow less secure apps" in your Google Account settings. Go to https://myaccount.google.com/security and turn on "Allow less secure apps" under the "Less secure app access" section.
You might need to include the javax.mail.jar in your project build path.

Limitations:
This email client currently supports sending emails through Gmail. To use other email providers, you must modify the SMTP host and port settings in the emailProperties() method in the source code.

Disclaimer:
This application requires entering your email password. The password is used for authentication purposes only and is not stored or transmitted to any third party. However, it is recommended to use a test email account for security reasons.
