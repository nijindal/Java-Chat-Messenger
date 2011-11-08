import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;


public class create_listener_threads implements MessageListener{

		XMPPConnection connection;
		Roster roster;
		Collection<RosterEntry> list;
		
		public create_listener_threads(){
		
			connection = ley.connection;
			roster = connection.getRoster();
			list = roster.getEntries();
			
			for(RosterEntry r:list)
			{
				if(roster.getPresence(r.getUser()).isAvailable())
				{
					String buddy = r.getUser();
					ley.connection.getChatManager().createChat(buddy, this);
					///keep the end open for receiving chats from other buddies.....
				}
			}
		}

		public void processMessage(Chat arg, Message msg) {

			if(msg.getType() == Message.Type.chat)
			{
				new receive_new_chat(msg.getBody(),arg);
				arg.removeMessageListener(this);
			}
		}

	}
