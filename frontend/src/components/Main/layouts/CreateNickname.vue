<template>
  <div>
    <InputText place-text="닉네임을 입력해 주세요" />
    <Buttons btn-text="생성" />
  </div>
</template>

<script>
// import InputNickname from '@/components/Main/modules/InputNickname.vue'
import InputText from '@/components/Main/modules/InputText.vue'
import Buttons from '@/components/Main/modules/Buttons.vue'
export default {
  components: {
    // InputNickname, 
    InputText,
    Buttons,
  },
  
}
</script>


<style scoped>

</style>