package B208.mag_jang.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String NickName;
    private String email;
    private Integer rankPoint;
    private Integer winAmount;
    private Integer gangAmount;
    private Integer proGangAmount;
    private Integer lastGenRoom;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRankPoint() {
        return rankPoint;
    }

    public void setRankPoint(Integer rankPoint) {
        this.rankPoint = rankPoint;
    }

    public Integer getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Integer winAmount) {
        this.winAmount = winAmount;
    }

    public Integer getGangAmount() {
        return gangAmount;
    }

    public void setGangAmount(Integer gangAmount) {
        this.gangAmount = gangAmount;
    }

    public Integer getProGangAmount() {
        return proGangAmount;
    }

    public void setProGangAmount(Integer proGangAmount) {
        this.proGangAmount = proGangAmount;
    }

    public Integer getLastGenRoom() {
        return lastGenRoom;
    }

    public void setLastGenRoom(Integer lastGenRoom) {
        this.lastGenRoom = lastGenRoom;
    }
}
