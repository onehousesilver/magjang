<template>
  <div>
    <!-- 라운드 끝나면 라운드 숫자 바꾸기 -->
    <p>1ROUND 시작</p>
    <p>이번 라운드의 브로커 순서는</p>
    <div
      :v-for="broker in brokers" 
      :key="broker">
      {{ broker }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return{
      // 백에서 주는 랜덤으로 정해진 브로커 배열
      brokers: []
    }
  },
}
</script>

<style>

</style>