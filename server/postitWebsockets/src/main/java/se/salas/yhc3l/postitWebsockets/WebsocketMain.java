package se.salas.yhc3l.postitWebsockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocketmain", encoders = { PostitEncoder.class }, decoders = { PostitDecoder.class })
public class WebsocketMain {
	private static final List<Postit> postits = Collections
			.synchronizedList(new ArrayList<Postit>());
	private static final Set<Session> sessions = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void handleOpen(Session session) throws IOException, EncodeException {
		System.out.println("Client is now connected");
		sessions.add(session);
		for (Postit post : postits) {
			session.getBasicRemote().sendObject(post);
		}
	}

	@OnClose
	public void handleClose(Session session) throws IOException,
			EncodeException {
		System.out.println("Client is now disconnected");
		sessions.remove(session);
	}

	@OnError
	public void handleError(Throwable t) {
		System.out.println("Error! " + t);
		t.printStackTrace();
	}

	@OnMessage
	public void handleMessage(Postit postit, Session session)
			throws IOException, EncodeException {
		String type = postit.getType();
		System.out.println(postit.getType());
		if (type.equals("add")) {
			System.out.println("DESC SIZE " + postit.getDesc().size());
			if (postit.getDesc().size() > 1) {
				iterateOverList(postit);
			}
			postits.add(postit);
			for (Session openSessions : sessions) {
				openSessions.getBasicRemote().sendObject(postit);
			}

		} else if (type.equals("remove")) {
			iterateOverList(postit);
		} else if (type.equals("editTitle")) {
			iterateOverList(postit);
			postits.add(postit);
			for (Session openSessions : sessions) {
				openSessions.getBasicRemote().sendObject(postit);
			}
		} else if (type.equals("editTask")) {
			iterateOverList(postit);
			postits.add(postit);
			for (Session openSessions : sessions) {
				openSessions.getBasicRemote().sendObject(postit);
			}

		}
	}

	public void iterateOverList(Postit postit) {
		Iterator<Postit> iterator = postits.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId() == postit.getId()) {
				iterator.remove();
				continue;
			}
		}
	}
}
