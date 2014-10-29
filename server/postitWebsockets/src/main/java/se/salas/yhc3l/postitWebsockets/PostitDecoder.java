package se.salas.yhc3l.postitWebsockets;

import java.io.IOException;
import java.io.Reader;

import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class PostitDecoder implements Decoder.TextStream<Postit> {

	@Override
	public Postit decode(Reader reader) throws DecodeException, IOException {
		JsonProvider provider = JsonProvider.provider();
		JsonReader jsonReader = provider.createReader(reader);
		JsonObject jsonPostit = jsonReader.readObject();
		Postit postit = new Postit();
		postit.setId(jsonPostit.getInt("id"));
		postit.setDesc(jsonPostit.getJsonArray("descriptions"));
		postit.setImage(jsonPostit.getString("BGcolor"));
		postit.setTitle(jsonPostit.getString("text"));
		postit.setType(jsonPostit.getString("type"));
		return postit;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

}
