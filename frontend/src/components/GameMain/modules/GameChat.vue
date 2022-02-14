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
      players: ['모두에게', '토리최고', '킨더조이언박싱장인', '가으니'],
      roomId: "room1",
      player: this.player,
      
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
    this.emitter.on('chat_disconnect', this.disconnect)
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

            // 받은 데이터를 json으로 파싱하고 리스트에 저장 - 채팅 내역
            this.recvList.push(JSON.parse(res.body));
      });
          // 서버의 귓속말 endpoint를 구독
          this.stompClient.subscribe("/sub/chat/room/" + this.roomId + "/" + this.writer, (res) => {
            console.log("DM : ", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 저장 - 채팅 내역
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
    
    // 소켓 연결 해제
    disconnect() {
      if (this.connected) {
        // 연결 해제 메세지 전송
        this.stompClient.send(
          "/pub/chat/quit",
          JSON.stringify({ roomId: this.roomId, writer: this.writer }, {})
        );
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