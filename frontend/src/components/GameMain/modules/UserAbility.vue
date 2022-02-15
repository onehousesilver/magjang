<template>
  <!-- <div
    class="ability"
    v-bind="getAbilities()">
    <span class="badge bg-secondary">
      {{ abilities[0] }}
    </span>
    <span class="badge bg-secondary">
      {{ abilities[1] }}
    </span>
  </div> -->
  <div>
    <Abilities 
      v-if="abilitiesArray"
      :ability="abilitiesArray[0]"
      :activate="!dealStateCount[abilitiesArray[0]].value" />
    <Abilities
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
  text-align: center;
  bottom: -3rem;
  width: 100%;
}

.ability .badge {
  margin: 0.5rem;
  top: 30px;
  justify-content: center;
  display: inline-block;
  width: 45%;
  font-size: 30px;
}
</style> 