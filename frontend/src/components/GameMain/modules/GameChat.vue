<template>
  <div>
    <div class="game-chat-log">
      <div
        v-for="(item, idx) in recvList"
        :key="idx">
        <div v-if="item.reader!=''">
          {{ item.writer }} 님이 {{ item.reader }} 님에게
        </div>
        <div v-else>
          {{ item.writer }} 님이 모두에게
        </div>
        <h3> 내용: {{ item.message }} </h3>
      </div>
    </div>
    <div class="game-chat-write">
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
      귓속말을 보낼 유저 이름:
      <input
        id="msg"
        v-model="reader"
        type="text" />
      <div class="mb-3">
        내용:  <textarea
          class="form-control"
          id="exampleFormControlTextarea1"
          v-model="message"
          type="text"
          @keyup="sendMessage"></textarea>
      </div>
      <!-- 내용: <input
        id="msg"
        v-model="message"
        type="text"
        @keyup="sendMessage" /> -->
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      // writer: "",
      writer: this.$store.getters.nickName,
      reader: "",
      message: "",
      recvList: [],
      roomId: "room1",
    };
  },
  
  //stomp Chat system
  created() {
    // 여기서 connect()하면 페이지 접속 시 연결 - 사전에 사용자 id 저장 필요
    this.connect();
  },
  methods: {
    // 엔터를 눌러 메세지 전송
    sendMessage(e) {
      if (e.keyCode === 13 && this.writer !== "" && this.message !== "") {
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
        };
        // 전체 채팅 전송
        if (this.reader == "") this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
        // 귓속말 전송
        else this.stompClient.send("/pub/chat/whisper", JSON.stringify(msg), {});
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
  // chat_on_scroll() {
  //   const log = document.getElementById(".game-chat-log");
  //   log.isScrollBottom = true;
  //   log.addEventListener("scroll", (e) => {
  //     if (e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight) {
  //       log.isScrollBottom = true;
  //     } else {
  //       log.isScrollBottom = false;
  //     }
  //   });

  //   const addLog = () => {
  //     const msg = document.getElementById("msg").value;
  //     log.innerHTML += `${msg}`

  //     if(log.isScrollBottom) {
  //       log.scrollTop = log.scrollHeight
  //     }
  //   };
  //}
};
</script>

<style>
h3 {
  font-size: small;
}

.game-chat-log {
  font-size: small;
  width: 100%;
  height: 30vh;
  overflow-y: scroll;
  -ms-overflow-style: none;
}
/* .game-chat-log::-webkit-scrollbar {
  display: none;
} */
.game-chat-write {
  font-size: small;
}
</style>