package gov.nasa.arc.geocam.talk.bean;

// TODO: Auto-generated Javadoc
/**
 * The Enum TalkServerIntent.
 */
public enum TalkServerIntent {
	
	
	/** The INTENT_new_messages. */
	INTENT_NEW_MESSAGES("gov.nasa.arc.geocam.talk.NEW_MESSAGE"),

	/** The INTENT_synchronize. */
	INTENT_SYNCHRONIZE("gov.nasa.arc.geocam.talk.syncrhonize"),

	/** The INTENT_store_c2dm_id. */
	INTENT_STORE_C2DM_ID("gov.nasa.arc.geocam.talk.c2dm_id_store"),
	
	/** The INTENT_pushed_message. */
	INTENT_PUSHED_MESSAGE("gov.nasa.arc.geocam.talk.pushed_message"),
	
	/** The INTENT_login_failed. */
	INTENT_LOGIN_FAILED("gov.nasa.arc.geocam.talk.login_failed"),

	/** The INTENT_login. */
	INTENT_LOGIN("gov.nasa.arc.geocam.talk.login"),
	
	/** The EXTRA message_id. */
	EXTRA_MESSAGE_ID("message_id"),
	
	/** The EXTRa_registration_id. */
	EXTRA_REGISTRATION_ID("registration_id");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new talk server intent.
	 *
	 * @param value the value
	 */
	private TalkServerIntent(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return value;
	}
}
