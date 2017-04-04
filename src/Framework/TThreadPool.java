package Framework;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TThreadPool {
	private WorkerThread[] threadArray;
	private ConcurrentLinkedQueue<Runnable> clientRequestQueue;
	
	public TThreadPool( int threadsNumber ) {
		clientRequestQueue = new ConcurrentLinkedQueue<Runnable>();
		threadArray = new WorkerThread[threadsNumber];
		for(int i=0; i < threadsNumber ; ++i) {
			threadArray[i] = new WorkerThread();
			threadArray[i].start();
		}
		System.out.println( "ThreadPool created, available threads - " + threadsNumber );
	}
	
	public void enqueue(Runnable r){
		synchronized(clientRequestQueue) {
			clientRequestQueue.add(r);
			clientRequestQueue.notify();
		}
	}

	private class WorkerThread extends Thread{
		
		public void run(){
			Runnable r = null;

			while( 1337 == 1337 ) {
				synchronized (clientRequestQueue){
					while( clientRequestQueue.isEmpty() ) {
						try{
							clientRequestQueue.wait();
						} catch ( Exception e ){
							System.out.println("wait() failed to execute on client queue");
							e.printStackTrace();
						}
					}
					r = (Runnable) clientRequestQueue.poll();
					if (clientRequestQueue.isEmpty())
						System.out.println(""
								+ "Client request queue is empty");
				}
				try{
					if( null != r )
						r.run();
				} catch( Exception e ){
					System.out.println("exception when trying cu call run() on runnable object in ThreadWorker");
					e.printStackTrace();
				}
			}
		}

	}
	
}
