<template>
  <div v-if="isLogined">
    <IsNickname />
  </div>
  <div v-else>
    <NaverLogInBtn />
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import NaverLogInBtn from '@/components/Main/modules/NaverLogInBtn.vue'
import IsNickname from '@/components/Main/layouts/IsNickname.vue'

export default {
  components: {
    NaverLogInBtn,
    IsNickname,
  },
  computed: {
    ...mapGetters([
      'isLogined',
    ])
  }
}
</script>

<style scoped>

</style>