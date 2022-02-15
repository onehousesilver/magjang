<template>
  <div class="ability">
    <Abilities 
      class="badge"
      v-if="abilitiesArray"
      :ability="abilitiesArray[0]"
      :activate="!dealStateCount[abilitiesArray[0]].value" />
    <Abilities
      class="badge"
      v-if="abilitiesArray"
      :ability="abilitiesArray[1]"
      :activate="!dealStateCount[abilitiesArray[1]].value" />
  </div>
</template>

<script>
import Abilities from '@/components/GameMain/modules/Abilities.vue'
import {mapGetters} from 'vuex'

export default {
  components: {
    Abilities,
  },
  data() {
    return {
      
    }
  },
  props: {
    abilitiesArray: {
      type: Array,
      default: undefined,
    }
	},
  methods: {
    
  },
  computed: {
    ...mapGetters([
      'dealStateCount',
    ])
  }
}
</script>

<style scoped>
.ability {
  display : flex;
}

</style> 