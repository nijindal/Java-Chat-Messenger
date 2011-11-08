import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;


public class add_to_list_refresh implements MessageListener{

		XMPPConnection connection;
		public add_to_list_refresh(String newcomer){
			connection = ley.connection;
			System.out.println("in add to list refresh");
			connection.getChatManager().createChat(newcomer, this);
		}

		@Override
		public void processMessage(Chat arg, Message msg) {

			if(msg.getType() == Message.Type.chat)
			{
				new receive_new_chat(msg.getBody(),arg);
				arg.removeMessageListener(this);
			}
		}
	}