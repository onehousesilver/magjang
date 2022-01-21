<template>
  <div id="main">  
    <main-theme />
    <main-text />
    <room-open-btn />
    <room-join-btn />
  </div>
</template>

<script>
import MainTheme from '../../components/Main/MainTheme.vue'
import MainText from '../../components/Main/MainText.vue'
import RoomJoinBtn from '../../components/Main/RoomJoinBtn.vue'
import RoomOpenBtn from '../../components/Main/RoomOpenBtn.vue'
export default {
  components: { MainTheme, MainText, RoomOpenBtn, RoomJoinBtn },

}
</script>

<style>

</style>