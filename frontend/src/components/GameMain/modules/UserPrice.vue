<template>
  <div class="price-box">
    <span>💰</span>
    <span
      class="price"
      v-bind="price">
      {{ price }}
    </span>
    <span> 만원 </span>
  </div>
</template>

<script>
export default {
  data() {
    return {
      price : this.$store.getters.myMoney,
    };
  },
}
</script>

<style scoped>
.price-box .price {
    font-size: 2rem;
    color : white;
    border : none;
    background-color: #1f1f1f;
    margin-left: 100px;
}
.price-box span {
    font-size: 2rem; 
    color : white;
}
</style>