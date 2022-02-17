import { createStore } from 'vuex'

export default createStore({
  state: {
    userId: null,  // DB에서 유저 키값
    nickName: null,
    rankPoint: null,
    naverId: null,
    winAmount: null,
    gangAmount: null,
    proGangAmount: null,
    lastGenRoom: null,

    turnPrice: 0,  // 매 턴마다 주어지는 가격, 변동 없음

    gamePossible: false,
    // turn마다
    userPrice: [0, 0, 0, 0, 0],
    userNickName: ["", "", "", "", ""],
    
    // 결정여부
    conclusion: true,
    
    // 가져와야하는 값
    host: null,
    
    playerJobs: {},
    
    dealPrice: 0,  // 브로커가 가져갈 가격, 변동 심함
    dealLimitPeople: 0,
    dealCondition: [],
    dealStateCount: {},

    broker: true

  },
  getters: {  // state 상태 가져오기
    userId: state => state.userId,
    nickName: state => state.nickName,
    rankPoint: state => state.rankPoint,
    naverId: state => state.naverId,
    winAmount: state => state.winAmount,
    gangAmount: state => state.gangAmount,
    proGangAmount: state => state.proGangAmount,
    lastGenRoom: state => state.lastGenRoom,
    gamePossible: state => state.gamePossible,
    conclusion: state => state.conclusion,
    broker: state => state.broker,
    turnPrice: state => state.turnPrice,
    userPrice: state => state.userPrice,
    dealPrice: state => state.dealPrice,
    dealCondition: state => state.dealCondition,
    dealStateCount: state => state.dealStateCount,
    userNickName: state => state.userNickName,
    playerJobs: state => state.playerJobs,
    dealLimitPeople: state => state.dealLimitPeople,

    isLogined: function(state){
      return state.userId && state.naverId
    },

    isDealPossible(state) {  // 여기에 현재 선택된 인원 제한도 걸어두기
      return state.dealStateCount["선박"].value && state.dealStateCount["언변"].value &&
      state.dealStateCount["창고"].value && state.dealStateCount["인맥"].value &&
      state.dealStateCount["정보"].value && state.dealStateCount["로비"].value
    },

    findMyJob: state => nickName => {
      return state.playerJobs[nickName]
    },

  },
  mutations: {  // state 상태 변경, 동기적이어야 함
    SET_USER: function (state, userdata) {
      state.userId = userdata["userId"]
      state.nickName = userdata["nickName"]
      state.rankPoint = userdata["rankPoint"]
      state.naverId = userdata["naverId"]
      state.winAmount = userdata["winAmount"]
      state.gangAmount = userdata["gangAmount"]
      state.proGangAmount = userdata["proGangAmount"]
      state.lastGenRoom = userdata["lastGenRoom"]
    },
    SET_NICKNAME(state, nickName){
      console.log("닉네임", nickName)
      state.nickName = nickName
    },
    DEAL_STATE_COUNT_PLUS(state, selectdata){
      const first_ability = selectdata["first_ability"]
      const second_ability = selectdata["second_ability"]
      const isUserSelected = selectdata["isUserSelected"]

      if (isUserSelected) {
        state.dealStateCount[first_ability] += 1
        state.dealStateCount[second_ability] += 1
      }
      else {
        state.dealStateCount[first_ability] -= 1
        state.dealStateCount[second_ability] -= 1
      }
    },
    CHANGE_GAME_POSSIBLE(state, flag) {
      state.gamePossible = flag
    },
    UPDATE_PRICE(state, pricedata) {
      const value = pricedata["value"]
      const index = pricedata["index"]

      state.dealPrice += value
      state.userPrice[index] -= value
    },
    SET_USER_NICKNAME(state, userdata) {
      const NickName = userdata["NickName"]
      const index = userdata["index"]

      state.userNickName[index] = NickName
      // console.log(state.userNickName)
    },
    SET_PLAYER_JOB(state, jobObject) {
      state.playerJobs = jobObject
    },
    SET_DEAL_CONDITIONS(state, deal) {
      state.turnPrice = deal.turnPrice;  // 거래 금액
      state.dealPrice = deal.turnPrice;
      state.dealLimitPeople = deal.dealLimitPeople;  // 필요 인원수
      state.dealCondition = deal.dealCondition;  // 필요 능력
      state.dealStateCount = deal.dealStateCount;

      // console.log("딜", state.dealPrice, state.dealLimitPeople, state.dealCondition, state.dealStateCount)
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
    changeGamePossible({commit}, flag) {
      commit("CHANGE_GAME_POSSIBLE", flag)
    },
    updatePrice({commit}, pricedata) {
      commit("UPDATE_PRICE", pricedata)
    },
    setUserNickName({commit}, userdata) {
      commit("SET_USER_NICKNAME", userdata)
    },
    setPlayerJob({commit}, jsonJob) {
      const jobObject = {}
      jsonJob.forEach(data => {
        const nickName = data.nickName
        const jobs = data.jobs

        jobObject[nickName] = jobs
      })
      commit("SET_PLAYER_JOB", jobObject)
    },
    setDealConditions({commit}, deal) {
      const turnPrice = deal.dealMoney;  // 거래 금액
      const dealLimitPeople = deal.playerCount;  // 필요 인원수
      const dealCondition = deal.chosenJobs;  // 필요 능력

      const dealStateCount = {
        "선박": 1,
        "언변": 1,
        "창고": 1,
        "인맥": 1,
        "정보": 1,
        "로비": 1,
      };
      
      dealCondition.forEach(element => {
        dealStateCount[element] = 0
      });
      // console.log(turnPrice, dealLimitPeople, dealCondition, dealStateCount)
      commit("SET_DEAL_CONDITIONS", {turnPrice, dealLimitPeople, dealCondition, dealStateCount})
    }
  },
  modules: {
  }
})
