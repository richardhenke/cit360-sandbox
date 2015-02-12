
public class MonicaAndChandlerJob implements Runnable {
	public BankAccount account = new BankAccount();
	
	public static void main(String[] args) {
		MonicaAndChandlerJob theJob = new MonicaAndChandlerJob();
		Thread one = new Thread(theJob);
		Thread two = new Thread(theJob);
		one.setName("Chandler");
		two.setName("Monica");
		one.start();
		two.start();
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			makeWithdrawal(20);
			if (account.getBalance() < 0) {
				System.out.println("---------------------- Overdrawn! ---------------------");
			}
			makeDeposit(1);
		}
	}
	
	private synchronized void makeWithdrawal(int amount) {
		if (account.getBalance() >= amount) {
			System.out.println(Thread.currentThread().getName() + " is about to withdraw");
			try {
				System.out.println(Thread.currentThread().getName() + " is going to sleep");
				Thread.sleep(500);
			} catch (InterruptedException e) {e.printStackTrace(); }
			System.out.println(Thread.currentThread().getName() + " woke up!");
			account.withdraw(amount);
			System.out.println(Thread.currentThread().getName() + " completes the withdrawal");
		} else {
			System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
		}
	}
	private synchronized void makeDeposit(int amount) {
		System.out.println(Thread.currentThread().getName() + " makes a deposit of $1");
		account.deposit(amount);
		System.out.println("Balance is: " + account.getBalance() + " Name: " + Thread.currentThread().getName());
	}
}