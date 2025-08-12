package entities;

import java.io.*;

public class Customer extends User implements Serializable {

	private double walletBalance;

	public Customer(double walletBalance, String username, String password, String address, String email, String salt) {
		super(username, password, address, email, salt);
		this.walletBalance = walletBalance;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	//
	public String toString() {
		return getEmail() + " " + getUsername() + " " + getPassword() + " " + getSalt() + " " + walletBalance + " " + getAddress();
	}

}
