package B208.mag_jang.domain;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameMap {
    private Map<String, GameDTO> games = new HashMap<>();

    public void addPlayer(String roomId, String nickname) {
        games.put(roomId, new GameDTO(roomId));
        games.get(roomId).addPlayer(nickname);
    }

    public void removeNickname(String roomId, String writer) {
        if(games.get(roomId)==null) {
            System.out.println("RoomMap : " + writer + "의 quit 요청, " + roomId + "가 null 입니다.");
            return;
        }
//        games.get(roomId).removeNickname(writer);
    }
    public GameDTO getGame(String roomId){
        return games.get(roomId);
    }
}
