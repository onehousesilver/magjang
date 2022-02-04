<template>
  <div class="row">
    <div class="col">
      <div> 
        인원 조건
      </div>
      <div> 
        능력 조건
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
          <GameTimer />
          <BaseTimer
            :time-left="timeLeft" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import GameTimer from '@/components/GameMain/modules/GameTimer'
import GameText from '@/components/GameMain/modules/GameText'
import BaseTimer from '@/components/GameMain/modules/BaseTimer'


export default {
  components: {
    GameTimer,
    GameText,
    BaseTimer
  },
  data() {
    return {
      money : 2000,
      timeLimit: 20,
      timePassed: 0,
      timerInterval: null,
    };
  },
  computed: {
    timeLeft() {
      return this.timeLimit - this.timePassed
    },
    formattedTimeLeft() {
      const timeLeft = this.timeLeft
			
      const minutes = Math.floor(timeLeft / 60)
			
      let seconds = timeLeft % 60
			
      if (seconds < 10) {
        seconds = `0${seconds}`
      }
			
      // The output in MM:SS format
      return `${minutes}:${seconds}`
    }
  },
  methods: {
    startTimer() {
      this.timerInterval = setInterval(() => (this.timePassed += 1), 1000);
    }
  },
  mounted() {
    this.startTimer();
  },
}
</script>

<style>

</style>