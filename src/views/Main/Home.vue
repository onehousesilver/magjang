<template>
  <div id="main">
    <MainTheme />
    <MainText />
    <IsLogined />
  </div>
</template>

<script>
import MainTheme from '@/components/Main/modules/MainTheme.vue'
import MainText from '@/components/Main/modules/MainText.vue'
import IsLogined from '@/components/Main/layouts/IsLogined.vue'

export default {
  components: { 
    MainTheme,
    MainText,
    IsLogined, 
  },
  
}

</script>


<style>
 #main {
   position: relative;
   width: 1980px;
   height: 1020px;
   background-image: url('/src/assets/money_bg1.jpg');
   background-repeat: no-repeat;
   background-size: cover;
   background-attachment: fixed;

 }
  #main::before {
    content: '';
    opacity: 0.5;
    position: absolute;
    top: 0px;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #000;
 }

 .mj-btn {
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
.mj-btn:hover {
  background-color: rgb(223, 223, 223);
  color: #333;
}

</style>
