<template>
  <b-button
    :variant="ce()"
    disabled="disabled">
    {{ ability }}
  </b-button>
</template>

<script>
export default {
  props: {
		ability: {
      type: String,
      default: "회색",
    }
	},
  data() {
    return {
      colors: {
        "선박": "danger",
        "언변": "primary",
        "창고": "success",
        "인맥": "warning",
        "정보": "info",
        "로비": "light",
        "회색": "secondary"
      },
    }
  },
  methods: {
    ce() {
      return this.colors[this.ability]
    }
  }
}
</script>

<style>

</style>