<template>
  <div>
    <InputText place-text="닉네임을 입력해 주세요" />
    <button class="mj-btn">
      생성
    </button>
  </div>
</template>

<script>
// import InputNickname from '@/components/Main/modules/InputNickname.vue'
import InputText from '@/components/Main/modules/InputText.vue'
export default {
  components: {
    // InputNickname, 
    InputText,
  },
  
}
</script>


<style scoped>

</style>