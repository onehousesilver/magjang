<template>
  <div class="price-box">
    <vue-number-input
      v-bind="selectedUser"
      v-model="price"
      :min="0" 
      :step="100" 
      :inputtable="false"
      inline 
      controls
      @click="sumPrice"
      @focusout="pushSelectedDict" /> 
    <span>만원</span>
  </div>
</template>

<script>

export default {
  data() { 
    return {
      // 바뀌기 전 price
      preprice: 0,
      // 현재 price
      price: 100,
      // 두 번째 click event 발생 시 true로 변환(turn이 바뀔 때마다 reset)
      againClick: false,
    };
  },
  props: {
    selectedUser: Boolean
  },
  methods: {
    sumPrice() {
      if (this.againClick) {
        this.$store.state.turnPrice -= this.preprice
      } else {
        this.againClick=true
      }
      this.$store.state.turnPrice += this.price
      this.preprice = this.price
      console.log(this.$store.state.turnPrice)
    },
    pushSelectedDict(){
      console.log('----함수실행---------')
      if(this.selectedUser){
        if (this.againClick) {
          console.log('선택됐고 두번째이상 클릭')
        }else{
          this.againClick = true
          console.log('선택됐고 첫번째 클릭')
        }
      }else{
        console.log('선택 안됨')
      }
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
.price-box span {
    position: absolute;
    font-size: 1.5rem; 
    color : white;
    margin-left: 10px;
    font-weight: 700;
    margin-top: 3px;
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