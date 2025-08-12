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

			// if (flag) {
			// 	System.out.println("Write in product file");
			// 	try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			// 		// if (pFile == null) {
			// 		// pFile = new LinkedHashMap<>();
			// 		// }
			// 		for (Map.Entry<Integer, Product> entry : pFile.entrySet()) {
			// 			writer.append(entry.getKey() + "," + entry.getValue().toString());
			// 			writer.newLine();
			// 		}
			// 		writer.close();
			// 	} catch (Exception e) {
			// 		throw new ProductException(e.getMessage());
			// 	}
			// 	
			// }
			return pFile;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pFile;
	}

	public static void writeProduct(Map<Integer, Product> products, boolean appendStatus) throws ProductException {
		// Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Product.csv");

		// System.out.println("Write in product file");
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
		// Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Product.csv");
		if (f.exists()) {
			// System.out.println("Read product file");
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(" ");
					int id = Integer.parseInt(parts[0]);
					String name = parts[1];
					int qty = Integer.parseInt(parts[2]);
					double price = Double.parseDouble(parts[3]);
					String category = parts[4];
					// System.out.println("id: " + id + "name: " + name + "qty: " + qty + "price: " + price + "category: "
							// + category);
					Product product = new Product(id, name, qty, price, category);
					pFile.put(id,product);
					// System.out.println("pFile---" + pFile);
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
		// Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Customer.csv");
		boolean flag = false;
		try {
			if (!f.exists()) {
				System.out.println("Customer file not exist");
				f.createNewFile();
				flag = true;
			}

			// if (flag) {
			// 	System.out.println("Write in customer file");
			// 	try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			// 		// if (pFile == null) {
			// 		// pFile = new LinkedHashMap<>();
			// 		// }
			// 		for (Map.Entry<String, Customer> entry : cFile.entrySet()) {

			// 			writer.append(entry.getKey() + "," + entry.getValue().toString());
			// 			writer.newLine();
			// 		}
			// 		writer.close();
			// 	} catch (Exception e) {
			// 		throw new ProductException(e.getMessage());
			// 	}

			// }

			return cFile;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return cFile;

	}

	public static void writeCustomer(Map<String, Customer> customer, boolean appendStatus) throws CustomerException {
		// Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Customer.csv");

		// System.out.println("Write in customer file");
		if (f.exists()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, appendStatus))) {
				// System.out.println("customer write --- " + customer);
				for (Map.Entry<String, Customer> entry : customer.entrySet()) {
					// System.out.println("get key--- " + entry.getKey());
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
			// System.out.println("Read customer file");
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = reader.readLine()) != null) {
					// System.out.println("customer line ---- " + line);
					String[] parts = line.split(" ",6);
					String email = parts[0];
					String username = parts[1];
					String password = parts[2];
					String salt = parts[3];
					double walletBalance = Double.parseDouble(parts[4]);
					String address = parts[5];
					// System.out.println(
					// 		"email: " + email +
					// 				" username: " + username + " address: " + address + " wallet: " + walletBalance
					// 				+ " password: " + password);
					Customer customer = new Customer(walletBalance, username, password, address, email, salt);
					cFile.put(email,customer);
					// System.out.println("cFile---" + cFile);
				}
				// System.out.println("customer read close");
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

			// if (flag) {
			// 	System.out.println("Write in transaction file");
			// 	try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			// 		for (Transaction tr : tFile) {
			// 			writer.append(tr.toString());
			// 			writer.newLine();
			// 		}
			// 		writer.close();
			// 	} catch (Exception e) {
			// 		throw new TransactionException(e.getMessage());
			// 	}
			// }

			return tFile;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return tFile;
	}

	public static void writeTransaction(List<Transaction> transaction, boolean appendStatus)
			throws TransactionException {
		// Map<Integer, Product> pFile = new LinkedHashMap<>();
		File f1 = new File("src","database");
		File f = new File(f1,"Transaction.csv");

		// System.out.println("Write in transaction file");
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
			// System.out.println("Read transaction file");
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
					// System.out.println("Transaction line --- " + line);
					tFile.add(transaction);
            		// tFile.add(new Transaction());
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

