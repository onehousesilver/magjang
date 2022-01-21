<template>
  <div id="main">  
    <main-theme />
    <main-text />
    <input-nickname />
    <create-nickname-btn />
  </div>
</template>

<script>
import MainTheme from '../../components/Main/MainTheme.vue'
import MainText from '../../components/Main/MainText.vue'
import InputNickname from '../../components/Main/InputNickname.vue'
import CreateNicknameBtn from '../../components/Main/CreateNicknameBtn.vue'
export default {
  components: { MainTheme, MainText, InputNickname, CreateNicknameBtn },
  
}
</script>


<style>

</style>