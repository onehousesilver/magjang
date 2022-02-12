<template>
  <div class="price-box">
    <span>
      <input
        class="price form-control form-control-lg"
        aria-label="default input example"
        type="number"
        placeholder="금액을 입력하세요."
        v-model="price" 
        @input="showPrice" /><b>만원</b>
    </span>
  </div>
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
    text-align: center;
}
.price-box .price {
    font-size: 1.5rem;
    color : white;
    background-color: #1f1f1f;
    text-align:right;
}
.price-box b {
    font-size: 1.5rem; 
    color : white;
}

/* input창에서 증감버튼 없애기 */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.form-control {
  display: inline-block;
  width: 60%;
}


</style>