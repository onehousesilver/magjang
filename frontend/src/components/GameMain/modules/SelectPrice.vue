<template>
  <span class="price-box">
    <b>￦</b>
    <input
      class="price"
      type="number"
      placeholder="금액을 입력하세요."
      v-model="price" 
      @input="showPrice" />
    {{ price }}
    <b> 만원</b>
  </span>
</template>

<script>
export default {
  data() { 
    return {  
      price : '',
    };
  },
  watch: {   
    showPrice(){
      return this.price > 0 && this.price % 100 == 0? this.price : '100단위로 작성해주세요.' 
    }
  },
}
</script>

<style scoped>
.price-box {
    /* border: 1px solid black;*/
    width: 70%;
}
.price-box .price {
    font-size: 2rem;
    color : white;
    border : 3px solid red;
    background-color: #1f1f1f;
}
.price-box b {
    font-size: 2rem; 
    color : white;
}

/* input창에서 증감버튼 없애기 */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>