<template>
  <div class="game-chat-write">
    <div
      class="game-chat-log"
      ref="recvList">
      <div
        v-for="(item, idx) in recvList"
        :key="idx">
        <div v-if="item.reader != '모두에게'">
          {{ item.writer }} 님이 {{ item.reader }} 님에게 :
        </div>
        <div v-else>
          {{ item.writer }} 님이 모두에게 :
        </div>
        <span> {{ item.message }} </span>
      </div>
    </div>
    <!-- <button @click="connect">
        연결
      </button>
      <button @click="disconnect">
        해제
      </button>
      유저이름:
      <input
        v-model="writer"
        type="text" /> -->
    <span>귓속말을 보낼 유저를 선택하세요.</span>
    <select
      class="form-select mb-3 mt-2"
      v-model="reader"
      aria-label="Default select example">
      <option
        selected
        disabled>
        귓속말을 보낼 유저를 선택하세요.
      </option>
      <option
        v-for="player in players"
        :key="player">
        <div v-if="player != writer">
          {{ player }}
        </div>
      </option>
    </select>
    <!-- 원래 귓말 보내는 창 -->
    <!-- <input
        id="msg"
        v-model="reader"
        type="text" /> -->
    <div class="mb-3 input-content">
      <span>내용을 입력해 주세요:</span>
      <input
        class="form-control"
        aria-label="default input example"
        v-model="message"
        type="text"
        @keyup="sendMessage" />
      <button
        type="button"
        class="btn btn-outline-warning"
        @click="sendMessage">
        전송
      </button>
    </div>
    <!-- 내용: <input
        id="msg"
        v-model="message"
        type="text"
        @keyup="sendMessage" /> -->
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
      // 여기에 플레이어 이름을 받아오고, 된다면 나 빼고 ^^
      players: ['모두에게', '토리최고', '킨더조이언박싱장인', '가으니'],
      roomId: "room1",
      // bottom_flag: true
      player: this.player,
    };
  },
  //stomp Chat system
  created() {
    // 여기서 connect()하면 페이지 접속 시 연결 - 사전에 사용자 id 저장 필요
    this.connect();
  },
  
  updated() {
    // 새로운 채팅이 입력되면 스크롤 하단으로 update
    let objDiv = this.$refs.recvList;
    objDiv.scrollTo({ top: objDiv.scrollHeight, behavior:'smooth'});
  },
  methods: {
    // 엔터를 눌러 메세지 전송
    sendMessage(e) {
      if (e.keyCode === 13 || e.type == "click" && this.writer !== "" && this.message !== "" ) {
        this.send();
        this.message = "";
      }
    },
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
        if (this.reader == '모두에게')
          this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {})
        // 귓속말 전송
        
        else{ 
          console.log('귓속말 전송')
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
          // this.stompClient.send(
          //   "/pub/chat/enter",
          //   JSON.stringify({ roomId: this.roomId, writer: this.writer }, {})
          // );
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
      }
    },
  },
}
</script>

<style scoped>
.game-chat-write {
  font-size: 16px;
}

.game-chat-log {
  width: 100%;
  height: 25vh;
  overflow-y: scroll;
  -ms-overflow-style: none;
}
.game-chat-log::-webkit-scrollbar {
  display: none;
}
.form-control {
  width: 80%;
  display: inline-block;
}

.btn {
  margin-left: 20px;
  margin-bottom: 4px;
}
</style>