package B208.mag_jang.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameDTO {
    private String gameId;
    private List<Player> playerList; // 플레이어 리스트
    //직업리스트도 있어야됨
    private DealDTO deal;

    private int round; // 라운드
    private int turn;  // 턴

    //게임 초기 생성
    public GameDTO(String roomId) {
        this.gameId = roomId;
        this.playerList = new ArrayList<Player>();
        this.round = 1;
        this.turn = 1;
    }
//    public void setTurn(){
//        this.turn = this.playerList.size();
//    }

    //인원추가
    public void addPlayer(Player player){
        this.playerList.add(player);
    }


    //거래생성
    public void createDeal(){
        this.deal = new DealDTO();
    }
    //체결여부확인
    public boolean isVoteFinished(){
        return deal.isVoteOK();
    }
    //거래성사여부
    public boolean isDealOk(){
        return deal.isDealOk();
    }
    //거래분배금액반환
    public Map<String,Integer> getFinalMoney(){
        return this.deal.calcMoney(this.round);
    }

}
