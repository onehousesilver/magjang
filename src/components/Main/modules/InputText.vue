<template>
  <div
    class="mj-input-group input-group-lg"
    @keyup.enter="member">
    <input
      type="text"
      class="form-control"
      aria-label="Sizing example input"
      aria-describedby="inputGroup-sizing-lg"
      :placeholder="placeText" />
  </div>
</template>

<script>
export default {
  props: {
    placeText: {
      type: String,
      default: "default"
    },
  },
}
</script>

<style scoped>

.mj-input-group {
  position: relative;
  text-align: center;
  width: 400px;
  margin: 0 auto;
}

</style>