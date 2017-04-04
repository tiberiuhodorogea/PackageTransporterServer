package Framework.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	private String filePath;
	private String fileName;
	private boolean append = true;
	private FileWriter fw;
	private PrintWriter pw;
	
	
	
	public WriteFile(String filePath,String fileName){
		if( null == filePath || null == fileName ){
			throw new UnsupportedOperationException();
		}
		
		this.filePath = filePath;
		this.fileName = fileName;
	}
	
	public WriteFile( String filePath,String fileName, boolean append ){
		if( null == filePath || null == fileName ){
			throw new UnsupportedOperationException();
		}
		
		this.append = append;
		this.filePath = filePath;
		this.fileName = fileName;
		
		
	}
	
	public void writeLine( String text ) throws IOException{
		if( null != text ){
			String filePathName = filePath + "/" + fileName;
			fw = new FileWriter(filePathName,append);
			pw = new PrintWriter(fw);
			pw.printf("%s" + "%n", text );
			pw.close();
			fw.close();
		}
	}
	
	public void setFileName(String fileName){
		if(fileName != null){
			this.fileName = fileName;
		}
	}
}
