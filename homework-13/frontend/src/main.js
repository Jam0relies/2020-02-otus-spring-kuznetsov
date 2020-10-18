import Vue from 'vue'
import Router from 'vue-router'
import store from "./store";
// import Meta from 'vue-meta'
import App from './App.vue'
import router from "./router/router";
// import "core-js/stable";
// import "regenerator-runtime/runtime";
import {getAuthenticatedUser, refreshToken} from "./util/utils";

Vue.use(Router)

// Vue.use(Meta)


async function init() {
  await getAuthenticatedUser();
  router.beforeEach(async (to, from, next) => {
    try {
      if (to.path !== "/login" && !store.getters.getIsAuthenticated) {
        const statusCode = await refreshToken();
        if (statusCode !== 200) {
          console.log("refresh failed")
          next("/login");
        } else {
          await getAuthenticatedUser();
        }
      }
      const userAuthorities = store.getters.getCurrentUser.authorities;
      console.log("User authorities:" + userAuthorities)
      console.log("Route authorities: " + to.meta.authorities)
      if (!to.matched.some(record => to.meta && to.meta.authorities && record.meta.authorities.every(a => userAuthorities.includes(a)))) {
        console.log("Authorities does not match. Redirect to home")
        next("/")
      }
      next();
    } catch (error) {
      console.log(error)
      next("/login");
    }
  });

  new Vue({
    el: '#app',
    render: h => h(App),
    router
  })
}

init();

