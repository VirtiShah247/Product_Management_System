package entities;


import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
public class User implements Serializable{

	private String username;
	private String password;
	private String salt;
	private String address;
	private String email;

	public User() {
		super();
	}

	public User(String username, String password, String address, String email, String salt) {
		super();
		this.username = username;
		this.address = address;
		this.email = email;
		if (salt == null || salt.isEmpty()) {
			// New user - generate salt and hash password
			this.salt = generateSalt();
			this.password = hashPassword(password, this.salt);
		} else {
			// Existing user from file - password is already hashed
			this.salt = salt;
			this.password = password; // Don't hash again
		}
	}
	private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash password with salt
    private String hashPassword(String plainPassword, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(plainPassword.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
	public boolean verifyPassword(String plainPassword, String salt) {
        String hashedInput = hashPassword(plainPassword, salt);
        return this.password.equals(hashedInput);
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.salt = generateSalt();
        this.password = hashPassword(password, this.salt);
	}

	public String getSalt() {
		return this.salt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//
	public String toString() {
		return username + " " + password + " " + address + " " + email + " " + salt;
	}

}
