
public class linkedList {
	
	private linkNode head = null;
	private linkNode tail = null;
	
	public void add(String data) {
		
		if (tail == null) {
			head = new linkNode(data, null);
			tail = head;
		} else {
			linkNode newNode = new linkNode(data, null);
			tail.setLink(newNode);
			tail = newNode;
		}
		
	}
	
	public boolean remove(String target) {
		linkNode cursor;
		linkNode precursor;
		
		for(precursor = null, cursor = head; cursor != null;
				precursor = cursor, cursor = cursor.getLink()) {
			
			if (cursor.getData().equals(target)) {
				if (precursor == null) {
					head = head.getLink();
				} else {
					precursor.setLink(cursor.getLink());
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean doublesTester() {
		linkNode sub = head;
		
		while (sub.getLink() != null)  
	    {  
	        // Starting from the next node  
	        linkNode ptr = sub.getLink();  
	        while (ptr != null)  
	        {  
	            // If some duplicate node is found  
	            if (sub.getData().equalsIgnoreCase(ptr.getData()))  
	            {  
	                return true;   
	            }  
	            ptr = ptr.getLink();  
	        }  
	        sub = sub.getLink();  
	    } 
		return false;
	}
	
	
	@Override
	public String toString() {
		
		String listInfo = "{ ";
		linkNode cursor = head;
		
		while (cursor != null) {
			listInfo += cursor.getData() + ((cursor.getLink() != null) ? " -> " : "");
			cursor = cursor.getLink();
		}
		
		listInfo += " }";
		
		return listInfo;
	}
	
}
