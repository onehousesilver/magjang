<template>
  <div
    class="ability"
    v-bind="getAbilities()">
    <span class="badge bg-secondary">
      {{ abilities[0] }}
    </span>
    <span class="badge bg-secondary">
      {{ abilities[1] }}
    </span>
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