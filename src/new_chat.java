import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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


	public class new_chat extends Thread implements MessageListener{


		JFrame frame_chat;
		JPanel panel_chat;
		JPanel send_file;
		JButton send_but;
		JTextArea text;
		JTextField textarea;
		String receipient;
		ChatManager Manager;
		Chat chat;
		JScrollPane scrollar;
		String to = null,tos;
		new_chat link = null;

		public new_chat(String str){
			receipient = str;
			System.out.println("New_chat " + str);
		}

		public void run()
		{
			System.out.println("Run in New_chat ");
			frame_chat = new JFrame(receipient);
			panel_chat = new JPanel() ;
			send_file = new JPanel();

			send_but = new JButton("send a file");

//			These two lines are for send files Button.......
//			send_but.addActionListener(new sendfileto());
//			send_but.setActionCommand(connection.getRoster().getPresence(receipient).getFrom());
			
			text = new JTextArea(15,30);
			text.setLineWrap(true);
			text.setEditable(false);
			textarea = new JTextField("",10);
			scrollar = new JScrollPane(text);

			scrollar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollar.setAutoscrolls(true);
			scrollar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			panel_chat.add (scrollar);
			frame_chat.getContentPane().add(BorderLayout.NORTH, send_file);
			frame_chat.getContentPane().add(BorderLayout. CENTER, panel_chat);
			frame_chat.getContentPane().add(BorderLayout. SOUTH,textarea);
			send_file.add(send_but);
			textarea.requestFocus();

			System.out.println("New_chat in middle of run " + receipient);
			textarea.setActionCommand(receipient);
			
			textarea.addActionListener(new ActionListener(){
				
				public void actionPerformed (ActionEvent event){

					System.out.println("Actionperformed in new_chat: ");
					String msg = textarea.getText();
					textarea.setText("");
					text.setForeground(Color.red);
					text.append("YOU:  " + msg + "\n" );
					String receipient = event.getActionCommand();
					try {
						sendMessage(msg, receipient);
					} catch (XMPPException ex) {}

				}
			});
						
			frame_chat.setSize(350,300);
			frame_chat.setVisible(true);
			chat = ley.connection.getChatManager().createChat(receipient, this);
		}


		public void sendMessage(String message, String tos) throws XMPPException
		{
			System.out.print("in send messgae");
			chat.sendMessage(message);
			scrollar.getCorner(ScrollPaneConstants.LOWER_LEFT_CORNER);
		}


		public void processMessage(Chat chat, Message msg) {
			System.out.println(Thread.currentThread());
			System.out.print("in the process message of newchat");
			if(msg.getType() == Message.Type.chat)
				System.out.println(chat.getParticipant() + " says: " + msg.getBody());
			scrollar.getCorner(ScrollPaneConstants.LOWER_LEFT_CORNER);
			text.setForeground(Color.BLUE);
			text.append(chat.getParticipant() + ":  "  + msg.getBody() + "\n");

		}
	}
