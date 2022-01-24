<template>
  <div v-if="nickname">
    <div v-if="roomEnterFlag">
      <EnterInviteCodeBtn />
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
  </div>
  <div v-else>
    <CreateNickname />
  </div>
  <!-- 로그아웃필요 -->
</template>

<script>
import CreateNickname from '@/components/Main/layouts/CreateNickname.vue'
import EnterInviteCodeBtn from '@/components/Main/modules/EnterInviteCodeBtn.vue'
import {mapGetters} from 'vuex'

export default {
  components: {
    CreateNickname,
    EnterInviteCodeBtn,
  },

  computed: {
    ...mapGetters([
      'nickname',
    ])
  },

  data(){
    return {
      roomEnterFlag: false
    }
  }

}
</script>

<style scoped>

</style>