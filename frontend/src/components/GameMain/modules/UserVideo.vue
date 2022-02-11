<template>
  <div
    class="ov-video-wrap"
    v-if="streamManager"
    :key="streamManager.stream.connection.connectionId">
    <ov-video :stream-manager="streamManager" />
    <di class="video-nickname">
      <p>{{ clientData }}</p>
    </di>
  </div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
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
      console.log("이거 찍히나?")
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
  height: 90%;
  width: 100%;
  top: 50%;  
  left: 50%;
  transform: translate(-50%, -52%);
  overflow: hidden;
}

.ov-video-wrap .video-nickname p {
  background-color: black;
  color: #fff;
  display: inline;
  margin-left: 50px;
}
</style>
