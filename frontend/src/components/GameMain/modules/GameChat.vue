<template>
  <div>
    <button @click="connect">연결</button>
    <button @click="disconnect">해제</button>
    유저이름:
    <input v-model="writer" type="text" />
    내용: <input v-model="message" type="text" @keyup="sendMessage" />
    귓속말:
    <input v-model="reader" type="text" />
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>유저이름: {{ item.writer }}</h3>
      <h3>내용: {{ item.message }}</h3>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      writer: "",
      reader: "",
      message: "",
      recvList: [],
      roomId: "room1",
    };
  },
  created() {
    // 여기서 connect()하면 페이지 접속 시 연결 - 사전에 사용자 id 저장 필요
    // this.connect();
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
      }
    },
  },
};
</script>
