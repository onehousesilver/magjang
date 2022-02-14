<template>
  <div class="price-box">
    <span>
      <input
        class="price form-control form-control-lg"
        aria-label="default input example"
        type="number"
        placeholder="금액을 입력하세요."
        ref="price"
        v-model="price"
        @input="turnPriceSum" /><b>만원</b>
    </span>
  </div>
</template>

<script>
export default {
  data() { 
    return {
      price : '',
      noprice: '100단위로 작성해주세요.'
    };
  },
  // watch: {
  //   checkPrice() {
  //     console.log('!!!!!!!!!!!!!!!!!!!!!!!!!!!!')
  //     if (this.price*1 > 0 && this.price*1 % 100 == 0) {
  //       this.price
  //      } else {
  //       this.noprice
  //      }
  //   }
  // },
  methods: {
    // inputPrice(e) {
    //   e.preventDefault();
    //   if(this.price%100 != 0) {
    //     console.warn("100단위로 입력해주세요.");
    //     this.price = '';
    //     this.$refs.price.focus();
    //   }else {
    //     console.warn(this.price);
    //     this.$refs.price.focus();
    //   }
    // },
    turnPriceSum(){
      this.$store.state.turnPrice -= this.price*1
      this.$store.state.turnPrice += this.price*1
      console.log(this.price)
      console.log(this.$store.state.turnPrice)
    }
  }
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
  width: 56%;
}
</style>