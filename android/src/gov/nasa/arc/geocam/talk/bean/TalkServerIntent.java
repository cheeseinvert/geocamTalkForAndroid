package gov.nasa.arc.geocam.talk.bean;

public enum TalkServerIntent {
	
	
	INTENT_NEW_MESSAGES("gov.nasa.arc.geocam.talk.NEW_MESSAGE"),

	INTENT_SYNCHRONIZE("gov.nasa.arc.geocam.talk.syncrhonize"),

	INTENT_STORE_C2DM_ID("gov.nasa.arc.geocam.talk.c2dm_id_store"),
	
	INTENT_PUSHED_MESSAGE("gov.nasa.arc.geocam.talk.pushed_message"),
	
	EXTRA_MESSAGE_ID("message_id"),
	
	EXTRA_REGISTRATION_ID("registration_id");

	private String value;

	private TalkServerIntent(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
