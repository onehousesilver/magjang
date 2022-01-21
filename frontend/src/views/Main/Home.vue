<template>
  <div id="main">  
    <main-theme />
    <main-text />
    <naver-log-in-btn />
    <my-btn>엥ㅇㅇㅇㅇㅇ</my-btn>
  </div>
</template>

<script>
import MainText from '../../components/Main/MainText.vue'
import NaverLogInBtn from '../../components/Main/NaverLogInBtn.vue'
import MainTheme from '../../components/Main/MainTheme.vue'
import MyBtn from '../../components/Main/MyBtn.vue'

export default {
  components: { NaverLogInBtn, MainText, MainTheme, MyBtn },
  
}

</script>


<style>
 #main {
   position: relative;
   width: 1980px;
   height: 1020px;
   background-image: url('../../assets/money_bg1.jpg');
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
  
