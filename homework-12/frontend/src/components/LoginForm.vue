<template>
  <div>
    <form v-on:submit.prevent="performLogin">
      <div>Login</div>
      <label>login<input v-model="login"/></label>
      <label>password<input v-model="password" type="password"/></label>
      <button>send</button>
    </form>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'login',
  data() {
    return {login: "", password: ""}
  },
  methods: {
    performLogin(event) {
      event.preventDefault();
      let formData = new FormData();
      formData.set("username", this.login);
      formData.set("password", this.password);
      axios.post('http://localhost:8080/api/login', formData, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
        .then((result) => {
          // this.$store.commit('loginSuccess')
          window.location = '/books'
        })
        .catch((error) => {
          console.error(error)
        })
    },
  }
}
</script>
