package moodishclient.client.callbacks;


import moodishclient.client.views.viewComponents.ConversationView;

public interface StartScreenCallback
{

    public void validateUserNickname();

    public void showErrorMessage(String messageTitle, String errorMessage);

    public void logoutUser();

    public void confirmConversationClose(ConversationView conversationView, String confirmMessage);

    public void sendMessage(String moodishMessage);

    public void sendFriendshipRequest(String userNickname);
    
    public void sendUnfriendshipRequest(String userNickname);
}
