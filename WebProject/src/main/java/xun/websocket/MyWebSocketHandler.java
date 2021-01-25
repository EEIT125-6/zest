package xun.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.time.LocalTime;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler{
	
    @Override
    protected void handleTextMessage(
    		WebSocketSession session
    		,TextMessage message
    		)
            throws Exception {

    	session.sendMessage(new TextMessage(message.getPayload()));
        String clientMessage = message.getPayload();

        System.out.println(clientMessage);
        System.out.println("------------------");
        System.out.println(message.getPayload());
        
        if (clientMessage.startsWith("hello") || clientMessage.startsWith("greet")) {
            session.sendMessage(new TextMessage("Hello there!"));
        } else if (clientMessage.startsWith("time")) {
            LocalTime currentTime = LocalTime.now();
            session.sendMessage(new TextMessage(currentTime.toString()));
        } else {

            session.sendMessage(new TextMessage("Unknown command"));
        }
    }
}
