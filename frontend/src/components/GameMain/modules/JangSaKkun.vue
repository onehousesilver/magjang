<template>
  <div class="col p-3 bg-light">
    <UserVideo
      :stream-manager="streamManager"
      @click="updateMainVideoStreamManager(streamManager)" />
    <UserAbility />
  </div>
</template>

<script>
import UserVideo from '@/components/GameMain/modules/UserVideo.vue'
import UserAbility from '@/components/GameMain/modules/UserAbility.vue'
export default {
  components: { 
    UserVideo,
    UserAbility,
  },
  props: {
		streamManager: {
      type: Object,
      default: null,
    }
	},
}
</script>

<style>

</style>