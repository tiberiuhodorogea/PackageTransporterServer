package Framework.Log;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AsyncLogWriter implements Runnable {

	WriteFile writeFile;
	ConcurrentLinkedQueue<String> concurrentQueue;
	
	public AsyncLogWriter(WriteFile writeFile, ConcurrentLinkedQueue<String> concurrentQueue ){
		this.writeFile = writeFile;
		this.concurrentQueue = concurrentQueue;
	}
	
	@Override
	public void run() {
		System.out.println("Async logger thread started...");
		boolean forever = true;
		String currentLine = null;
		while( forever ){
			currentLine = null;
			try {
				currentLine = concurrentQueue.poll();
				if( null != currentLine ){
					writeFile.writeLine( currentLine );
				}
			} catch (IOException e) {
				System.out.println("AsyncLogWriter failed to write log: " + currentLine );
				e.printStackTrace();
			}
		}
		
	}

}
