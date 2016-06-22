import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class SaveFile {
	File newFile;
	BufferedWriter writer;
	JFileChooser fileChooser;

	public void saveFile(JFrame frame, File file, JTextArea txtArea) {

		if (file == null) {
			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a name to save file");
			int userSelection = fileChooser.showSaveDialog(frame);
			if(userSelection == JFileChooser.APPROVE_OPTION){
				newFile = fileChooser.getSelectedFile();
				
				try {
					writer = new BufferedWriter(new FileWriter(newFile.getAbsolutePath()));
					writer.write(txtArea.getText());
					writer.close();
					JOptionPane.showMessageDialog(frame, "The file saved sucessfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frame, "The file could not be saved!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}

		}else{
			try {
				writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
				writer.write(txtArea.getText());
				writer.close();
				JOptionPane.showMessageDialog(frame, "The file saved sucessfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "The file could not be saved!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
