<template>
  <div
    id="main"
    style="overflow: hidden">
    <div class="container">
      <MainTheme />
      <MainText />
      <IsLogined />
    </div>
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
  :root {
    height: 100%;
    overflow: hidden;
    background-color: #1f1f1f;
  }
 #main {
   position: relative;
   height: 100vh;
   background-image: url('/src/assets/money_bg1.jpg');
   background-repeat: no-repeat;
   background-size: 100%; 
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
