package state.test;

import state.core.SingleArcTransition;
import state.core.StateMachine;
import state.core.StateMachineFactory;

public class Main {	
	private static final StateMachineFactory<Main,TestState,TestEventType,TestEvent> stateMachineFactory
	= new StateMachineFactory<Main,TestState,TestEventType,TestEvent>(TestState.NEW)
	// Transitions from NEW state
	.addTransition(TestState.NEW, TestState.STARTED,
			TestEventType.START, new StartTransition())
	.addTransition(TestState.STARTED, TestState.STOPED,
			TestEventType.STOP, new StopTransition())
	.installTopology();
	
	private final StateMachine<TestState, TestEventType, TestEvent>	stateMachine;

	public Main(){
		stateMachine=stateMachineFactory.make(this);
	}
	
	public static void main(String[] args){
		Main testStateMachine=new Main();
		System.out.println("currentState:\n"+testStateMachine.stateMachine.getCurrentState());
		
		TestEvent startEvent=new TestEvent(1, TestEventType.START);
		testStateMachine.stateMachine.doTransition(startEvent.getEventType(), startEvent);		
		System.out.println("currentState:\n"+testStateMachine.stateMachine.getCurrentState());
		
		TestEvent stopEvent=new TestEvent(1, TestEventType.STOP);
		testStateMachine.stateMachine.doTransition(stopEvent.getEventType(), stopEvent);		
		System.out.println("currentState:\n"+testStateMachine.stateMachine.getCurrentState());
	}
	
	
	
	private static final class StartTransition implements SingleArcTransition<Main, TestEvent> {
		public void transition(Main app, TestEvent event) {
			System.out.println("Action:\nstate change to STARTED");
		};
	}
	
	private static final class StopTransition implements SingleArcTransition<Main, TestEvent> {
		public void transition(Main app, TestEvent event) {
			System.out.println("Action:\nstate change to STOPED");
		};
	}
}



