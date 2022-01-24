<template>
  <div class="naver-login">
    <a href="/api/naverloginrequest">
      <img 
        src="src/assets/naver_login.png"
        alt="login" />
    </a>
  </div>
</template>

<script>
export default {
  methods: {
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