<template>
  <div class="text-center">
    <img
      src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
      width="222"
      alt="카카오 로그인 버튼" 
      @click="loginWithKakao" />
  </div>
</template>

<script>
export default {
  methods: {
    loginWithKakao() {
      window.Kakao.init('1fcbc7cbab73f87e64dc83c8f48ee628')
      const params = {
        redirectUri: "https://i6b208.p.ssafy.io/kakaocallback",
      };
      window.Kakao.Auth.authorize(params);
    }
  }
}
</script>

<style>

</style>