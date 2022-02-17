<template>
  <div class="row">
    <div class="col-4 first-area">
      <div> 
        <span class="badge bg-light">거래 조건</span> <br />
        <span>브로커 포함 {{ dealLimitPeople }}명</span>
        <div id="abilities">
          <div
            v-for="deal in dealCondition"
            :key="deal.id">
            <Abilities
              class="table-ability"
              :ability="deal"
              :activate="!dealStateCount[deal]" />
          </div>
        </div>
      </div>
    </div>

    <!-- 거래 금액 -->
    <div class="col-3 total-money">
      <span class="badge bg-light">거래 금액</span> <br />
      <img
        src="@/assets/money.png"
        alt="money.png" />
      <div> {{ turnPrice }} 만원</div>
    </div>
          
    <!-- 설명 -->
    <div
      class="col-5">
      <div class="row">
        <div
          v-if="this.$store.state.conclusion === true"
          class="col table-text-col">
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
import SelectedUserBtn from '@/components/GameMain/layouts/SelectedUserBtn.vue'
import Abilities from '@/components/GameMain/modules/Abilities.vue'
import { mapGetters } from 'vuex'

export default {
  components: {
    // GameTimer,
    GameText,
    GameTimer,
    SelectedUserBtn,
    Abilities,
    // GameRoundInfo,
  },
  data() {
    return {
      // back에서 랜덤으로 주는 금액
      timeLimit: 180,
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
    ...mapGetters([
      'dealCondition',
      'dealStateCount',
      "turnPrice",
      "dealLimitPeople",
    ]),
  },
  methods: {
    // GameTimerMethods
    startTimer() {
      this.timerInterval = setInterval(() => (this.timePassed += 1), 1000);
    },
    skipTimer() {
      this.timeLimit = 180,
      this.timePassed = 0,
      clearInterval(this.timerInterval);
      this.timerInterval = null;
      // this.startTimer(); // 타이머 무한 반복하고 싶지 않을 때 주석하기
    },
  },
  watch: {
    turnPrice() {
      this.startTimer();
    }
  },
  mounted() {
    // this.startTimer();
  },
}
</script>

<style scoped>
.total-money img {
  width: 120px;
}
span {
  font-size: 25px;
  margin-bottom: 10px;
}
.badge {
  color: black;
}

#abilities {
  display: flex;
  float: left;
  flex-wrap: wrap;
  left: 22%;
  position: absolute;
}

.first-area {
  position: relative;
}

.table-text-col {
  position: relative;
}

</style>