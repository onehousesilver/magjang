<template>
  <b-button>{{ $route.query.code }}</b-button>
</template>

<script>
import { mapActions } from 'vuex'
import axios from 'axios'

export default {
  mounted() {
    this.sendToken(this.$route.query.code);
    // 토큰이 있든 없든, 루트 페이지로 이동한다.
    this.$router.replace('/')
  },
  methods: {
    ...mapActions([
      'setUser'
    ]),
    sendToken(code) {
      axios.get('/account/kakaologinrequest', {
        params: {
          "code": code,
        }
      })
        .then(response => {
          this.setUser(response.data)
          // console.log(response.data);
          
          // console.log(this.$store.getters.userId); //Debug
        })
        .catch(error =>{
          console.log(error)
        });
    }
  },
}
</script>

<style>

</style>