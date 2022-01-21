<template>
  <div class="naver-login">
    <img
      @click="login"
      src="../../assets/naver_login.png"
      alt="login" />
  </div>
</template>

<script>
export default {
  methods: {
    login: function() {
      this.$router.push({ name: 'Nickname' })
    }
  }
}
</script>

<style scoped>
  .naver-login {
    position: relative;
    display: block;
    cursor: pointer;
    transition: all 0.2s linear;
    text-align: center;
  }
  .naver-login:hover {
    transform: scale(1.05);
  }
</style>