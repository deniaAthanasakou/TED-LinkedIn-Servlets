package JavaFiles;

public class KObject {
	
	private Object object;
	private float distance;

	public KObject(Object object, float distance) {
		this.object = object;
		this.distance = distance;
	}

	public Object getObject() {
		return object;
	}

	public float getDistance() {
		return distance;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
}
