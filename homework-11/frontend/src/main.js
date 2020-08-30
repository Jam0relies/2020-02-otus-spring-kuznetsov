import Vue from 'vue'
import Router from 'vue-router'
// import Meta from 'vue-meta'
import App from './App.vue'
import Authors from "./components/Authors";
import Genres from "./components/Genres";

Vue.use(Router)
// Vue.use(Meta)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      // component: Home,
    },
    {
      path: '/authors',
      name: 'authors',
      component: Authors
    },
    {
      path: '/genres',
      name: 'genres',
      component: Genres
    },
  ],
  mode: 'history'
})

new Vue({
  el: '#app',
  render: h => h(App),
  router
})
