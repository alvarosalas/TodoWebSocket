package se.salas.yhc3l.postitWebsockets;

import java.io.IOException;
import java.io.Writer;

import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.spi.JsonProvider;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class PostitEncoder implements Encoder.TextStream<Postit> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public void encode(Postit postit, Writer writer) throws EncodeException, IOException{
		JsonProvider provider = JsonProvider.provider();
		JsonObject jsonPostit = provider.createObjectBuilder().add("BGcolor", postit.getImage()).add("text", postit.getTitle()).add("descriptions", postit.getDesc()).add("id", postit.getId()).build();
		try(JsonWriter jsonWriter = provider.createWriter(writer)){
			jsonWriter.write(jsonPostit);
			System.out.println("Skickar jsonObject till AVA Todo app " + jsonPostit);
		}
	}

}
