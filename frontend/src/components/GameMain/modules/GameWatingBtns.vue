<template>
  <div
    class="button-flex">
    <button
      type="button"
      class="btn btn-outline-dark btn-lg"
      disabled>
      게임 코드 <hr />
      {{ gameCode }}
    </button>
    <button
      type="button"
      class="btn btn-secondary btn-lg"
      :disabled="userCount < 4"
      @click="gamePossible">
      게임시작
    </button>
    <button
      type="button"
      class="btn btn-secondary btn-lg"
      @click=" this.$router.push({ name: 'Home' })">
      메인화면으로
    </button>
  </div>
  <div></div>
</template>

<script>

export default {
  data() {
    return {
      gameStartFlag: false,
      gameCode: '2D3201',
      userCount: 4,
    }
  },
  methods: {
    gamePossible(){
      this.$emit('game-possible')
    }
  }
}

</script>

<style>
.button-flex {
  display: flex;
  justify-content: space-evenly;
  margin-top: 100px;

}


</style>