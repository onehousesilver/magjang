<template>
  <div id="game-main">
    <div class="container">
      <InputPrice />
      <div class="start-btn">
        <Buttons
          btn-text="게임시작" />
        <div>
          <div class="game-table"></div>
          <div class="game-box">
            <div class="game-log">
              <div class="log-title">
                게임로그
              </div>
            </div>
            <div class="cheat">
              <div class="cheat-view"></div>
              <!--채팅 보여지는 곳 -->
              <div>
                <input
                  class="cheat-input"
                  type="text"
                  placeholder="채팅쳐라." />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InputPrice from '../../components/GameMain/layouts/InputPrice.vue'
import Buttons from '../../components/Main/modules/Buttons.vue'

export default {
  components: { 
    InputPrice,
    Buttons 
  },
}
</script>
<style scoped>
#game-main {
  position: relative;
  width: 100%;
  height: 940px;
  background-color: #1f1f1f;
}

.game-table {
    display: inline-block;
    width:1000px;
    height:400px;
    position:absolute;
    left:50%;
    top:50%;
    margin-left: calc(1000px/-2);
    margin-top:-150px;
    background-color: rgb(180, 180, 180);
}

.game-box {
    width: 300px;
    height: 100%;
    /*border : 1px solid white;*/
    float: right;
}
.game-box .game-log {
    width: 100%;
    height: 500px;
    border-radius: 20px;
    background-color: gray;
    margin : 50px 0px 10px 0px;
}
.game-box .game-log .log-title {
    background-color: #1f1f1f;
    border-radius: 30px;
    color: white;
    width: 100px;
    margin: 0 auto;
    text-align: center;
}
.game-box .cheat {
    width: 100%;
    height: 400px;
    margin: 0 0 50px 0;
    border: 1px solid white;
    border-radius: 10px;
    position: relative;
}
.game-box .cheat .cheat-input {
    border: 1px solid white;
    color: white;
    background-color: transparent;
    width: 280px;
    border-radius: 10px;
    position: absolute;
    bottom: 10px;
    left: 10px;
}
.game-box .cheat .cheat-view {
  width: 100%; 
  height: 90%;
  border: 1px solid white;
}
</style>