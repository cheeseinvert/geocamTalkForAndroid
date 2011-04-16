package gov.nasa.arc.geocam.talk.service;

public interface IIntentHelper {
	void Synchronize();

	void BroadcastNewMessages();

	void RegisterC2dm(); // Register with google servers

	void UnregisterC2dm();

	void StoreC2dmRegistrationId(String registrationId); // forward registration
															// on to our server
	void PushedMessage(String messageId);

	void Login(); // Attempt to login to the django server

	void LoginFailed(); // alert listeners of a failed login

	void StopServices();

}
