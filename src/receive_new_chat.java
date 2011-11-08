import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


	public class receive_new_chat extends Thread implements MessageListener{

		JFrame frame_chat;
		JPanel panel_chat;
		JTextArea text;
		JTextField textarea;
		String receipient;
		ChatManager Manager;
		Chat chat;
		String to = null,tos;
		new_chat link = null;
		int count=0;
		String print = "";
		String signal;

		public receive_new_chat(String msg, Chat chat){
			
			signal = new String();
			print += msg + "\n";
			receipient = chat.getParticipant();
			this.chat = chat;
			start();
			chat.addMessageListener(this);
		}

		public void run(){

			dabba();
			text.append(chat.getParticipant() + ":  "  + print + "\n");
		}

		public void dabba(){

			frame_chat = new JFrame(receipient);
			panel_chat = new JPanel() ;

			text = new JTextArea(15,30);
			text.setLineWrap(true);
			text.setEditable(false);
			textarea = new JTextField("",10);
			JScrollPane scrollar = new JScrollPane(text);

			scrollar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			panel_chat.add (scrollar);
			frame_chat.getContentPane().add(BorderLayout. CENTER, panel_chat);
			frame_chat.getContentPane().add(BorderLayout. SOUTH,textarea);
			textarea.requestFocus();

			textarea.setActionCommand(receipient);
			textarea.addActionListener(new sendchat());
			frame_chat.setSize(350,300);
			frame_chat.setVisible(true);
			signal = "BOX_OPEN";
			
			frame_chat.addWindowListener(new WindowAdapter(){
				  public void windowClosing(WindowEvent we){
//			the chat box is closed, so we need  to set the msg to BOX_IS_CLOSED....
					  signal = "BOX_CLOSED"; 
					  
				  }
				  });


		}

		public void processMessage(Chat chat, Message msg) {
			if(msg.getBody().equalsIgnoreCase("") || signal.equals("BOX_CLOSED"))
				dabba();
			System.out.print("in the process message of receive");

			if((msg.getType() == Message.Type.chat) && msg.getBody() != null){
				System.out.println(chat.getParticipant() + " says: " + msg.getBody());
				text.append(chat.getParticipant() + ":  "  + msg.getBody() + "\n");
			}
		}

		private class sendchat implements ActionListener{

			public void actionPerformed (ActionEvent event){
				String msg = textarea.getText();
				textarea.setText("");
				text.setForeground(Color.red);
				text.append("YOU: " + msg + "\n" );
				try {
					chat.sendMessage(msg);
				} catch (XMPPException ex) {}

			}
		}

	}