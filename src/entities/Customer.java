package entities;

import java.io.*;
// write a code for Customer class with Private double walletBalance. It has constructor to store data, getter and setter method of each data and toString method which returns all this data like username=username

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
		// return "Customer [email=" + getEmail()
		// + ", username=" + getUsername() + ", address=" + getAddress() + ",
		// walletBalance=" + walletBalance + ", password=" + getPassword()
		// + ", ]";
		// System.out.println("addresss ---- " + getAddress());
		return getEmail() + " " + getUsername() + " " + getPassword() + " " + getSalt() + " " + walletBalance + " " + getAddress();
	}

}
