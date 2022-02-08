<template>
  <div class="nav-top">
    <GameRuleInfo />
    <div v-if="gamePossible">
      <InputPrice />
    </div>
  </div>
</template>

<script>
import InputPrice from '@/components/GameMain/modules/InputPrice.vue'
import GameRuleInfo from '@/components/GameMain/modules/GameRuleInfo.vue'

export default {
  components: {
    InputPrice,
    GameRuleInfo,
  }
  
}
</script>

<style>

</style>