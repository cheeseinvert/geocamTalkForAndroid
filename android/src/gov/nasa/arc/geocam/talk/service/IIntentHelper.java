package gov.nasa.arc.geocam.talk.service;

public interface IIntentHelper {
	void Synchronize();

	void BroadcastNewMessages();

	void RegisterC2dm(); // Register with google servers

	void StoreC2dmRegistrationId(String registrationId); // forward registration
															// on to our server
	void PushedMessage(String messageId);

	void StopServices();

	void UnregisterC2dm();
}
