<template>
  <div
    class="button-flex">
    <button
      type="button"
      class="btn mj-btn"
      disabled>
      게임 코드 <hr />
      {{ gameCode }}
    </button>
    <button
      type="button"
      class="btn mj-btn"
      :disabled="userCount < 4"
      @click="gamePossible">
      게임시작
    </button>
    <!-- @click=" this.$router.push({ name: 'Home' })" -->
    <button
      type="button"
      class="btn mj-btn"
      @click="$emit('go-to-main')">
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
      this.$emit('game-possible')
    },
    
  }
}

</script>

<style>
.button-flex {
  display: flex;
  justify-content: space-evenly;
  margin-top: 6vh;
}


</style>