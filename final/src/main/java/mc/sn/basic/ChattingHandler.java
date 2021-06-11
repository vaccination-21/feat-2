package mc.sn.basic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping("/chatting")
public class ChattingHandler extends TextWebSocketHandler{
    //���� ����Ʈ
    private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

    private static Logger logger = LoggerFactory.getLogger(ChattingHandler.class);

    //Ŭ���̾�Ʈ�� ���� �Ǿ��� �� ����
  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        logger.info("{} �����", session.getId()); 
    }

    //Ŭ���̾�Ʈ�� ������ ������ �޽����� �������� �� ����
   
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("{}�� ����", session.getId());
        logger.info("{}�� ����", message.getPayload());
        //��� �������� �޼��� ���
        for(WebSocketSession sess : sessionList){
            sess.sendMessage(new TextMessage(message.getPayload()));
        }
    }
    //Ŭ���̾�Ʈ ������ ������ �� ����
    
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        logger.info("{} ���� ����.", session.getId());
    }

}