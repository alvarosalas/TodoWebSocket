package se.salas.yhc3l.postitWebsockets;

import javax.json.JsonArray;

public class Postit {
	private String image;
	private String title;
	private JsonArray desc;
	private int id;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JsonArray getDesc() {
		return desc;
	}

	public void setDesc(JsonArray desc) {
		this.desc = desc;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
