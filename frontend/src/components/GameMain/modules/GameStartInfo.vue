<template>
  <div class="row">
    <div class="col-4">
      <div> 
        <span class="badge badge-info">인원 조건</span>
        <h2>브로커 {{ broker }} 제외 2명</h2>
        <div>
          <UserAbility />
        </div>
      </div>
    </div>

    <!-- 거래 금액 -->
    <div class="col-3 total-money">
      <span class="badge badge-info">거래 금액</span> <br />
      <img
        src="@/assets/money.png"
        alt="money.png" />
      <div> {{ money }} 만원</div>
    </div>
          
    <!-- 설명 -->
    <div
      class="col-5">
      <div class="row">
        <div
          v-if="this.$store.state.conclusion === true"
          class="col">
          <GameText />
        </div>
        <div
          v-else
          class="col">
          <SelectedUserBtn />
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
import UserAbility from '@/components/GameMain/modules/UserAbility.vue'
import SelectedUserBtn from './SelectedUserBtn.vue'

export default {
  components: {
    // GameTimer,
    GameText,
    GameTimer,
    UserAbility,
    SelectedUserBtn,
    // GameRoundInfo,
  },
  data() {
    return {
      // back에서 랜덤으로 주는 금액
      money : 2000,
      timeLimit: 30,
      timePassed: 0,
      timerInterval: null,
      // 브로커 받아온 값
      broker: null,
    };
  },
  computed: {
    timeLeft() {
      if (this.timeLimit - this.timePassed == 0) {
        this.skipTimer();
      }
      return this.timeLimit - this.timePassed
    },
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
      this.timeLimit = 10,
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

<style scoped>
.total-money img {
  width: 150px;
}
span {
  font-size: 25px;
  margin-bottom: 20px;
}
.badge {
  background-color: #2778c4;
}
</style>