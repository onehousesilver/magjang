<template>
  <div class="row">
    <div class="col">
      <div> 
        인원 조건
        <h1>브로커 {{ broker }} 제외 2명</h1>
        <div
          v-for="deal in dealCondition"
          :key="deal.id">
          <Abilities
            :ability="deal"
            :activate="!dealStateCount[deal].value" />
        </div>
      </div>
    </div>

    <!-- 거래 금액 -->
    <div class="col">
      <img
        src="@/assets/money.png"
        alt="money.png" />
      <div> {{ money }} 만원</div>
    </div>
          
    <!-- 설명 -->
    <div
      class="col">
      <div class="row">
        <div class="col">
          <GameText />
        </div>
        <div class="col">
          <GameTimer
            :time-left="timeLeft" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import GameTimer from '@/components/GameMain/modules/GameTimer'
import GameText from '@/components/GameMain/modules/GameText.vue'
import Abilities from '@/components/GameMain/modules/Abilities.vue'
import { mapGetters } from 'vuex'

export default {
  components: {
    // GameTimer,
    GameText,
    GameTimer,
    Abilities,
    // GameRoundInfo,
  },
  data() {
    return {
      money : 2000,
      timeLimit: 30,
      timePassed: 0,
      timerInterval: null,
      // 브로커 받아온 값
      broker: null,
    };
  },
  created() {
    console.log('create되자마자는 안되는거같구..')
    // this.ShowRoundInfo();
  },
  computed: {
    timeLeft() {
      if (this.timeLimit - this.timePassed == 0) {
        this.skipTimer();
      }
      return this.timeLimit - this.timePassed
    },
    ...mapGetters([
      'dealCondition',
      'dealStateCount',
    ])
    // formattedTimeLeft() {
    //   const timeLeft = this.timeLeft
			
    //   const minutes = Math.floor(timeLeft / 60)
			
    //   let seconds = timeLeft % 60
			
    //   if (seconds < 10) {
    //     seconds = `0${seconds}`
    //   }
			
    //   // The output in MM:SS format
    //   return `${minutes}:${seconds}`
    // }
  },
  methods: {
    // GameTimerMethods
    startTimer() {
      this.timerInterval = setInterval(() => (this.timePassed += 1), 1000);
    },
    skipTimer() {
      this.timeLimit = 30,
      this.timePassed = 0,
      clearInterval(this.timerInterval);
      this.timerInterval = null;
      this.startTimer(); // 타이머 무한 반복하고 싶지 않을 때 주석하기
    },
  },
  mounted() {
    this.startTimer();
  },
}
</script>

<style>

</style>