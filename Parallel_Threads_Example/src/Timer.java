
public class Timer extends Thread {
	@Override
	public void run() {
		int iterations = 5;
		int count = 0;
		try {
			for (int i = 0; i < iterations; i++) {
				System.out.println("Time: " + i);
				sleep(1000);
				count++;
			}
		} catch (InterruptedException e) {
			System.out.println("Total Time: " + count + " Seconds");
		}
		
	}

}
