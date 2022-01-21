<template>
  <div id="main">  
    <main-theme />
    <main-text />
    <enter-invite-code-btn />
    <game-start-btn />
  </div>
</template>

<script>
import MainTheme from '../../components/Main/MainTheme.vue'
import MainText from '../../components/Main/MainText.vue'
import EnterInviteCodeBtn from '../../components/Main/EnterInviteCodeBtn.vue'
import GameStartBtn from '../../components/Main/GameStartBtn.vue'
export default {
  components: {  MainTheme, MainText, EnterInviteCodeBtn, GameStartBtn },

}
</script>

<style>

</style>