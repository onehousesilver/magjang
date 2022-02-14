<template>
  <div class="price-box">
    <span>
      <vue-number-input 
        v-model="price" 
        :min="100" 
        :step="100" 
        :inputtable="false"
        inline 
        controls
        @click="btnCnt" />
      <b>만원</b>
    </span>
  </div>
</template>

<script>

export default {
  data() { 
    return {
      price: 100,
      btncount: 1,
      againClick: false,
      preprice: 0
    };
  },
  // props: {
  //   selectedUser: false,
  // },
  methods: {
    btnCnt() {
      if (this.againClick) {
        this.$store.state.turnPrice -= this.preprice
      } else {
        this.againClick=true
      }
      this.$store.state.turnPrice += this.price
      this.preprice = this.price
      console.log(this.$store.state.turnPrice)
    }
  }
}
</script>

<style scoped>

.price-box {
    text-align: center;
    margin-top: 10px;
}
/*
.price-box .price {
    font-size: 1.5rem;
    color : white;
    background-color: #1f1f1f;
    text-align:right;
}
*/
.price-box b {
    font-size: 1.5rem; 
    color : white;
}
/*
input창에서 증감버튼 없애기 
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.form-control {
  display: inline-block;
  width: 56%;
}
*/
</style>