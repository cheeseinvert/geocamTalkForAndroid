package gov.nasa.arc.geocam.talk.bean;

import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import gov.nasa.arc.geocam.talk.service.ITalkServer;

/**
 * Intent identifiers for various django server actions.
 */
public enum TalkServerIntent {
	
	
	/** Intent to announce to activities that new messages are available in an {@link IMessageStore} object */
	INTENT_NEW_MESSAGES("gov.nasa.arc.geocam.talk.NEW_MESSAGE"),

	/** Intent to request that a {@link ITalkServer synchronize with the django server */	
	INTENT_SYNCHRONIZE("gov.nasa.arc.geocam.talk.syncrhonize"),

	/** Intent to store the C2DM Registration id to the django server */
	INTENT_STORE_C2DM_ID("gov.nasa.arc.geocam.talk.c2dm_id_store"),
	
	/** Intent to signal a pushed message to listening activities */
	INTENT_PUSHED_MESSAGE("gov.nasa.arc.geocam.talk.pushed_message"),
	
	/** Intent to alert current activity that authentication with the service has failed */
	INTENT_LOGIN_FAILED("gov.nasa.arc.geocam.talk.login_failed"),

	/** Intent to attempt manual login by an {@link ISiteAuth} object. */
	INTENT_LOGIN("gov.nasa.arc.geocam.talk.login"),
	
	/** The intent extra, message_id. */
	EXTRA_MESSAGE_ID("message_id"),
	
	/** The intent extra, registration_id. */
	EXTRA_REGISTRATION_ID("registration_id");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new talk server intent identifier from the enum.
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
