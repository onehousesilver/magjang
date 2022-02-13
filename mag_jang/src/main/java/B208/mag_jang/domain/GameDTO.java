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
        this.deal = new DealDTO();
    }
//    public void setTurn(){
//        this.turn = this.playerList.size();
//    }

    //인원추가
    public void addPlayer(Player player){
        this.playerList.add(player);
    }

    public int  getPlayerListSize() {
        return playerList.size();
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

    public void addPlayer(String player) {
        playerList.add(new Player(player));
    }

    public void initGame(int seedMoney) {
        for(int i=0; i<playerList.size(); i++){
            playerList.get(i).setMoney(seedMoney);
//            playerList.get(i).setJobs(jobs[i]);
        }
    }
    public List<Player> initJobs(String[][] jobs){
        for(int i=0; i<playerList.size(); i++){
            playerList.get(i).setJobs(jobs[i]);
        }
        return playerList;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public DealDTO getDeal() {
        return deal;
    }

    public void setDeal(DealDTO deal) {
        this.deal = deal;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "gameId='" + gameId + '\'' +
                ", playerList=" + playerList +
                ", deal=" + deal +
                ", round=" + round +
                ", turn=" + turn +
                '}';
    }
}
