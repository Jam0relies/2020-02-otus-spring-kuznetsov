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
    if (to.path !== "/login" && !store.getters.getIsAuthenticated) {
      try {
        const statusCode = await refreshToken();
        if (statusCode !== 200) next("/login");
        else next();
      } catch (error) {
        next("/login");
      }
    } else {
      next();
    }
  });

  new Vue({
    el: '#app',
    render: h => h(App),
    router
  })
}

init();

