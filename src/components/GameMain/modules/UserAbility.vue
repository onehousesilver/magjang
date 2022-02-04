<template>
  <div v-bind="getAbilities()">
    <h2>
      <span class="badge bg-secondary">
        {{ abilities[0] }}
      </span>
    </h2>
    <h2>
      <span class="badge bg-secondary"> 
        {{ abilities[1] }}
      </span>
    </h2>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  data: function() {
    return {
      abilities: [],
    }
  },
  methods: {
    getAbilities: function() {
      const abilityList = ['창고','인맥','언변','정보','로비','선박',]
      this.abilities = _.sampleSize(abilityList, 2)
    }
  }
}
</script>

<style>

</style>