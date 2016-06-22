import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class OpenFile {
	File  file;
     public File openFile(JFrame frame, JTextArea txtArea){

    	 JFileChooser fc = new JFileChooser();
    	 int returnVal = fc.showOpenDialog(frame);
    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
             file = fc.getSelectedFile();
             try {
				BufferedReader bf = new BufferedReader(new FileReader(file));
				txtArea.setText(null);
				String line = bf.readLine();
				while(line != null){
					txtArea.append(line+"\n");
					line = bf.readLine();
				}
				bf.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
          else{
             System.out.println("Open command cancelled by user." );           
          }
    	  return file;
     }
}
