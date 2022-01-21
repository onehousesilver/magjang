<template>
  <div class="main">
    <div class="logo">
      <img
        @click="home"
        src="../../assets/logo.gif"
        alt="logo" />
    </div>
    <div class="title">
      <span class="one">막?</span>
      <span class="two">나가는</span>
      <span class="three">장!</span>
      <span class="four">사꾼</span>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    home: function() {
      this.$router.push({ name: 'Home' })
    }
  }
}
</script>

<style scoped>
  .main {
    position: relative;
  }
  .main .logo {
    text-align: center;
  }
  .main .logo img {
    text-align: center;
    width: 200px;
    height: 200px;
    transition: all 0.2s linear;
    cursor: pointer;
  }
  .main .logo img:hover {
    transform: scale(1.05);
  }
  .main .title {
    text-align: center;
    font-weight: 700;
  }
  .main .title .one {
    font-size: 90px;
    color: #720000;
  }
  .main .title .two {
    font-size: 70px;
    color: #fff;
  }
  .main .title .three {
    font-size: 90px;
    color: #F7CE0A
  }
  .main .title .four {
    font-size: 70px;
    color: #fff;
  }
</style>