<template>
  <div id="game-main">
    <div class="container-fluid">
      <GameHeader />
      <GamePage />
    </div>
  </div>
</template>

<script>
import GameHeader from '../../components/GameMain/layouts/GameHeader.vue';
import GamePage from '../../components/GameMain/layouts/GamePage.vue';

export default {
  components: {
    GameHeader,
    GamePage,
  },

  data(){
    return{
      
    }
  },
  methods: {
    
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
  height: 25vh;
  border-radius: 20px;
  border: solid black 2px;
}

.game-layout .user-video1 .col .p-3:hover {
  cursor: pointer;
  border-color: tomato;
}

.game-layout .user-video2 {
  position: relative;
  top: 40vh;
}

.game-layout .user-video2 .col .p-3 {
  height: 25vh;
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