package gov.nasa.arc.geocam.talk.bean;

public enum TalkServerIntent {
	NEW_MESSAGES("new_messages"),

	SYNCHRONIZE("syncrhonize"),

	C2DM_REGISTER("c2dm_register"),

	STORE_C2DM_ID("c2dm_id_store");

	private String action;

	private TalkServerIntent(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	@Override
	public String toString() {
		return action;
	}
}
