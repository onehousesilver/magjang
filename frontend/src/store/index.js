import { createStore } from 'vuex'

export default createStore({
  state: {
    userId: null,  // DB에서 유저 키값
    nickName: null,
    rankPoint: null,
    email: null,
    winAmount: null,
    gangAmount: null,
    proGangAmount: null,
    lastGenRoom: null,
    gamePossible: false,
    // turn마다
    turnPrice: 0,
    selectedList: [],

    // 결정여부
    conclusion: true,
  },
  getters: {  // state 상태 가져오기
    userId: state => state.userId,
    nickName: state => state.nickName,
    rankPoint: state => state.rankPoint,
    email: state => state.email,
    winAmount: state => state.winAmount,
    gangAmount: state => state.gangAmount,
    proGangAmount: state => state.proGangAmount,
    lastGenRoom: state => state.lastGenRoom,
    gamePossible: state => state.gamePossible,
    conclusion: state => state.conclusion,

    isLogined: function(state){
      if (state.userId && state.email)
        return true
      return false
    },
  },
  mutations: {  // state 상태 변경, 동기적이어야 함
    SET_USER: function (state, userdata) {
      state.userId = userdata["userId"]
      state.nickName = userdata["nickName"]
      state.rankPoint = userdata["rankPoint"]
      state.email = userdata["email"]
      state.winAmount = userdata["winAmount"]
      state.gangAmount = userdata["gangAmount"]
      state.proGangAmount = userdata["proGangAmount"]
      state.lastGenRoom = userdata["lastGenRoom"]
    },
    SET_NICKNAME(state, nickName){
      state.nickName = nickName
    },
    addSelectedList(state, price) {
      state.selectedList.push(price)
    },
  },
  actions: {  // mutations 호출, 비동기 가능
    setUser: function ({commit}, userId, nickName, rankPoint, email, winAmount, gangAmount, proGangAmount, lastGenRoom) {
      commit('SET_USER', userId, nickName, rankPoint, email, winAmount, gangAmount, proGangAmount, lastGenRoom)
    },
    setNickName({commit}, nickName){
      commit('SET_NICKNAME', nickName)
    }
  },
  modules: {
  }
})
