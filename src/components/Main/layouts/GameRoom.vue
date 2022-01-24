<template>
  <div v-if="roomEnterFlag">
    <InputText place-text="초대 코드를 입력해 주세요" />
    <button class="mj-btn">
      게임시작
    </button>
    <button
      class="mj-btn"
      @click="roomEnterFlag = false">
      뒤로가기
    </button>
  </div>
  <div v-else>
    <button class="mj-btn">
      방 개설
    </button>
    <button
      class="mj-btn"
      @click="roomEnterFlag = true">
      방 참가
    </button>
  </div>
</template>

<script>
import InputText from '@/components/Main/modules/InputText.vue'
export default {
  components: {
    InputText,
  },

  data(){
    return {
      roomEnterFlag: false
    }
  }
}
</script>

<style>

</style>