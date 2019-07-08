package com.hy.project.model;

public class MessageChatDatabase {
    private String userIdChat;
    private String userNameChat;
    private String avatarURLChat;
    private String roomIdChat;
    private String textChat;

    public MessageChatDatabase() {

    }

    public MessageChatDatabase(String userIdChat, String userNameChat, String avatarURLChat, String roomIdChat, String textChat) {
        this.userIdChat = userIdChat;
        this.userNameChat = userNameChat;
        this.avatarURLChat = avatarURLChat;
        this.roomIdChat = roomIdChat;
        this.textChat = textChat;
    }

    public String getUserIdChat() {
        return userIdChat;
    }

    public void setUserIdChat(String userIdChat) {
        this.userIdChat = userIdChat;
    }

    public String getUserNameChat() {
        return userNameChat;
    }

    public void setUserNameChat(String userNameChat) {
        this.userNameChat = userNameChat;
    }

    public String getAvatarURLChat() {
        return avatarURLChat;
    }

    public void setAvatarURLChat(String avatarURLChat) {
        this.avatarURLChat = avatarURLChat;
    }

    public String getRoomIdChat() {
        return roomIdChat;
    }

    public void setRoomIdChat(String roomIdChat) {
        this.roomIdChat = roomIdChat;
    }

    public String getTextChat() {
        return textChat;
    }

    public void setTextChat(String textChat) {
        this.textChat = textChat;
    }
}
