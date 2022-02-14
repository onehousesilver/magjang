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
    
    // Stomp에서 전송한 조건 리스트, or마다 하나의 row로, table show용이자 ar 생성용(어떻게 제대로 굴릴 것인지는 Stomp 연동되면 수정할 것)
    dealCondition: 
      ["선박", "언변", "창고"],

    // mes 데이터를 이용하여 생성한 현 거래 조건 state
    dealStateCount: {
      "선박": {
        value: 0,
      },
      "언변": {
        value: 0,
      },
      "창고": {
        value: 0,
      },
      "인맥": {
        value: 1,
      },
      "정보": {
        value: 1,
      },
      "로비": {
        value: 1,
      },
    },
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
    
    isLogined: function(state){
      return state.userId && state.email
    },

    dealCondition: state => state.dealCondition,
    dealStateCount: state => state.dealStateCount,

    isDealPossible(state) {
      return state.dealStateCount["선박"].value && state.dealStateCount["언변"].value &&
      state.dealStateCount["창고"].value && state.dealStateCount["인맥"].value &&
      state.dealStateCount["정보"].value && state.dealStateCount["로비"].value
    }

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
    DEAL_STATE_COUNT_PLUS(state, selectdata){
      const first_ability = selectdata["first_ability"]
      const second_ability = selectdata["second_ability"]
      const isUserSelected = selectdata["isUserSelected"]

      if (isUserSelected) {
        state.dealStateCount[first_ability].value += 1
        state.dealStateCount[second_ability].value += 1
      }
      else {
        state.dealStateCount[first_ability].value -= 1
        state.dealStateCount[second_ability].value -= 1
      }
    }
  },
  actions: {  // mutations 호출, 비동기 가능
    setUser: function ({commit}, userdata) {
      commit('SET_USER', userdata)
    },
    setNickName({commit}, nickName){
      commit('SET_NICKNAME', nickName)
    },
    userSelectEvent({commit}, selectdata){
      commit('DEAL_STATE_COUNT_PLUS', selectdata)
    },
  },
  modules: {
  }
})
