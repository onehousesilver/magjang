<template>
  <div
    class="btn"
    @click="joinroom">
    방 참가
  </div>
</template>

<script>
export default {
  methods: {
    joinroom: function() {
      this.$router.push({ name: 'JoinRoom' })
    }
  }
}
</script>

<style scoped>
.btn {
  position: relative;
  width: 250px;
  padding: 10px;
  border: 2px solid #333;
  border-radius: 4px;
  color: #333;
  font-weight: 700;
  font-size: 20px;
  text-align: center;
  cursor: pointer;
  box-sizing: border-box;
  display: block;
  transition: .4s;
  margin: 0 auto;
  margin-top: 40px;
  background-color: #333;
  color: #fff;

}
.btn:hover {
  background-color: rgb(223, 223, 223);
  color: #333;
}

.btn:hover {
  background-color: rgb(223, 223, 223);
  color: #333;
}

</style>