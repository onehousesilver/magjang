package B208.mag_jang.controller;

import B208.mag_jang.domain.ChatMessageDTO;
import B208.mag_jang.domain.ChatRoomDTO;
import B208.mag_jang.domain.GameDTO;
import B208.mag_jang.domain.RoomMap;
import B208.mag_jang.service.AsyncService;
import B208.mag_jang.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final AsyncService asyncService;

    // game시작시에 room에 있는 유저아이디를 game으로 넘겨주며 roommap 삭제
    //메서드 만들어서 처리 "/game/start/{roomId}"
    @MessageMapping(value = "/start")
    public void gameStart(ChatMessageDTO message) throws InterruptedException {
        gameService.gameStart(message.getWriter(), message.getRoomId());
        
        // 1. 게임시작 메세지 전송
        System.out.println("게임을 시작합니다!");
        template.convertAndSend("/sub/game/start/" + message.getRoomId(), gameService.getGame(message.getRoomId()));


        //다른 @Async 메서드를 호출한다.
        asyncService.sleep(message.getRoomId()).addCallback((result) -> {
            System.out.println("callback!! : " + result);
            try {
                initJobs(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, (e) -> {
            System.out.println("error");
        });

        System.out.println("gameStart Ended");
    }

    // 플레이어 능력 생성 후 반환
    public void initJobs(String roomId) throws InterruptedException {
        System.out.println("initJobs : "+roomId);
        template.convertAndSend("/sub/game/jobs/" + roomId, gameService.getNextJobs(roomId));

        //다른 @Async 메서드를 호출한다.
        asyncService.sleep(roomId).addCallback((result) -> {
            System.out.println("callback!! : " + result);
            initOrder(result);
        }, (e) -> {
            System.out.println("error");
        });
    }

    // 플레이어 순서를 생성 후 반환
    public void initOrder(String roomId){
        System.out.println("initOrder : "+roomId);
        template.convertAndSend("/sub/game/order/" + roomId, gameService.initOrder(roomId));
    }

    // 게임 시작 : 게임 기본 정보를 반환 -initGame을 해서 반환 but 딱히 init할 일이 없다    - ㅇ
    // 3초 정도 뒤, 플레이어 능력을 반환 -initJobs, 세 라운드 동안 출력해야 함              - ㅇ
    // 기다렸다가, 순서를 반환(프론트에서는 채팅창에 이를 출력)                             - ㅇ
    // 1라운드 1턴의 현재 브로커 및 거래 조건을 생성하여 반환 x 사람 수 x 라운드 수
    // 프론트에 타이머가 구현되어 있으니까 백에서는 쓰레드를 쓰지 않고 요청만 받기
    // -> 브로커가 거래 조건을 만족시켜서 요청을 보내거나, 혹은 시간이 초과되었다는 요청을 보냄
    // 백에서 해당 요청을 받아 결과를 메세지로 전송
    // 각 플레이어의 프론트에서 출력, 투표 UI 및 타이머 시작 -> 백에서는 투표 결과를 동기로 기다림
    // 모든 플레이어가 투표를 완료하거나 시간이 초과되면 요청
    // 시간이 초과되면 투표 거절로 인식하여 최종 결과를 전송
    // 턴++
    // 턴이 사람 수 만큼 진행된다면 라운드 ++ 및 턴 = 1, 순위 반환
    // 이후부터는 2번째 줄부터 다시 진행
    // ...
    // 마지막 라운드의 마지막 턴이 끝나면 최종 순위 반환
    // 이때 각 플레이어별 점수, 업적 등의 정보를 DB에서 최신화하여 전송

}
