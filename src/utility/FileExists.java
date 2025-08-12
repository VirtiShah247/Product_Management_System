package utility;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import entities.*;
import exceptions.*;

public class FileExists {
	public static Map<Integer, Product> productFile() {

		Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Product.csv");
		boolean flag = false;
		try {
			if (!f.exists()) {
				System.out.println("Product file not exist");
				f.createNewFile();
				flag = true;
			}

			return pFile;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pFile;
	}

	public static void writeProduct(Map<Integer, Product> products, boolean appendStatus) throws ProductException {
		File f1 = new File("src","database");
		File f = new File(f1,"Product.csv");

		if (f.exists()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, appendStatus))) {
				for (Map.Entry<Integer, Product> entry : products.entrySet()) {
					writer.append(entry.getValue().toString());
					writer.newLine();
				}
				writer.close();
			} catch (Exception e) {
				throw new ProductException(e.getMessage());
			}
		}

	}

	public static Map<Integer, Product> readProduct() throws ProductException {
		Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Product.csv");
		if (f.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(" ");
					int id = Integer.parseInt(parts[0]);
					String name = parts[1];
					int qty = Integer.parseInt(parts[2]);
					double price = Double.parseDouble(parts[3]);
					String category = parts[4];
					
					Product product = new Product(id, name, qty, price, category);
					pFile.put(id,product);
				}
				reader.close();
				return pFile;
			} catch (Exception e) {
				throw new ProductException("product error: " + e.getMessage());
			}
		}
		return pFile;
	}

	public static Map<String, Customer> customerFile() throws CustomerException {
		Map<String, Customer> cFile = new LinkedHashMap<>();

		File f1 = new File("src","database");
		File f = new File(f1,"Customer.csv");
		boolean flag = false;
		try {
			if (!f.exists()) {
				System.out.println("Customer file not exist");
				f.createNewFile();
				flag = true;
			}
			
			return cFile;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cFile;

	}

	public static void writeCustomer(Map<String, Customer> customer, boolean appendStatus) throws CustomerException {
		File f1 = new File("src","database");
		File f = new File(f1,"Customer.csv");

		if (f.exists()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, appendStatus))) {
				for (Map.Entry<String, Customer> entry : customer.entrySet()) {
					writer.append(entry.getValue().toString());
					writer.newLine();
				}
				writer.close();
			} catch (Exception e) {
				throw new CustomerException(e.getMessage());
			}
		}

	}

	public static Map<String, Customer> readCustomer() throws CustomerException {
		Map<String, Customer> cFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Customer.csv");
		if (f.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(" ",6);
					String email = parts[0];
					String username = parts[1];
					String password = parts[2];
					String salt = parts[3];
					double walletBalance = Double.parseDouble(parts[4]);
					String address = parts[5];
					
					Customer customer = new Customer(walletBalance, username, password, address, email, salt);
					cFile.put(email,customer);
				}
				reader.close();
				return cFile;
			} catch (Exception e) {
				throw new CustomerException("customer error: " + e.getMessage());
			}
		}
		return cFile;
	}

	public static List<Transaction> transactionFile() {

		List<Transaction> tFile = new ArrayList<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Transaction.csv");
		boolean flag = false;
		try {
			if (!f.exists()) {
				System.out.println("Transaction file not exist");
				f.createNewFile();
				flag = true;
			}

			return tFile;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return tFile;
	}

	public static void writeTransaction(List<Transaction> transaction, boolean appendStatus)
			throws TransactionException {
		File f1 = new File("src","database");
		File f = new File(f1,"Transaction.csv");

		if (f.exists()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, appendStatus))) {
				for (Transaction tr : transaction) {
					writer.append(tr.toString());
					writer.newLine();
				}
				writer.close();
			} catch (Exception e) {
				throw new TransactionException(e.getMessage());
			}
		}

	}

	public static List<Transaction> readTransaction() throws TransactionException {
		List<Transaction> tFile = new ArrayList<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Transaction.csv");
		if (f.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(" ");
					String username = parts[0];
					String email = parts[1];
					int productId = Integer.parseInt(parts[2]);
					String productName = parts[3];
					int qty = Integer.parseInt(parts[4]);
					double price = Double.parseDouble(parts[5]);
					double total = Double.parseDouble(parts[6]);
					LocalDate dt = LocalDate.parse(parts[7]);

					Transaction transaction = new Transaction(username,email,productId,productName,qty,price,total,dt);
					tFile.add(transaction);
        		}
				reader.close();
				return tFile;
			}
				
			catch (Exception e) {
				throw new TransactionException("tarnsaction error: " + e.getMessage());
			}
		}
		return tFile;
	}
}

