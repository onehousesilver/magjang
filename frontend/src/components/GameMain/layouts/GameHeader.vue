<template>
  <div class="nav-top">
    <GameRule />
    <div v-if="this.$store.state.gamePossible">
      <UserPrice />
    </div>
  </div>
</template>

<script>
// import InputPrice from '@/components/GameMain/modules/InputPrice.vue'
import GameRule from '@/components/GameMain/layouts/GameRule.vue'
import UserPrice from '@/components/GameMain/modules/UserPrice.vue'

export default {
  components: {
    GameRule,
    UserPrice,
  },
}
</script>

<style>

</style>