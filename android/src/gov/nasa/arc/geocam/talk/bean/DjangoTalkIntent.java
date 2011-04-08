package gov.nasa.arc.geocam.talk.bean;

public enum DjangoTalkIntent {
	// sent by djangoTalk object when new messages are stored into the db	
	NEW_MESSAGES("new_messages"),
	
	// sent to djantoTalk object when a synchronization is needed
	SYNCHRONIZE("syncrhonize");
	
	private String action;
	
	private DjangoTalkIntent(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;		
	}
	
	@Override
	public String toString()
	{
		return action;
	}
}
