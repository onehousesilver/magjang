<template>
  <!--수정 필요 -->
  <div id="timer">
    <div class="clock">
      <span class="minutes">{{ minutes }}</span>
      <span class="middle"> : </span>
      <span class="seconds">{{ seconds }}</span>
    </div>
    <!-- skip 버튼 -->
    <button
      class="btn"
      v-if="skipButton"
      @click="skipTimer">
      skip
    </button>
    <!-- start버튼 -->
    <button
      class="btn"
      v-else
      @click="startTimer">
      start
    </button>
  </div>
</template>

<script>
export default {
	name:"GameTimer",
		computed: {
			minutes() {
				const minutes = Math.floor(this.totalTime / 60);
				return this.padTime(minutes);
			},
			seconds() {
				const seconds = this.totalTime - (this.minutes * 60);
				return this.padTime(seconds);
			}
    },
    data() {
			return{
				timer: null,
				totalTime: (3* 60),
				skipButton: false,
			};
    },
    methods: {
			startTimer() {
				this.timer = setInterval(() => 
					this.countdown(), 1000);
					this.skipButton = true;
			},
			skipTimer() {
				this.totalTime = (3*60);
				clearInterval(this.timer);
				this.timer = null;
				this.skipButton = false;
			},
			padTime(time) {
				return (time < 10? '0' : '') + time;
			},
			countdown() {
				if(this.totalTime >= 1) {
						this.totalTime--;
				} else{
						this.totalTime = 0;
						this.skipTimer();
				}

				if(this.totalTime == 0){
						this.skipButton = false;
				}
			}
    },

}
</script>

<style scoped>
#timer {
	width: 100px;
	height: 100px;
	border-radius: 50%;
	background-color: rgb(236, 186, 0);
	text-align: center;
	padding: 10px;
	font-size: 20px;
	margin-top: 20px;
}
</style>