package hello;

public class Greeting {

	private final long id;
	private final String content;
	private final int size;

	public Greeting(long id, String content, int size) {
		this.id = id;
		this.content = content;
		this.size = size;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public int getSize() {
		return size;
	}
}
