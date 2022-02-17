<template>
  <div v-if="isVoter">
    <div class="deal-text">
      <span style="font-size:20px; font-weight: 700;">🤝거래를<br />체결하시겠습니까?</span> <br />
      <span>* 거래를 체결하지 않을 경우 거래는 <span style="font-weight: 700;">실패</span>하게 됩니다.</span> <br />
      <span>* 혼자 배신한다면 이득을 얻을 수 있지만, 2명이상 배신할 경우 패널티가 부여됩니다.</span>
    </div>
    <div class="btns">
      <button
        type="button"
        class="btn btn-ok btn-success"
        @click="voteOk">
        체결
      </button>
      <button
        type="button"
        class="btn btn-nope btn-danger"
        @click="voteNo">
        미체결
      </button>
    </div>
  </div>
  <div v-else> 
    <div class="deal-text">
      <span>*투표가 진행중입니다. 잠시만 기다려주세요.</span> <br />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 자신이 투표자인지 알려주는 boolean
      isVoter: this.$store.getters.voter,
    };
  },
  method:{
    voteOk(){
      this.emitter.emit('vote', true);
    },
    voteNo(){
      this.emitter.emit('vote', false);
      this.$store.getters.voter = false;
    },
  },
}
</script>

<style scoped>
.deal-text {
  font-size: 16px;
}

.btns {
  margin: 10px;
  display: flex;
  justify-content: space-evenly;
}
</style>