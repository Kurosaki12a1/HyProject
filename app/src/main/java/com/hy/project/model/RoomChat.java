package com.hy.project.model;

public class RoomChat {
    private String roomName;
    private String roomId;
    private String numberPeopleJoin;

    public RoomChat(String roomName, String roomId, String numberPeopleJoin) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.numberPeopleJoin = numberPeopleJoin;

    }

    public RoomChat(){

    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getNumberPeopleJoin() {
        return numberPeopleJoin;
    }

    public void setNumberPeopleJoin(String numberPeopleJoin) {
        this.numberPeopleJoin = numberPeopleJoin;
    }
}
