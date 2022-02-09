package B208.mag_jang.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealDTO {
    private Map<String,Boolean> vote; //투표리스트
    private Map<String,Integer> dealAmount; //분배금액 리스트
    private boolean isDealSuccess;
    private final int[] roundMoney = {200,300,400};
    //생성자
    public DealDTO() {
        this.vote = new HashMap<>();
        this.dealAmount = new HashMap<>();
        this.isDealSuccess = false;
    }
    //분배금액 값넣기
    public void dealAmountCheck(Map<String,Integer> deal){
        this.dealAmount = deal;
    }

    //투표리스트값넣기
    public void addVote(String nickname,boolean isVote) {
        this.vote.put(nickname, isVote);
    }
    //체결여부확인 - 투표 다했니?
    public boolean isVoteOK(){
        if(this.vote.size()==this.dealAmount.size()){
            return true;
        }    
        return false;
    }
    //거래가 체결됐니?
    public boolean isDealOk(){
        for (String nickname:vote.keySet()) {
            if(vote.get(nickname)==false){
                this.isDealSuccess=false;
                return isDealSuccess;//거래파토
            }
        }
        this.isDealSuccess=true;
        return isDealSuccess; //거래성사
    }
    //분배금액계산
    public Map<String,Integer> calcMoney(Integer round){
        //거래성사시
        if(isDealSuccess){
            return dealAmount;
        }else{
        //거래파토시
            List<String> patoList = new ArrayList<>();
            //파토가 한명이면 그사람만 라운드별 수당
            for (String nickname:vote.keySet()) {
                if(!vote.get(nickname)){
                    patoList.add(nickname);
                }
                dealAmount.put(nickname,0);
            }

            if(patoList.size()==1){
                dealAmount.put(patoList.get(0),roundMoney[round]);
            }else{
                for (String nickname:patoList) {
                    dealAmount.put(nickname,roundMoney[round]*-1);
                }
            }
            //파토가 2명이상이면 그 사람들다 라운드별 패널티
            return dealAmount;
        }
    }

}
