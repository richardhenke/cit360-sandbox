
class BankAccount {
	private int balance = 100;
	
	public int getBalance() {
		return balance;
	}
	public int withdraw(int amount) {
		return balance = balance - amount;
	}
	public void deposit(int amount) {
		balance += amount;
	}
}