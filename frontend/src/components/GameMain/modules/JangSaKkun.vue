<template>
  <div>
    <div
      class="col">
      <UserVideo
        :stream-manager="streamManager"
        @click="selectPriceShow" />
    </div>
    <div v-show="selectedUser && this.$store.state.gamePossible">
      <SelectPrice 
        :selected-user="selectedUser" />
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
    },
	},
  methods: {
    selectPriceShow(){
      this.selectedUser = !this.selectedUser;
    },
  },
}
</script>

<style scoped>
/* .col {
  background: rgb(156, 158, 127);
} */
</style>