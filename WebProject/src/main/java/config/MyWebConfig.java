package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import xun.websocket.MyHandler;
import xun.websocket.MyWebSocketHandler;

@Configuration
@EnableWebSocket
@EnableWebMvc
public class MyWebConfig implements WebSocketConfigurer {
	
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
	
	
//  @Override
//  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//      registry.addHandler(new MyHandler(), "/myHandler")
//              .setAllowedOrigins("*");
//  }
	
    @Autowired
    private MyWebSocketHandler myWebSocketHandler;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler, "/socketHandler");
    }
    
  
}
