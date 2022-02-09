<template>
  <div>
    <div
      class="col p-3 bg-light"
      @click="selectPriceShow">
      <UserVideo
        :stream-manager="streamManager"
        @click="updateMainVideoStreamManager(streamManager)" />
      <UserAbility />
    </div>
    <div v-if="selectedUser">
      <SelectPrice />
    </div>
  </div>
</template>

<script>
import UserVideo from '@/components/GameMain/modules/UserVideo.vue'
import UserAbility from '@/components/GameMain/modules/UserAbility.vue'
import SelectPrice from '@/components/GameMain/modules/SelectPrice.vue'

export default {
  components: { 
    UserVideo,
    UserAbility,
    SelectPrice,
  },
  data() {
    return{
      selectedUser: false,
    }
  },
  props: {
		streamManager: {
      type: Object,
      default: null,
    }
	},
  methods: {
    selectPriceShow(){
      this.selectedUser = !this.selectedUser;
    },
  },
}
</script>

<style>

</style>