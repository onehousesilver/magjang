<template>
  <div>
    <div
      class="game-chat-log"
      ref="recvList">
      <div
        v-for="(item, idx) in recvList"
        :key="idx">
        <div v-if="item.reader!=''">
          {{ item.writer }} 님이 {{ item.reader }} 님에게 :
        </div>
        <div v-else>
          {{ item.writer }} 님이 모두에게 :
        </div>
        <h3> {{ item.message }} </h3>
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
      <select
        class="form-select"
        v-model="player"
        aria-label="Default select example">
        <option selected>
          귓속말을 보낼 유저를 선택하세요.
        </option>
        <option
          v-for="player in players"
          :key="player">
          <div v-if="player !== this.$store.getters.nickName">
            {{ player }}
          </div>
        </option>
      </select>
      <!-- <input
          id="msg"
          v-model="reader"
          type="text" /> -->
      <div class="mb-3">
        내용:  <input
          class="form-control"
          aria-label="default input example"
          v-model="message"
          type="text"
          @keyup="sendMessage" />
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
      writer: this.$store.getters.nickName,
      reader: "",
      message: "",
      recvList: [],
      selected: null,
      // 여기에 플레이어 이름을 받아오고
      players: ['토리최고', '킨더조이언박싱장인', '가으닝'],
      roomId: "room1",
      // bottom_flag: true
      player: this.player
    };
  },

  //stomp Chat system
  created() {
    // 여기서 connect()하면 페이지 접속 시 연결 - 사전에 사용자 id 저장 필요
    this.connect();
  },
  // updated() {
  //   var objDiv = document.getElementByClass('game-chat-log');
  //   if(this.bottom_flag) {
  //     objDiv.scrollTop = objDiv.scrollHeight
  //   }
  // },
  updated() {
    // 새로운 채팅이 입력되면 스크롤 하단으로 update
    let objDiv = this.$refs.recvList;
    console.log(objDiv.scrollHeight)
    objDiv.scrollTo({ top: objDiv.scrollHeight, behavior:'smooth'});
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
          reader: '',
          player: this.player
        };
        // 전체 채팅 전송
        if (this.reader == '') this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
        // 귓속말 전송
        else this.stompClient.send("/pub/chat/whisper", JSON.stringify(msg), {});
      }
      console.log(this.player)
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
    //   chat_on_scroll() {
    //     var pre_diffHeight = 0;
    //     var objDiv = document.getElementByClass("game-chat-log");

    //     if((objDiv.scrollTop + objDiv.clientHeight) == objDiv.scrollHeight){
    //             // 채팅창 전체높이 + 스크롤높이가 스크롤 전체높이와 같다면
    //             // 이는 스크롤이 바닥을 향해있다는것이므로
    //             // 스크롤 바닥을 유지하도록 플래그 설정
    //             this.bottom_flag = true;
    //     }

    //   if(pre_diffHeight > objDiv.scrollTop + objDiv.clientHeight ){
    //                   // 스크롤이 한번이라도 바닥이 아닌 위로 상승하는 액션이 발생할 경우
    //                   // 스크롤 바닥유지 플래그 해제
    //                   this.bottom_flag = false;
    //   }
    //     //
    //     pre_diffHeight = objDiv.scrollTop + objDiv.clientHeight
    // }
  },
}
</script>

<style>
h3 {
  font-size: 16px;
}

.game-chat-log {
  font-size: 16px;
  width: 100%;
  height: 30vh;
  overflow-y: scroll;
  -ms-overflow-style: none;
}
.game-chat-log::-webkit-scrollbar {
  display: none;
}
.game-chat-write {
  font-size: 16px;
}
</style>