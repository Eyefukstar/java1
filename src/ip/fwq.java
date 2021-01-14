package ip;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Handler;

public class fwq {
		private ServerSocket serversocket;
		private ByteBuffer buff=ByteBuffer.allocate(2048);
		private Selector serector;
		private Map<String,String> set=new HashMap<>();
		private Map<String,String> name=new HashMap<>();
		public fwq(int pon) throws IOException {
			ServerSocketChannel serversocketchannel=ServerSocketChannel.open();
			serversocketchannel.configureBlocking(false);
			serversocket=serversocketchannel.socket();
			serversocket.bind(new InetSocketAddress(pon));
			serector.open();
			serversocketchannel.register(serector,SelectionKey.OP_ACCEPT);
			System.out.println("server start on " + pon);
		}
		public void listen() throws IOException {
			while(true)
			{
				serector.select();
				Iterator<SelectionKey> iteaIterable=serector.selectedKeys().iterator();
				while(iteaIterable.hasNext())
				{
					SelectionKey key=iteaIterable.next();
					iteaIterable.remove();
					handlekey(key);
				}
				serector.selectedKeys().clear();
			}
		}
		private void handlekey(SelectionKey key) throws IOException {
			ServerSocketChannel server;
			SocketChannel socket;
			if(key.isAcceptable())
			{
				server=((ServerSocketChannel)key.channel());
				socket=server.accept();
				socket.configureBlocking(false);
				socket.register(serector,SelectionKey.OP_READ);
				System.out.println("receive connection from"+socket.getRemoteAddress());
				boardMsg("当前有" + (name.size() + 1) + "人\n", null);
				Write(socket,"欢迎来到聊天室，请输入你的昵称！");
				key.interestOps(SelectionKey.OP_ACCEPT);
			}else if(key.isReadable())
			{
				 
			}
		}
		private void boardMsg(String string, Object object) {
			// TODO Auto-generated method stub
			
		}
		private void Write(SocketChannel socket, String string) {
			// TODO Auto-generated method stub
			
		}
}
