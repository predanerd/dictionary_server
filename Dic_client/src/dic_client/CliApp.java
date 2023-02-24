//John Dan
//694911
package dic_client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CliApp {
	//Variables
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblWord;
	private JLabel lblDefinition;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JButton btnDelete;
	private JButton btnAdd;
	private JButton btnDelete_1;
	private static JTextArea textArea_1;
	private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				Socket clisocket=null;
				// Object that will store the parsed command line arguments.
		        cliCommand cmmdarg = new cliCommand();

		        // Parser provided by args4j.
		        CmdLineParser parser = new CmdLineParser(cmmdarg);
		      
				try {
					parser.parseArgument(args);
					clisocket = new Socket(cmmdarg.getHost(),cmmdarg.getPort());
					CliApp window = new CliApp(clisocket);
					textArea_1.append("Connection Established\n");
					window.frame.setVisible(true);
					//Failure Model
				} catch(CmdLineException e) {
					System.out.println("Parsing errors.");
		            System.exit(0);
				}catch (UnknownHostException e){
					JOptionPane.showMessageDialog(null, "Host is unreachable","Error",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Error reading the file","Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
		        }catch(Exception e)
				{
		        	JOptionPane.showMessageDialog(null,"Some error has come up","Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CliApp(Socket socket) {
		initialize();
		//Search function for click
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchOp(socket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//Search function for enter on textfield
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						searchOp(socket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		//Add for click on bttn
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addOp(socket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//Add for enter onbutton
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						addOp(socket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		//Delete function for click
		btnDelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					delOp(socket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//Delete function for enter on textfield
		textField_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						delOp(socket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Dictionary client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 50, 50, 50, 50};
		gridBagLayout.rowHeights = new int[]{29, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Search");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(5, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 0;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		
		lblWord = new JLabel("Word:");
		GridBagConstraints gbc_lblWord = new GridBagConstraints();
		gbc_lblWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblWord.gridx = 0;
		gbc_lblWord.gridy = 1;
		frame.getContentPane().add(lblWord, gbc_lblWord);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 1;
		frame.getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		lblDefinition = new JLabel("Definition:");
		GridBagConstraints gbc_lblDefinition = new GridBagConstraints();
		gbc_lblDefinition.insets = new Insets(0, 0, 5, 5);
		gbc_lblDefinition.gridx = 2;
		gbc_lblDefinition.gridy = 1;
		frame.getContentPane().add(lblDefinition, gbc_lblDefinition);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 1;
		frame.getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		btnAdd = new JButton("Add");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 4;
		gbc_btnAdd.gridy = 1;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.gridwidth = 4;
		gbc_textField_4.insets = new Insets(5, 5, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 0;
		gbc_textField_4.gridy = 2;
		frame.getContentPane().add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		btnDelete_1 = new JButton("Delete");
		GridBagConstraints gbc_btnDelete_1 = new GridBagConstraints();
		gbc_btnDelete_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete_1.gridx = 4;
		gbc_btnDelete_1.gridy = 2;
		frame.getContentPane().add(btnDelete_1, gbc_btnDelete_1);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane.setViewportView(textArea_1);
		
		
		
	}

	//Methods for each operations
	private void searchOp(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		if(!textField.getText().equals("")) {
			String clientmsg = "search "+ textField.getText()+ "\n";
			out.write(clientmsg);
			out.flush();
			String received=in.readLine();
			textArea_1.append(textField.getText()+": "+ received +"\n");	
		}else {
			textArea_1.append("There is no word entered\n");
		}
			
	}
	
	private void addOp(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		if(!textField_2.getText().equals("") && !textField_3.getText().equals("")) {
			
			if(!textField_2.getText().contains(";") && !textField_3.getText().contains(";") && !textField_3.getText().contains(" ") ) {
				String clientmsg = "add "+ textField_3.getText()+" "+textField_2.getText()+"\n";
				out.write(clientmsg);
				out.flush();
				String received=in.readLine();
				textArea_1.append( received +"\n");
			} else {
				textArea_1.append("';' usage is incompatible | there is space in Word area \n");
			}
		}else {
			textArea_1.append("One or more fields are empty\n");
		}
	}
	
	private void delOp(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		
		if(!textField_4.getText().equals("")) {
			int n = JOptionPane.showConfirmDialog(null, "Are you sure");
			if(n==0) {	
				String clientmsg = "delete "+ textField_4.getText()+ "\n";
				out.write(clientmsg);
				out.flush();
				String received=in.readLine();
				textArea_1.append(textField_4.getText()+" deleted\n"+ received +"\n");
			}
		}else {
			textArea_1.append("There is no word entered\n");
		}
		
	}
	
}