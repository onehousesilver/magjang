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
  height: 30vh;
  margin: 0 auto;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: rgb(180, 180, 180);
  font-size: 30px;
  border-radius: 20px;
}
.game-layout .user-video-head {
  position: absolute;
  top: 10px;
  width: 100%;
}

.game-layout .user-video .col {
  height: 25vh;
  border-radius: 20px;
  border: solid black 2px;
}

.game-layout .user-video .col:hover {
  cursor: pointer;
  border-color: tomato;
}

.game-layout .user-video-foot {
  position: absolute;
  top: 63vh;
  width: 100%;
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
  margin-top: 10px;
}

.game-chatting {
  width: 100%;
  height: 45vh;
  margin-top: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}
@keyframes float {
    0% {
        transform: translatey(0px);
    }
    70% {
        transform: translateY(-5px) scaleX(1);
    }
    to {
        transform: translatey(0px);
    }
}
</style>