<template>
  <div class="price-box">
    <b>💰</b>
    <span
      class="price"
      v-bind="price">
      {{ price }}
    </span>
    <b> 만원 </b>
  </div>
</template>

<script>
export default {
  data() {
    return {
      price : 5000,
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
.price-box b {
    font-size: 2rem; 
    color : white;
}
</style>