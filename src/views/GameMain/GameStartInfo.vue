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
      class="col"
      style="display: flex; align-items: center;">
      <GameText />
      <GameTimer />
    </div>
  </div>
</template>

<script>
import GameTimer from '@/components/GameMain/modules/GameTimer'
import GameText from '@/components/GameMain/modules/GameText'


export default {
  components: {
    GameTimer,
    GameText
  },
  data() {
    return {
      money : 2000,
    };
  }
}
</script>

<style>

</style>