<template>
  <div
    class="button-flex">
    <button
      type="button"
      class="btn mj-btn"
      @click="copyCode">
      입장 코드 <hr />
      {{ gameCode }} 
    </button>
    <!-- player가 host일때  -->
    <button
      type="button"
      class="btn game-start-btn mj-btn"
      :disabled="userCount < 4"
      @click="gamePossible()">
      게임시작
    </button>
    <button
      type="button"
      class="btn mj-btn"
      @click="$emit('go-to-main'), clickMain()">
      메인화면으로
    </button>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  emits: ['go-to-main'],
  data() {
    return {
      gameCode: this.$route.params.code,
      userCount: 4,
    }
  },
  methods: {
    copyCode() {
      this.$copyText(this.gameCode)
      alert(this.gameCode + '복사되었습니다!')
    },
    gamePossible(){
      this.emitter.emit('gameStart')
      this.changeGamePossible(true)

      console.log('')
    },
    clickMain() {
      this.emitter.emit('chat_disconnect')
    },
    ...mapActions([
      "changeGamePossible"
    ])
  },
}

</script>

<style scoped>
.button-flex {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
}

.button-flex .mj-btn {
  width: 200px;
  margin-top: 0;
  padding-top: 15px;
}

.button-flex .game-start-btn {
  width: 200px;
  margin-top: 0;
  padding-top: 15px;
  /*color: #198754;*/
  border: 2px solid #198754;
  background-color: #198754;
  color: #fff;
}

.game-start-btn:hover {
  background-color: rgb(223, 223, 223);
  color: #198754;
}

.sp {
  background-blend-mode: overlay;
  background-color: rgba(0, 0, 0);
}
</style>