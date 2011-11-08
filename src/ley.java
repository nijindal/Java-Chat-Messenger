
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;


@SuppressWarnings("serial")
public class ley extends JFrame{

	public static JFrame frame;
//	JPanel panel;
//	JTextField u_name,field1,field2,field3;
//	JPasswordField pass;
	JButton connect;
	public static XMPPConnection connection;
	Collection<RosterEntry> list;
	public static ArrayList<String> buttons;

	public ley()
	{
		set_layout_login_page();
	}


	void set_layout_login_page(){

		frame = new JFrame("LOGIN");

		final JPasswordField pass;
		final JTextField u_name;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);

		JPanel panel1 = new JPanel();
		final JPanel main = new JPanel();
		JPanel panel2 = new JPanel();
		JButton login = new JButton();

		Container pane = frame.getContentPane();

		pane.setLayout(new BorderLayout());
		main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

		panel1.add( new JLabel("     USERNAME    "));
		u_name = new JTextField("",15);
		panel1.add(u_name);
		panel1.add( new JLabel("      "));

		main.add(new JLabel("   "));
		main.add(panel1);
		main.add(new JLabel("   "));


		panel2.add(new JLabel("     PASSWORD   "));
		pass = new JPasswordField("",15);
		
		pass.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent event) {

				
				try {
					String uname =  u_name.getText();
					if(uname.indexOf("@gmail.com")<0)
						uname  += "@gmail.com";
					String pas =  pass.getText();
					main.setVisible(false);
					frame.removeAll();
					frame.setTitle("BUDDY LIST");
					new retrieve_roster(uname,pas);
				} catch (XMPPException ex) {
					Logger.getLogger(ley.class.getName()).log(Level.SEVERE, null, ex);
				} 			
			}
					
		});
		
		panel2.add(pass);
		panel2.add( new JLabel("      "));
		main.add(panel2);

		main.add(new JLabel("   "));

		login = new JButton("Login");

		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
				try {
					String uname =  u_name.getText();
					if(uname.indexOf("@gmail.com")<0)
						uname  += "@gmail.com";
					String pas =  pass.getText();
					
					main.setVisible(false);
					frame.removeAll();
					frame.setTitle("BUDDY LIST");
					new retrieve_roster(uname,pas);
				} catch (XMPPException ex) {
					Logger.getLogger(ley.class.getName()).log(Level.SEVERE, null, ex);
				}		
			}
					
		});
		
		main.add(login);
		pane.add(main,BorderLayout.NORTH);	

		frame.setVisible(true);
	}

	
	public static void main(String[] args){

		new ley();

	}


	
//Below this ...two classes are file transfer related........Not implemented properly....

	public class receive_file implements Runnable{

		public receive_file(){

			new Thread(this).start();
		}
		@Override
		public void run() {
			FileTransferManager manager = new FileTransferManager(connection);
			manager.addFileTransferListener(new FileTransferListener() {
				public void fileTransferRequest(FileTransferRequest request) {
					try {
						int ret = JOptionPane.showConfirmDialog(null, request.getRequestor() + "sending a file named" + request.getFileName());
						System.out.println("returned value" + ret);
						if(ret == 0){
							System.out.println("returned value" + ret);
							IncomingFileTransfer transfer = request.accept();
							transfer.recieveFile(new File("received_file"));
							System.out.println("File Received complete");}
					} catch (XMPPException e) {
						e.printStackTrace();
					}
				}
			});	
		}
	}

	


	public class sendfileto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			String file_path = JOptionPane.showInputDialog("enter the path of file");

			String receiver = e.getActionCommand();
			System.out.println("in sendfileto" + receiver);
			FileTransferManager mgr = new FileTransferManager(connection);
			OutgoingFileTransfer transfer = mgr.createOutgoingFileTransfer(receiver);
			File file = new File(file_path);
			try {
				transfer.sendFile(file,"file");

				while(!transfer.isDone())
				{
					System.out.println(transfer.getProgress() + " is done!");
				}
				System.out.println("loop broken"+transfer.getProgress() + " is done!");
			} catch (Exception ex) {
				Logger.getLogger(ley.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}

}

