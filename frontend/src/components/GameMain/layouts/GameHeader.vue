<template>
  <div class="nav-top">
    <GameRuleInfo />
    <div v-if="gamePossible">
      <UserPrice />
    </div>
  </div>
</template>

<script>
// import InputPrice from '@/components/GameMain/modules/InputPrice.vue'
import GameRuleInfo from '@/components/GameMain/modules/GameRuleInfo.vue'
import UserPrice from '@/components/GameMain/modules/UserPrice.vue'

export default {
  components: {
    GameRuleInfo,
    UserPrice,
  },
}
</script>

<style>

</style>