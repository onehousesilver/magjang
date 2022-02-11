<template>
  <div>
    <h1>브로커 {{ broker }} 제외 2명</h1>
  </div>
  <div>
    <UserAbility />
  </div>
</template>

<script>
import UserAbility from './UserAbility.vue'
export default {
  components: { UserAbility },
  data() {
    return {
      broker: this.$store.getters.nickName,
    }
  }
}
</script>

<style>

</style>