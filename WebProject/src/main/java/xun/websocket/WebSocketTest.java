package xun.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

//@ServerEndpoint("/websocket")
//public class WebSocketTest {
@ServerEndpoint("/websocket/{user}")
@Component
public class WebSocketTest {
	private static int onlineCount = 0;
	private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
	private static Map<String, WebSocketTest> map = new HashMap<String, WebSocketTest>();
	private Session session;

	@OnOpen
	public void onOpen(Session session,@PathParam(value = "user") String user) {
		this.session = session;
		System.out.println("如果你能接到就太好了 : "+user);
//		if (user.equals("sa")) {
//			webSocketSet.add(this); // 加入set中			
//		}
		
//		webSocketSet.add(this); // 加入set中
		if(user.equals("SM")) {
			map.put("SM",this);
		}
		if (!map.containsKey(user)) {
			map.put(user, this);			
		}
		addOnlineCount(); // 線上數加1
		System.out.println("有新連線加入！當前線上人數為" + getOnlineCount());
		System.out.println(webSocketSet.toString());
		System.out.println(map);
	}

	/**
	 * 連線關閉呼叫的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 從set中刪除
		subOnlineCount(); // 線上數減1
		System.out.println("有一連線關閉！當前線上人數為" + getOnlineCount());
	}

	/**
	 * 收到客戶端訊息後呼叫的方法
	 * 
	 * @param message 客戶端傳送過來的訊息
	 * @param session 可選的引數
	 */
	@OnMessage
	public void onMessage(String message, Session session,@PathParam(value = "user") String user) {
		System.out.println("來自客戶端的訊息:" + message);
		System.out.println(webSocketSet.toString());
//群發訊息
//		for (WebSocketTest item : webSocketSet) {
//			try {
//				item.sendMessage(message);
//				System.out.println(item.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//				continue;
//			}
//		}
		try {
			map.get(user).sendMessage(message);
			map.get("SM").sendMessage(message);
			System.out.println(map.get(user));
			System.out.println(map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 發生錯誤時呼叫
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("發生錯誤");
		error.printStackTrace();
	}

	/**
	 * 這個方法與上面幾個方法不一樣。沒有用註解，是根據自己需要新增的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
//this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketTest.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketTest.onlineCount--;
	}
}