import { createStore } from 'vuex'

export default createStore({
  state: {
    user: null,  // DB에서 유저 키값
    nickname: null,
    score: null,
    accessToken: null,
    refreshToken: null,
  },
  getters: {  // stage 상태 가져오기
    user: state => state.user,
    nickname: state => state.nickname,
    score: state => state.score,
    accessToken: state => state.accessToken,
    refreshToken: state => state.refreshToken,
    isLogined: function(state){
      if (state.accessToken && state.refreshToken)
        return true
      return false
    }
  },
  mutations: {  // state 상태 변경, 동기적이어야 함
    SET_TOKEN: function (state, tokens) {
      state.accessToken = tokens["accessToken"]
      state.refreshToken = tokens["refreshToken"]
      console.log("accessToken: " + state.accessToken, "\nrefreshToken:" + state.refreshToken)
    },
    SET_USER: function (state, userdata) {
      state.user = userdata["user"]
      state.nickname = userdata["nickname"]
      state.score = userdata["score"]
      console.log("user:" + state.user, "\nnickname: " + state.nickname, "\nscore: " + state.score)
    },
  },
  actions: {  // mutations 호출, 비동기 가능
    setToken: function ({commit}, accessToken, refreshToken) {
      commit('SET_TOKEN', accessToken, refreshToken)
    },
    setUser: function ({commit}, user, nickname, score) {
      commit('SET_USER', user, nickname, score)
    }
  },
  modules: {
  }
})
