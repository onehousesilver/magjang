<template>
  <div>
    <div
      class="col">
      <UserVideo
        :stream-manager="streamManager"
        :abilities-array="abilitiesArray"
        :index="index"
        @click="selectPriceShow" />
    </div>
    <div
      v-if="player && broker && gamePossible">
      <TotalPrice />
    </div>
    <div
      v-else-if="selectedUser && broker && gamePossible">
      <SelectPrice 
        :selected-user="selectedUser"
        :player="player" 
        :index="index" />
    </div>
  </div>
</template>

<script>
import UserVideo from '@/components/GameMain/modules/UserVideo.vue'
import TotalPrice from '@/components/GameMain/modules/TotalPrice.vue'
import SelectPrice from '@/components/GameMain/modules/SelectPrice.vue'
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'

export default {
  components: { 
    UserVideo,
    TotalPrice,
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
    player: {
      type: Boolean,
      default: false
    },
    index: {
      type: Number,
      default: -1
    }
	},
  methods: {
    ...mapActions([
      "userSelectEvent",
    ]),
    selectPriceShow(){
      if (!this.player && this.broker && this.turnPrice >= 100) {
        this.selectUser();
      }
    },
    selectUser() {
      // console.log("selectUser")
      this.selectedUser = !this.selectedUser;
        this.userSelectEvent({
          "first_ability": this.abilitiesArray[0],
          "second_ability": this.abilitiesArray[1], 
          "isUserSelected": this.selectedUser
        })
    },
    getAbilities() {
      const abilityList = ['창고','인맥','언변','정보','로비','선박',]
      this.abilitiesArray = _.sampleSize(abilityList, 2)
    },
  },
  mounted() {
    this.getAbilities();
  },
  computed: {
    ...mapGetters([
      "broker",
      "gamePossible",
      "turnPrice",
    ]),
  },
  watch: {
    broker(isBroker) {
      // console.log("broker is true")
      if (isBroker && this.player) {
        this.selectUser();
      }
    }
  }
}
</script>

<style scoped>
/* .col {
  background: rgb(156, 158, 127);
} */
</style>