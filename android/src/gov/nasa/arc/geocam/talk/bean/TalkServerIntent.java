package gov.nasa.arc.geocam.talk.bean;

public enum TalkServerIntent {
	INTENT_NEW_MESSAGES("new_messages"),

	INTENT_SYNCHRONIZE("syncrhonize"),

	INTENT_C2DM_REGISTER("c2dm_register"),

	INTENT_STORE_C2DM_ID("c2dm_id_store"),
	
	EXTRA_MESSAGE_ID("message_id");

	private String value;

	private TalkServerIntent(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
