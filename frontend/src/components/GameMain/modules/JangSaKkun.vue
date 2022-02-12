<template>
  <div>
    <div
      class="col"
      @click="selectPriceShow">
      <UserVideo
        :stream-manager="streamManager"
        @click="updateMainVideoStreamManager(streamManager)" />
    </div>
    <div v-show="selectedUser">
      <SelectPrice />
    </div>
  </div>
</template>

<script>
import UserVideo from '@/components/GameMain/modules/UserVideo.vue'
import SelectPrice from '@/components/GameMain/modules/SelectPrice.vue'

export default {
  components: { 
    UserVideo,
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

<style scoped>
.col {
  background: linear-gradient(90deg, #fff 48%, #000 48%);
}
</style>