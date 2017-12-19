package state.test;

import dispatcher.core.AbstractEvent;

public class TestEvent extends AbstractEvent<TestEventType>{
	int id;
	TestEventType type;
	public TestEvent(int id , TestEventType type) {
		super(type);
		this.id=id;
		this.type=type;
	}
	
	public int getId(){
		return id;
	}
	
	public TestEventType getEventType(){
		return this.type;
	}

	

}
