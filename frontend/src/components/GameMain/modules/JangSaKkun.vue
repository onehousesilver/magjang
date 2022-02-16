<template>
  <div>
    <div
      class="col">
      <UserVideo
        :stream-manager="streamManager"
        :abilities-array="abilitiesArray"
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
import { mapActions } from 'vuex'
// import _ from 'lodash'

export default {
  components: { 
    UserVideo,
    SelectPrice,
  },
  data() {
    return{
      selectedUser: false,
      abilitiesArray: [],
    }
  },
  props: {
		streamManager: {
      type: Object,
      default: null,
    },
	},
  methods: {
    ...mapActions([
      'userSelectEvent'
    ]),
    selectPriceShow(){
      this.selectedUser = !this.selectedUser;
      this.userSelectEvent({
        "first_ability": this.abilitiesArray[0],
        "second_ability": this.abilitiesArray[1], 
        "isUserSelected": this.selectedUser
      })
    },
    getAbilities(jobsList) {
      console.log(this.$store.getters.nickName)
      console.log(jobsList)
      // const abilityList = ['창고','인맥','언변','정보','로비','선박',]
      // this.abilitiesArray = _.sampleSize(abilityList, 2)

    },
  },
  mounted() {
  //  this.emitter.on('playerAbility', playerAbilities => this.getAbilities(playerAbilities))
    // this.emitter.off('initJobs')
    this.emitter.on('initJobs', jobsList => this.getAbilities(jobsList))
  },
}
</script>

<style scoped>
/* .col {
  background: rgb(156, 158, 127);
} */
</style>