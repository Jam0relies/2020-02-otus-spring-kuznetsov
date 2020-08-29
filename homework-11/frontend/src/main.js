import Vue from 'vue'
import Router from 'vue-router'
// import Meta from 'vue-meta'
import App from './App.vue'
import Home from "./components/Home";

Vue.use(Router)
// Vue.use(Meta)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    // {
    //   path: '/authors',
    //   name: 'authors',
    //   component: Authors
    // }
    // {
    //   path: '/post/:id',
    //   name:'post',
    //   component: Post,
    //   props: true,
    // },
  ],
  mode: 'history'
})

new Vue({
  el: '#app',
  render: h => h(App),
  router
})
