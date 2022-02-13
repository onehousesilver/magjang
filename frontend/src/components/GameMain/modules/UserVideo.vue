<template>
  <div
    class="ov-video-wrap"
    v-if="streamManager"
    :key="streamManager.stream.connection.connectionId">
    <ov-video :stream-manager="streamManager" />
    <div v-if="this.$store.state.gamePossible">
      <UserAbility />
    </div>
    <p class="video-nickname">
      {{ clientData }}
    </p>
  </div>
</template>

<script>
import OvVideo from './OvVideo';
import UserAbility from './UserAbility.vue'
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

<style>
.ov-video-wrap {
  position: relative;
  height: 100%;
  width: 70%;
  top: 50%;  
  left: 50%;
  transform: translate(-50%, -50%);
}

.ov-video-wrap p {
  background-color: black;
  color: #fff;
  display: inline-block;
  margin-left: 10px;
  font-size: 15px;
}
</style>
