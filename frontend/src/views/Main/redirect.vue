<template>
  <div></div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  created () {
    // 컴포넌트 렌더링이 되었을 때,

    // 쿼리스트링으로부터 토큰을 획득
    const accessToken = this.$route.query.accesstoken
    const refreshToken = this.$route.query.refreshtoken

    // 토큰이 존재하는 경우, Vuex Store에 토큰을 저장한다.
    if (accessToken && refreshToken) {
      this.setToken({
        "accessToken": accessToken,
        "refreshToken": refreshToken,
      })
      this.setUser({
        "user": 1,
        "nickname": "김주호",
        "score": 1000,
      })
    }

    // 토큰이 있던 없던, 루트 페이지로 이동한다.
    this.$router.replace('/')
  },
  methods: {  // vue 공부 빡쎄게 해서 제대로 쓰기
    ...mapActions([
      'setToken',
      'setUser'
    ]),
  }
}
</script>