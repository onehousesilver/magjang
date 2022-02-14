<template>
  <div v-if="activate">
    <b-button
      :variant="ce()"
      disabled="disabled">
      {{ ability }}
    </b-button>
  </div>
  <div v-else>
    <b-button
      disabled="disabled">
      {{ ability }}
    </b-button>
  </div>
</template>

<script>
export default {
  props: {
		ability: {
      type: String,
      default: "무능",
    },
    activate: {
      type: Boolean,
      default: true,
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
      },
    }
  },
  methods: {
    ce() {
      return this.colors[this.ability]
    }
  }  // 차라리 여기에 dealStateCount 넣어서 자동으로 보게 하는 건 어떨까? 내일 그렇게 수정하자
}
</script>

<style>

</style>