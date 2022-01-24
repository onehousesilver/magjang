<template>
  <div id="game-main">
    <span class="price-box">
      <b>￦</b>
      <input
        class="price"
        type="text"
        dir="rtl"
        placeholder="Price"
        v-model="price"
        readonly />
      <b> 원</b>
    </span>
    <div class="game-table"></div>
    <div class="game-box">
      <div class="game-log">
        <div class="log-title">
          게임로그
        </div>
      </div>
      <div class="cheat">
        <textarea
          name="cheat-view"
          class="cheat-view"
          cols="30"
          rows="10"></textarea> <!--채팅 보여지는 곳 -->
        <input
          class="cheat-input"
          type="text"
          placeholder="채팅쳐라." />
          <!--ㄷㄷㄷ개무서움 -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            price : 5000,
        };
    },
}
</script>
<style scoped>
#game-main {
  position: relative;
  width: 100%;
  height: 1000px;
  background-color: #1f1f1f;
}
.price-box {
    /* border: 1px solid black;*/
    width: 200px;
}
.price {
    font-size: 2rem;
    color : white;
    border : none;
    background-color: #1f1f1f;
}
.price-box b {
    font-size: 2rem; 
    color : white;
}

.game-table {
    display: inline-block;
    width:500px;
    height:500px;
    position:absolute;
    left:50%;
    top:50%;
    margin-left: calc(500px/-2);
    margin-top:-150px;
    border-radius: 50%;
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
</style>