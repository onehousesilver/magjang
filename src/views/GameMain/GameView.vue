<template>
  <div id="game-main">
    <div class="container-fluid">
      <!-- gamePossible=ture일 때 금액 화면 -->
      <div class="nav-top">
        <div v-if="gamePossible">
          <InputPrice />
        </div>
        <div v-else>
          <GameRuleInfo />
        </div>
      </div>
      <!-- 게임 대기 화면 -->
      <div class="row">
        <div class="game-layout col-10">
          <!-- user-video1 -->
          <div class="row user-video1 row-cols-3 g-2 g-lg-3">
            <div class="col">
              <div class="p-3 bg-light">
                Row column
              </div>
            </div>
            <div class="col">
              <div class="p-3 bg-light">
                Row column
              </div>
            </div>
            <div class="col">
              <div class="p-3 bg-light">
                Row column
              </div>
            </div>
          </div>
          <div class="row">
            <div class="game-table-el">
              <div v-if="gamePossible">
                <!-- gamePossible=ture일 때 게임 정보 테이블 화면 -->
                <GameStartInfo />
              </div>
              <div v-else>
                <!-- gamePossible=false일 때 대기화면 -->
                <GameWatingBtns 
                  @gamePossible="gamestart" />
              </div>
            </div>
          </div>
          <!-- user-video2 -->
          <div class="row user-video2 row-cols-3 g-2 g-lg-3">
            <div
              class="col">
              <div class="p-3 bg-light first">
                Row column
              </div>
            </div>
            <div class="col">
              <div class="p-3 bg-light">
                Row column
              </div>
            </div>
            <div class="col">
              <div class="p-3 bg-light">
                Row column
              </div>
            </div>
          </div>
        </div>
        <div class="col-2">
          <div class="row">
            <div class="game-log">
              게임로그
            </div>
          </div>
          <div class="row">
            <div class="game-chatting">
              게임채팅
            </div>
          </div>  
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InputPrice from '@/components/GameMain/modules/InputPrice.vue'
import GameWatingBtns from '@/components/GameMain/modules/GameWatingBtns.vue'
import GameStartInfo from './GameStartInfo.vue'
import GameRuleInfo from '@/components/GameMain/modules/GameRuleInfo.vue'


export default {
  components: {
    GameWatingBtns,
    GameStartInfo,
    InputPrice,
    GameRuleInfo
  },

  data(){
    return{
      gamePossible: false,
    }
  },
  methods: {
    gamestart() {
      this.gamePossible=true
    },
  }
}
</script>

<style>
:root {
  height: 100%;
  overflow: hidden;
  background-color: #1f1f1f;
}

#game-main {
  width: 100%;
  background-color: #1f1f1f;
}

.nav-top {
  height: 40px;
}

.game-layout {
  position: relative;
}

.game-layout .game-table-el {
  display: block;
  position: absolute;
  width: 80%;
  height: 35vh;
  margin: 0 auto;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: rgb(180, 180, 180);
  font-size: 30px;
  border-radius: 20px;
}
.game-layout .user-video1 {
  position: relative;
  top: 10px;
}

.game-layout .user-video1 .col .p-3 {
  height: 180px;
  border-radius: 20px;
  border: solid black 2px;
}

.game-layout .user-video1 .col .p-3:hover {
  cursor: pointer;
  border-color: tomato;
}

.game-layout .user-video2 {
  position: relative;
  top: 48vh;
}

.game-layout .user-video2 .col .p-3 {
  height: 180px;
  border-radius: 20px;
}

.game-log {
  width: 100%;
  height: 45vh;
  margin-right: 20px;
  background-color: black;
  margin-bottom: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

.game-chatting {
  width: 100%;
  height: 45vh;
  background-color: rgb(42, 106, 165);
  margin-top: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

</style>