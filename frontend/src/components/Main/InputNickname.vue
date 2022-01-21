<template>
  <div
    class="input-group input-group-lg"
    @keyup.enter="member">
    <input
      type="text"
      class="form-control"
      aria-label="Sizing example input"
      aria-describedby="inputGroup-sizing-lg"
      placeholder="닉네임을 입력하세요." />
  </div>
</template>

<script>
export default {
  methods: {
    member: function() {
      this.$router.push({ name: 'Member' })
    }
  }
}
</script>

<style scoped>
.input-group {
  position: relative;
  text-align: center;
  width: 400px;
  margin: 0 auto;
}
</style>