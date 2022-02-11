package B208.mag_jang.service;

import B208.mag_jang.domain.GameDTO;
import B208.mag_jang.domain.GameMap;
import B208.mag_jang.domain.Player;
import B208.mag_jang.domain.RoomMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private final int START_MONEY = 2000;
    private final String[] jobNames = {"언변", "창고", "선박", "로비", "정보", "인맥"};
    private final RoomMap roomMap;
    private final GameMap gameMap;

    // GameDTO 생성 후 GameMap<String roomId, GameDTO gameDTO>에 연결
    // roomMap의 key 삭제
    //
    public void gameStart(String writer, String roomId) {
        for(String player : roomMap.getNicknames(roomId)){
            gameMap.addPlayer(roomId, player);
        }

        gameMap.getGame(roomId).initGame(START_MONEY);
    }
    public List<Player> getNextJobs(String roomId){
        String[][] jobs = new String[gameMap.getGame(roomId).getPlayerListSize()][2];
        return gameMap.getGame(roomId).initJobs(initJobs(jobs));
    }

    // 모든 플레이어에게 능력을 두 개씩 부여하는 메서드
    // 규칙 1. 한 플레이어에게 동일한 능력 두 개를 줄 수 없음
    // 규칙 2. 완전히 겹치는 플레이어가 생길 수 있음
    // 규칙 3. 한 능력을 플레이어 수 (4, 5, 6) 당 (3, 3, 4) 개 초과하여 가질 수 없다.
    private String[][] initJobs(String[][] jobs) {
        Random random = new Random();
        outer : while (true){
            int[] jobArray = new int[6]; // {0, 0, 0, 0, 0, 0}
            for(int i = 0; i<jobs.length;i++){
                int first = random.nextInt(6);
                int second = random.nextInt(6);
                while (second == first){
                    first = random.nextInt(6);
                    second = random.nextInt(6);
                }
                jobArray[first]++;
                jobArray[second]++;
                System.out.println(i+1 + "번째 플레이어 : " + jobNames[first] + ", " + jobNames[second]);
                jobs[i][0] = jobNames[first];
                jobs[i][1] = jobNames[second];
            }
            for(int job : jobArray){
                if(job>jobs.length-(jobs.length/6)-1){
                    continue outer;
                }
            }
            break;
        }
        return jobs;
    }

    public GameDTO getGame(String roomId) {
        return gameMap.getGame(roomId);
    }
    
    // 첫 라운드 첫 턴에만 랜덤으로 Player 리스트를 반환
    // 이후에는 플레이어 돈의 내림차순으로 반환
    // -> 순위 발표 및 다음 라운드 순서 결정 시 활용
    public List<Player> initOrder(String roomId) {
        int round = gameMap.getGame(roomId).getRound();
        int turn = gameMap.getGame(roomId).getTurn();
        List<Player> order = new ArrayList<>();

        for(Player player : gameMap.getGame(roomId).getPlayerList()){
            order.add(player);
        }
        if(round==1 && turn==1){
            Collections.shuffle(order);
        }else{
            Collections.sort(order);
        }
        return order;
    }
}
