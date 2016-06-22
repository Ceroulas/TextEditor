import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

public class TextEditor {

	JFrame frame;
	JLabel infoMouse,infoArea;
	JButton btOpen, btSave, btEdit;
	JTextArea textArea, lines;
	JMenu menuFile, menuEdit, menuHelp, menuFormat;
	JMenuBar menuBar;
	JMenuItem menuOpen, menuSave;
	File fileOpenned;

	AbstractAction buttonPressed;

	public static void main(String[] args) {

		new TextEditor().montaTela();

	}

	public void montaTela() {
		frame = new JFrame("Jonathan's TextEditor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		menuBar = new JMenuBar(); 
		menuFile = new JMenu("File");
		menuEdit = new JMenu("Edit");
		menuFormat = new JMenu("Format");
		menuHelp = new JMenu("Help");
		menuOpen = new JMenuItem("Open");
		menuSave = new JMenuItem("Save");
		//menuOpen.addActionListener();
		menuFile.add(menuOpen);
		menuFile.add(menuSave);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuFormat);
		menuBar.add(menuHelp);
		
		
		btSave = new JButton("Save");
		btOpen = new JButton("Open");
		btEdit = new JButton("Edit");
		
		//Button Listeners and binding keys
		createActionForButton(btSave);
		createActionForButton(btOpen);
		createActionForButton(btEdit);
		//buton Save
		btSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK),"CTRLS_pressed");
		btSave.getActionMap().put("CTRLS_pressed", buttonPressed);
		btSave.addActionListener(buttonPressed);
		//Button Open
		btOpen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK),"CTRLO_pressed");
		btOpen.getActionMap().put("CTRLO_pressed", buttonPressed);
		btOpen.addActionListener(buttonPressed);
		
		//Button Edit
		btEdit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK),"CTRLN_pressed");
		btEdit.getActionMap().put("CTRLN_pressed", buttonPressed);
		btEdit.addActionListener(buttonPressed);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());
		addComp(panel2, btOpen, 0, 0, 1, 1,0.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
		addComp(panel2, btSave, 1, 0, 1, 1,0.0,0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE);
		addComp(panel2, btEdit, 2, 0, 1, 1,0.0,0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE);
		addComp(panel, panel2, 0, 0, 1, 1,0.0,0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH);
		
		lines = new JTextArea("1");
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
		
		textArea = new JTextArea(40, 100);
		textArea.setEditable(true);
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10,10,10,10)));
        
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //mostrar coluna de numeros
        ListenForDocument lForDocument = new ListenForDocument();
        textArea.getDocument().addDocumentListener(lForDocument);
        
        //mostrar posição cursor
        ListenForCaret lForCaret = new ListenForCaret();
		textArea.addCaretListener(lForCaret);
		
		scrollPane.getViewport().add(textArea);
        scrollPane.setRowHeaderView(lines);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addComp(panel, scrollPane, 0,2,3,3,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        				
		infoMouse = new JLabel("Ln: None Col: None");
		infoMouse.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		addComp(panel, infoMouse, 0,5,1,1,0.0,0.0, GridBagConstraints.WEST, GridBagConstraints.NONE);
		infoArea = new JLabel("bu");
		infoArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		addComp(panel, infoArea, 1,5,1,1,0.0,0.0, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		frame.setJMenuBar(menuBar);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		
	}

	private void addComp(JPanel thePanel, Component comp, int xPos, int yPos, int compWidht, int compHeight,
			double weightX, double weightY, int place, int stretch) {
		GridBagConstraints gridConstraints = new GridBagConstraints();

		gridConstraints.gridx = xPos;
		gridConstraints.gridy = yPos;
		gridConstraints.anchor = place;
		gridConstraints.insets = new Insets(1, 1, 1, 1);
		gridConstraints.fill = stretch;
		gridConstraints.gridheight = compWidht;
		gridConstraints.gridheight = compHeight;
		gridConstraints.weightx = weightX;
		gridConstraints.weighty = weightY;

		thePanel.add(comp, gridConstraints);
	}
	private class ListenForDocument implements DocumentListener{
		public String getText(){
			int caretPosition = textArea.getDocument().getLength();
			Element root = textArea.getDocument().getDefaultRootElement();
			String text = "1"+System.getProperty("line.separator");
			for(int i= 2; i < root.getElementIndex(caretPosition)+2; i++){
				text += i + System.getProperty("line.separator");
			}
			return text;
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			lines.setText(getText());
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			lines.setText(getText());
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			lines.setText(getText());
			
		}
		
	}
	
	private class ListenForCaret implements CaretListener{
		

		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea areaInfo = (JTextArea)e.getSource();
			int line=1, col=1;
			
			try {
				int pos = areaInfo.getCaretPosition();
				line = areaInfo.getLineOfOffset(pos);
				col = pos - areaInfo.getLineStartOffset(line);
				
				line++;
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			infoMouse.setText("Lin: "+line+" Col: "+col);
			
		}  
		
	}
	private void createActionForButton(JButton button) {
		buttonPressed = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btSave) {
					SaveFile sf = new SaveFile();
					sf.saveFile(frame, fileOpenned, textArea);
				} else if (e.getSource() == btOpen) {
					OpenFile of = new OpenFile();
					fileOpenned = of.openFile(frame, textArea);
				} else {
					EditFile ef = new EditFile();
				}
			}
		};

	}
}
