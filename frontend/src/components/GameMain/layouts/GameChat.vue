<template>
  <div
    class="game-chat-write">
    <!-- 실제 채팅이 보여지는 창 -->
    <div
      class="game-chat-log"
      ref="recvList">
      <div
        v-for="(item, idx) in recvList"
        :key="idx">
        <!-- 귓속말 할 때 발신자와 수신자에게 보이는 메세지 -->
        <div v-if="item.reader == '모두에게'">
          {{ item.writer }} 님이 모두에게 :
        </div>
        <!-- player가 입장/퇴장할 때 보이는 알림 -->
        <div
          v-else-if="item.reader == null"
          style="color: #ffc107;">
          📢 장사꾼 입/퇴장 알림
        </div>
        <!-- 전체채팅 할 때 모두에게 보이는 메세지 -->
        <div v-else>
          {{ item.writer }} 님이 {{ item.reader }} 님에게 :
        </div>
        <!-- 입력한 메세지 내용 -->
        <span> {{ item.message }} </span>
      </div>
    </div>
    
    <!-- 귓속말을 보낼 유저를 선택하는 select 창 -->
    <section class="not-game-chat-log">
      <span>귓속말을 보낼 장사꾼을 선택하세요.</span>
      <select
        class="form-select mb-3 mt-2"
        v-model="reader"
        aria-label="Default select example">
        <option
          selected
          disabled>
          귓속말을 보낼 장사꾼을 선택하세요.
        </option>
        <option
          v-for="gameplayer in whisperPeople"
          :key="gameplayer">
          {{ gameplayer }}
        </option>
      </select>


      <!-- 채팅 입력하는 input -->
      <div class="mb-3 input-content">
        <span>내용:</span>
        <input
          class="form-control"
          aria-label="default input example"
          v-model="message"
          type="text"
          @keyup="sendMessage" />

        <!-- 채팅 전송하는 버튼 -->
        <button
          type="button"
          class="btn btn-outline-warning chat-send-btn"
          @click="sendMessage">
          전송
        </button>
      </div>
    </section>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      writer: this.$store.getters.nickName,
      reader: '모두에게',
      message: "",
      recvList: [],
      selected: null,
      // 여기에 플레이어 이름을 받아오기
      players: [],
      roomId: "room1",
      player: this.player,
      host: '',   
    };
  },
  computed: {
    // 나를 제외한 다른 사람에게 귓속말이 가능하게 필터링
    whisperPeople : function() {
      return this.players.filter(player => player != this.writer)
    }
  },
  
  //stomp Chat system
  created() {
    // 여기서 connect()하면 페이지 접속 시 연결 - 사전에 사용자 id 저장 필요
    this.connect();
    // 채팅 나갔을 때 끊어짐
    this.emitter.on('chat_disconnect', this.disconnect)
  },
  mounted() {
    this.emitter.on('IamHost', this.gameStart)
    
  },
  updated() {
    // 새로운 채팅이 입력되면 스크롤 하단으로 update
    let objDiv = this.$refs.recvList;
    objDiv.scrollTo({ top: objDiv.scrollHeight, behavior:'smooth'});
  },
  methods: {
    // 엔터를 눌러 메세지 전송
    sendMessage(e) {
      if (e.keyCode === 13 && this.message !== "" ) {
        this.send();
        this.message = "";
    // 전송버튼 눌러서 메세지 전송
      } else if (e.type === "click" && this.writer !== null && this.message !== "") {
        this.send();
        this.message = "";
      }},
    // 전체 채팅 or 귓속말 전송
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          writer: this.writer,
          message: this.message,
          roomId: this.roomId,
          reader: this.reader,
          player: this.player
        };
        // 전체 채팅 전송
        if (this.reader == '모두에게') {
          this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {}) }
        // 귓속말 전송
        else { 
          this.player = this.reader
          this.stompClient.send("/pub/chat/whisper", JSON.stringify(msg), {});
        }
      }
    },
    connect() {
      // 서버에 연결 요청 - StompWebSocketConfig 참조
      const serverURL = "http://localhost:8080/stomp/chat";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);

          // 서버의 전체 채팅 endpoint를 구독
          this.stompClient.subscribe("/sub/chat/room/" + this.roomId, (res) => {
            console.log("전체 메시지 : ", res.body);

            this.recvList.push(JSON.parse(res.body));
          });

          // 서버의 귓속말 endpoint를 구독
          this.stompClient.subscribe("/sub/chat/room/" + this.roomId + "/" + this.writer, (res) => {
            console.log("DM : ", res.body);

            this.recvList.push(JSON.parse(res.body));
          });

          // 누가 입장?
          this.stompClient.subscribe("/sub/chat/enter/" + this.roomId, (res) => {
            var str = JSON.parse(res.body);
            str['message'] = "입장 하였습니다";
            str['writer'] = "더미 사용자";
            console.log("enter : ", str);
            this.recvList.push(str.writer + "가 입장하였습니다");
          });

          // !!!!!!!!!!!res.body로 변경해야함!!!!!!!!!!!!!!!//
          // 현재 플레이어들의 리스트(첫 번째 플레이어가 호스트)
          this.stompClient.subscribe("/sub/chat/players/" + this.roomId, (res) => {
            console.log("players : ", res.body);
            // player 받아서 push
            // res.body 초기화 해버리기!!!!!
            // var str = JSON.parse(res.body)
            const temPl = ['토리','연두', '쌀쫑', '보리']
            
            this.emitter.emit('hostNPlayers', temPl)
          });
          // 누가 퇴장?
          this.stompClient.subscribe("/sub/chat/quit/" + this.roomId, (res) => {
            console.log("quit : ", res.body);
            var str = JSON.parse(res.body);
            str['message'] = "입장 하였습니다";
            str['writer'] = "더미 사용자";
            this.recvList.push(str.writer + "가 퇴장하였습니다");
          });


          // 게임 관련 subscribe - "/sub/game/{endpoint}"

          // 게임 기본 정보를 받아 화면에 출력
          // 백에서 현재 인원수가 4보다 적다면 null을 반환
          // 다음 메세지로 sub/game/round/{players}를 받음
          this.stompClient.subscribe("/sub/game/start/" + this.roomId, (res) => {
            console.log("게임 시작 : ", res.body);
            // 1. res.body 확인 후 GameDTO가 잘 왔다면 채팅창 or 게임 로그에 "게임을 시작합니다" 출력
            
            console.log("resbody : " + res.body)
            if(res.body != null){
              console.log("resbody가 GameDTO")
              // this.emitter.emit('sendLog');//변수도 포함하여 전송
              //1. 게임로그에 메세지 띄우라고 emit
              //2. 게임 화면 구성하는 메서드를 실행해달라고 emit
            }else{
              console.log("인원이 부족하다고 알리기")
            }
            // 2. null이 왔다면 인원이 부족하다는 알림 출력
            // this.recvList.push(JSON.parse(res.body));
          });


          // 현재 라운드의 플레이어별 능력을 반환
          /*
          res.body : 
          [{"nickName":"1","money":0,"jobs":["창고","인맥"],"gangAmount":0}
          ,{"nickName":"2","money":0,"jobs":["정보","로비"],"gangAmount":0}
          ,{"nickName":"3","money":0,"jobs":["정보","인맥"],"gangAmount":0}
          ,{"nickName":"4","money":0,"jobs":["정보","창고"],"gangAmount":0}]
          */
          this.stompClient.subscribe("/sub/game/jobs/" + this.roomId, (res) => {
            console.log("직업 분배 : ", res.body);
            // 1. res.body 확인 후 게임 로그에 "이번 라운드의 능력을 분배합니다" 등 출력
            console.log("================== 각 res.body 출력 =================")
            var str = JSON.parse(res.body);
            // console.log("str: " + str);
            // console.log("str[0]: " + str[0]);  // object object
            // console.log("str[1].nickName: " + str[1].nickName);  // null
            // console.log("str[2].jobs: " + str[2].jobs);  // 선박, 로비
            // console.log("str[3].jobs[0]: " + str[3].jobs[0]);  // 인맥
            for(var p in res.body){
              console.log("nickName: " + str[p].nickName);
              console.log("jobs: " + str[p].jobs);
              const test = [str[p].nickName, str[p].job];
              console.log(test);  // test
              
              // this.emitter.emit('playerAbility',[str[p].nickName, str[p].job]);  // 이렇게 보내는게 아닌가...?
            }

            // 2. 플레이어별 능력 컴포넌트??에 이를 반영
            // this.recvList.push(JSON.parse(res.body));
          });


          // 현재 라운드의 순서를 반환. 1라운드 : 랜덤, 2~3라운드 : 소지 금액 순
          this.stompClient.subscribe("/sub/game/order/" + this.roomId, (res) => {
            console.log("순서 결정 : ", res.body);
            // 1. res.body 확인 후 게임 로그에 "{round} 라운드의 순서입니다. ~~" 등 출력
            // 2. 백엔드에서 매 턴 Player형객체로 브로커를 보내줍니다 
            // 형식 : ["3","4","2","1"]

            var order = JSON.parse(res.body);
            console.log("첫째 순번 : " + order[0]);
            for (var i = 0; i < order.length; i++) {
              console.log(order[i]);
            }

            this.recvList.push(JSON.parse(res.body));
          });


          // 현재 턴의 브로커 닉네임을 반환
          this.stompClient.subscribe("/sub/game/broker/" + this.roomId, (res) => {
            console.log("브로커 전달 : ", res.body.nickName);
            // 1. 백엔드에서 매 턴 Player형 객체로 브로커를 보내줍니다 
            // 2. res.body 확인 후 게임 로그에 "이번 턴의 브로커는 {nickname}입니다" 등 출력

            this.recvList.push(JSON.parse(res.body));
          });


          // 현재 턴의 거래 조건을 반환
          // 거래조건이란? 브로커를 포함한 인원수, 필요 능력, 거래 금액으로 이루어진 구조체
          // 4명 기준 : 필요능력 2~3, 인원수 2~3, 거래 금액 1000~2000
          // 5명 기준 : 필요능력 2~3, 인원수 2~3, 거래 금액 1000~2000
          // 6명 기준 : 필요능력 2~4, 인원수 2~4, 거래 금액 1000~2000
          // ※ 거래에 필요한 인원수에 따른 거래 금액 보너스가 있음(3명 +200, 4명 +300)
          this.stompClient.subscribe("/sub/game/deal/" + this.roomId, (res) => {
            console.log("거래 조건 생성 : ", res);
            // 1. res.body 확인 후 게임 로그와 테이블에 현재 거래 조건을 출력
            // 2. 일정 시간 후 or 바로 타이머를 작동시킴
            // 3. 사전에 브로커로 지정된 플레이어에게 클릭 권한...같은걸 주고 입력 받음
            //제가 예시로 적어놓은 변수들만 필요할거예요!!!어께이이이

            var deal = JSON.parse(res.body);
            console.log("거래 금액 : " + deal.dealMoney);
            console.log("필요 인원수 : " + deal.playerCount);
            console.log("필요 능력 : " + deal.chosenJobs);

            this.recvList.push(JSON.parse(res.body));
          });


          // 브로커의 선택을 실시간으로 반환
          // 이를 통해 모든 플레이어가 브로커의 선택과 동기화될 수 있음
          this.stompClient.subscribe("/sub/game/choice/" + this.roomId, (res) => {
            console.log("플레이어 실시간 선택 : ", res.body);
            // 1. res.body로 플레이어 객체가 오면, 해당 플레이어를 클릭했다는 뜻
            // 2. 따라서, 각 프론트에서는 사용자별 boolean 배열을 갖고 있다가 이를 반영하여 화면 결정

            console.log("여긴 글자 그대로 nickname 입니다 : " + res.body);

            this.recvList.push(JSON.parse(res.body));
          });

          // 브로커의 최종 선택 --> 거래를 원하는 플레이어를 모두 선택후 결정하는 버튼에 연결
          // 거래를 원하는 플레이어를 이차원 리스트로 저장하여 전송해줘야함 List<List<Object>>  
          this.stompClient.subscribe("/sub/game/finalchoice/" + this.roomId, (res) => {
            console.log("플레이어 최종 선택 : ", res.body);
            // 1. res.body로 투표에 참여한 플레이어 목록이 오면 해당 플레이어들에게 투표창을 띄워줌
            
            var deal = JSON.parse(res.body);
            if (deal == null) {
              console.log("제안 실패 : " + deal);
            } else {
              console.log("제안한 금액 : " + deal.dealAmount); // 요건 map처럼 되어 있어서
              console.log("제안한 금액 1 : " + deal.dealAmount["1"]); // 요렇게 쓰면 됩니당
              console.log("제안한 금액 2 : " + deal.dealAmount["1"]);
            }
            
            this.recvList.push(JSON.parse(res.body));
          });

          // 투표창이 뜬 플레이어는 거래 찬성 or 거래 반대를 투표함
          // 플레이어 별 투표현황은 서버에서 집계되고 모든 플레이어가 투표완료시 다음 턴으로 진행
          this.stompClient.subscribe("/sub/game/vote/" + this.roomId, (res) => {
            console.log("한 플레이어 투표 : ", res.body);
            // 1. res.body에 투표를 완료한 플레이어를 전송 --> 어떤 선택을 했는지는 비밀
            
            var player = JSON.parse(res.body);
            console.log("제안한 금액 : " + player[0]); // 투표한 플레이어 닉네임

            this.recvList.push(JSON.parse(res.body));
          });



          // 투표인원이 거래인원과 같아지면 백에서 자동으로 투표를 완료시킵니다.
          this.stompClient.subscribe("/sub/game/finalvote/" + this.roomId, (res) => {
            console.log("플레이어 투표 최종 완료 : ", res.body);
            // 투표가 전원완료되면 res.body에 플레이어 별 분배금액을 담아보냄
            //분배금액은 프론트에서 (본인금액+res.body의 분배금액) 하여 계산된 본인의 가격을 보여주시면됩니다.
            // finalvote가 수행되면 다음턴으로 넘어갑니다

            var deal = JSON.parse(res.body);
            console.log("실제 받을 금액 : " + deal.dealAmount); // 요건 map처럼 되어 있어서 밑에처럼 쓰면 됩니당
            console.log("실제 받을 금액 1 : " + deal.dealAmount["1"]);
            console.log("실제 받을 금액 2 : " + deal.dealAmount["2"]);
            if (deal.dealSuccess) {
              console.log("거래 체결 : " + deal.dealSuccess);
            } else {
              console.log("거래 결렬 : " + deal.dealSuccess);
            }

            this.recvList.push(JSON.parse(res.body));
          });
          
          // 해당 라운드의 순위 반환
          this.stompClient.subscribe("/sub/game/rank/" + this.roomId, (res) => {
            console.log("라운드 순위 반환 : ", res.body);
            //

            var rank = JSON.parse(res.body);
            console.log("현재 1등! : " + rank[0]);
            for (var i = 0; i < rank.length; i++) {
              console.log(rank[i]);
            }

            this.recvList.push(JSON.parse(res.body));
          });

          // 최종 순위 반환
          this.stompClient.subscribe("/sub/game/finalrank/" + this.roomId, (res) => {
            console.log("최종 순위 반환 : ", res.body);
            
            var finalrank = JSON.parse(res.body);
            console.log("최종 1등! : " + finalrank[0].nickName);
            for (var i = 0; i < finalrank.length; i++) {
              console.log(i + 1 + "등의 닉네임 : " + finalrank[i].nickName);
              console.log(i + 1 + "등의 최종 금액 : " + finalrank[i].money);
            }

            this.recvList.push(JSON.parse(res.body));
          });

          // 게임 로그 반환
          this.stompClient.subscribe("/sub/game/log/" + this.roomId, (res) => {
            console.log("최종 로그 반환 : ", res.body);
            
            var log = JSON.parse(res.body);
            if (log.length == 3) { // 3차원 배열로 게임 히스토리 출력
              console.log("로그 3차원 배열 : ");
              for (var round = 0; i < log.length; i++) {
                for (var turn = 0; i < log.length; i++) {
                  for (var playerIndex = 0; i < log.length; i++) {
                    console.log(
                      round +
                        "라운드 " +
                        turn +
                        "턴 " +
                        playerIndex +
                        "번째 플레이어 : " +
                        log[round][turn][playerIndex]
                    );
                  }
                }
              }
            } else if (log.length > 3) { // 플레이어 리스트 출력
              console.log("플레이어 리스트를 순서대로 반환 : " + log);
              for (var i = 0; i < log.length; i++) {
                console.log(i + 1 + "번째 플레이어 닉네임 : " + log[i]);
              }
            }

            this.recvList.push(JSON.parse(res.body));
          });

          // 처음 연결 시 접속 메세지 전송
          this.stompClient.send(
            "/pub/chat/enter",
            JSON.stringify({ roomId: this.roomId, writer: this.writer }, {})
          );
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    ////////// gameStart /////////////
    gameStart() {
      console.log("gameStart");
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          writer: this.writer,
          message: this.message,
          roomId: this.roomId,
          reader: this.reader,
        };
        // 게임 시작 메세지 전송
        this.stompClient.send("/game/start", JSON.stringify(msg), {});
      }
    },
    
    
    // 소켓 연결 해제
    disconnect() {
      if (this.connected) {
        // 연결 해제 메세지 전송
        // player 나가면 빼주기
        this.stompClient.send(
          "/pub/chat/quit",
          JSON.stringify({ roomId: this.roomId, writer: this.writer }, {})
        );
        // this.players = this.players.filter(this.player => player != this.writer)
        this.players.splice(this.players.indexOf(this.writer),1);
        this.stompClient.disconnect();

        this.connected = false;

        window.addEventListener('beforeunload', this.disconnect)
        this.$router.push({ name: 'Home' })
      }
    },
  },
}
</script>

<style scoped>

.game-chat-write {
  font-size: 16px;
}

.game-chat-write .game-chat-log {
  width: 100%;
  height: 25vh;
  overflow-y: scroll;
  -ms-overflow-style: none;
  margin-bottom: 10px;
  padding: 10px;
  border: solid rgb(67,66,47);
  border-radius: 10px;
}

.game-chat-write .game-chat-log div {
  margin-top: 10px;
}

.game-chat-write .game-chat-log::-webkit-scrollbar {
  display: none;
}

.game-chat-write .form-control {
  display: inline-block;
  width: 80%;
}

.game-chat-write .input-content .chat-send-btn {
  margin-left: 15px;
  margin-bottom: 4px;
}

.game-chat-write .input-content span {
  margin-bottom: 5px;
  display: block;
}

.not-game-chat-log .form-select {
  width: 52%;
}
</style>