<template>
  <div id="game-main">
    <div class="container-fluid">
      <!-- gamePossible=ture일 때 금액 화면 -->
      <div class="row">
        <div class="nav-top col">
          <div v-if="gamePossible">
            <InputPrice />
          </div>
        </div>
      </div>
      <!-- 게임 대기 화면 -->
      <div class="row">
        <div class="game-table-layout col-10">
          <div class="row ">
            <div class="game-table-el">
              <div v-if="gamePossible">
                <!-- gamePossible=ture일 때 게임 정보 테이블 화면 -->
                <GameStartInfo />
              </div>
              <div v-else>
                <!-- gamePossible=false일 때 대기화면 -->
                <GameWatingBtns />
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
          <div class="row ">
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


export default {
  components: {
    GameWatingBtns,
    GameStartInfo,
    InputPrice,
  },

  data(){
    return{
      gamePossible: true
    }
  }
}
</script>

<style>
:root {
  height: 100%;
  background-color: #1f1f1f;
}

#game-main {
  width: 100%;
  background-color: #1f1f1f;
}

.nav-top {
  height: 50px;
}

.game-table-layout {
  position: relative;
}

.game-table-layout .game-table-el {
  display: block;
  position: absolute;
  width: 80%;
  height: 40%;
  margin: 0 auto;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: rgb(180, 180, 180);
  font-size: 30px;
  border-radius: 20px;
}

.game-log {
  width: 100%;
  height: 400px;
  background-color: black;
  margin-bottom: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

.game-chatting {
  width: 100%;
  height: 400px;
  background-color: rgb(42, 106, 165);
  margin-top: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

</style>