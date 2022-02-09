package B208.mag_jang.domain;

import org.springframework.web.socket.WebSocketSession;

import java.util.*;


public class ChatRoomDTO {
    private String roomId;
    private String name;

    private List<String> nicknames;

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void addNickname(String nickname){
        if(this.nicknames==null) nicknames = new ArrayList<>();
        this.nicknames.add(nickname);
    }

    public static ChatRoomDTO create(String name){
        ChatRoomDTO room = new ChatRoomDTO();
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ChatRoomDTO{" +
                "roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
