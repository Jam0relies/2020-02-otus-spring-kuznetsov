<template>
  <div>
    <form class="login-form" v-on:submit.prevent="login">
      <div>Login</div>
      <label>username<input v-model="loginForm.username"/></label>
      <label>password<input v-model="loginForm.password" type="password"/></label>
      <button>send</button>
    </form>
  </div>
</template>
<script>
import httpResource from "../http/httpResource";
import router from "../router/router";
import {getAuthenticatedUser, parseApiErrors, performLogout} from "../util/utils";

export default {
  name: 'login',
  data() {
    return {
      loginForm: {
        username: "",
        password: ""
      },
      displayErrorMessage: false,
      errorMessage: "",
      loginInProcess: false
    };
  },
  methods: {
    async login() {
      this.loginInProcess = true;
      let canNavigate = false;
      const loginRequest = {
        username: this.loginForm.username,
        password: this.loginForm.password
      };
      try {
        const response = await httpResource.post("/api/auth/login", loginRequest);
        if (response.status === 200) {
          await getAuthenticatedUser();
          canNavigate = true;
        }
      } catch (error) {
        performLogout();
        const apierror = parseApiErrors(error);
        this.displayErrorMessage = true;
        this.errorMessage = apierror.message;
      }
      this.loginInProcess = false;
      if (canNavigate) {
        router.replace("/");
      }
    }
  }
}
</script>
<style>
.login-form {
  horiz-align: center;
}
</style>
