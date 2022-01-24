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

</style>
