<template>
  <div
    class="button-flex">
    <button
      type="button"
      class="btn mj-btn"
      disabled>
      입장 코드 <hr />
      {{ gameCode }}
    </button>
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


export default {
  emits: ['game-possible', 'go-to-main'],
  data() {
    return {
      gameStartFlag: false,
      gameCode: this.$route.params.code,
      userCount: 4,
    }
  },
  methods: {
    gamePossible(){
      this.$store.state.gamePossible = true  // 직접 store에 접근해서 값 바꾸지 맙시다.. 안됩니다.. actions까지 끌고와서 쓰십셔..
    },
    clickMain() {
      this.emitter.emit('chat_disconnect')
    }
  }
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
  color: #198754;
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