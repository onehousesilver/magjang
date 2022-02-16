<template>
  <div
    class="ov-video-wrap"
    v-if="streamManager"
    :key="streamManager.stream.connection.connectionId">
    <ov-video
      :stream-manager="streamManager" />
    <div
      v-if="this.$store.state.gamePossible"
      class="user-ability">
      <UserAbility :abilities-array="abilitiesArray" />
    </div>
    <p class="video-nickname">
      {{ clientData }}
    </p>
  </div>
</template>

<script>
import OvVideo from '@/components/GameMain/modules/OvVideo.vue';
import UserAbility from '@/components/GameMain/modules/UserAbility.vue'
export default {
	name: 'UserVideo',

	components: {
		OvVideo,
    UserAbility
	},

	props: {
		// streamManager: Object,
		streamManager: {
      type: Object,
      default: null,
    },
    abilitiesArray: {
      type: Array,
      default: undefined,
    }
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>

<style scoped>
.ov-video-wrap {
  position: relative;
  height: 100%;
  width: 70%;
  top: 50%;  
  left: 50%;
  transform: translate(-50%, -50%);
}

/* 이거 사실 이렇게 하면 안되긴 하는데 좀 더 손보기.. 이렇게 하면 다른 화면에 어그러질수도 */
.ov-video-wrap p {
  background-color: black;
  color: #fff;
  position: absolute;
  top: 87%;
  right: 2%;
  font-size: 20px;
}

.ov-video-wrap:hover {
  cursor: pointer;
}

.user-ability {
  display: flex;
  justify-content: center;
}
</style>
