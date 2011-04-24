package gov.nasa.arc.geocam.talk.service;

// TODO: Auto-generated Javadoc
/**
 * The Interface IIntentHelper.
 */
public interface IIntentHelper {
	
	/**
	 * Synchronize.
	 */
	void Synchronize();

	/**
	 * Broadcast new messages.
	 */
	void BroadcastNewMessages();

	/**
	 * Register c2dm.
	 */
	void RegisterC2dm(); // Register with google servers

	/**
	 * Unregister c2dm.
	 */
	void UnregisterC2dm();

	/**
	 * Store c2dm registration id.
	 *
	 * @param registrationId the registration id
	 */
	void StoreC2dmRegistrationId(String registrationId); // forward registration
															// on to our server
	/**
															 * Pushed message.
															 *
															 * @param messageId the message id
															 */
															void PushedMessage(String messageId);

	/**
	 * Login.
	 */
	void Login(); // Attempt to login to the django server

	/**
	 * Login failed.
	 */
	void LoginFailed(); // alert listeners of a failed login

	/**
	 * Stop services.
	 */
	void StopServices();

}
