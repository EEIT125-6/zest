package xun.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler{
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	      session.sendMessage(new TextMessage("echo: "+message.getPayload()));
	  }
}

