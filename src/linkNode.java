
public class linkNode {

	private String data;
	private linkNode link;
	
	public linkNode(String data, linkNode link) {
		this.data = data;
		this.link = link;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public linkNode getLink() {
		return link;
	}
	
	public void setLink(linkNode link) {
		this.link = link;
	}
	
	
	public void addNodeAfter(String data) {
		link = new linkNode(data, link);
	}
	
	public void removeNodeAfter() {
		link = link.link;
	}
	
	public static void print(linkNode head) {
		
		
	
	}
	
}
