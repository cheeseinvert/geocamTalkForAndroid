package gov.nasa.arc.geocam.talk.bean;

// TODO: Auto-generated Javadoc
/**
 * The Enum TalkServerIntent.
 */
public enum TalkServerIntent {
	
	
	/** The INTEN t_ ne w_ messages. */
	INTENT_NEW_MESSAGES("gov.nasa.arc.geocam.talk.NEW_MESSAGE"),

	/** The INTEN t_ synchronize. */
	INTENT_SYNCHRONIZE("gov.nasa.arc.geocam.talk.syncrhonize"),

	/** The INTEN t_ stor e_ c2 d m_ id. */
	INTENT_STORE_C2DM_ID("gov.nasa.arc.geocam.talk.c2dm_id_store"),
	
	/** The INTEN t_ pushe d_ message. */
	INTENT_PUSHED_MESSAGE("gov.nasa.arc.geocam.talk.pushed_message"),
	
	/** The INTEN t_ logi n_ failed. */
	INTENT_LOGIN_FAILED("gov.nasa.arc.geocam.talk.login_failed"),

	/** The INTEN t_ login. */
	INTENT_LOGIN("gov.nasa.arc.geocam.talk.login"),
	
	/** The EXTR a_ messag e_ id. */
	EXTRA_MESSAGE_ID("message_id"),
	
	/** The EXTR a_ registratio n_ id. */
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
