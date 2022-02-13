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
    <!-- @click=" this.$router.push({ name: 'Home' })" -->
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
      this.$store.state.gamePossible = true
      // console.log('찍히나?')
    },
    clickMain() {
      this.emitter.emit('disconnect')
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
</style>