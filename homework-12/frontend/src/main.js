import Vue from 'vue'
import Router from 'vue-router'
// import Meta from 'vue-meta'
import App from './App.vue'
import router from "./router";

Vue.use(Router)
// Vue.use(Meta)


new Vue({
  el: '#app',
  render: h => h(App),
  router
})
