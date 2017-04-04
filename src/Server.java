import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Framework.*;


public class Server {
	private static ServerSocket serverSocket;
	private static Socket clientEntrySocket;
	private static TThreadPool clientRequestThreadPool;
	
	public static void main(String[] args){
		int serverPort = getServerPort();
		System.out.println("Binding to port " + serverPort + " ...");
		
		try {
			serverSocket = new ServerSocket(serverPort);
			initializeThreadPool();
			
		} catch (IOException e) {
			System.out.println( "Could not listen on port: " + serverPort );
		}
		
		System.out.println("Server started. Listening to the port " + serverPort);
		
		while( true ){
			
			try {
				System.out.println("Waiting for client...");
				clientEntrySocket = serverSocket.accept();
				System.out.println("Client connect, passing it to ClientManager...");
				
				clientRequestThreadPool.enqueue(new ClientManager(clientEntrySocket));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static int getServerPort(){
		int ret = 0;
		ret = Integer.parseInt(SettingsManager.getSetting(SettingsNames.SERVER_PORT_VAR_NAME));
		
		return ret;
	}
	
	private static void initializeThreadPool(){
		int poolSize = Integer.parseInt(SettingsManager.getSetting(SettingsNames.THREAD_POOL_SIZE_VAR_NAME));
		clientRequestThreadPool = new TThreadPool(poolSize);
	}
}