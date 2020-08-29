import Vue from 'vue'
// import Meta from 'vue-meta'
import App from './App.vue'

// Vue.use(Router)
// // Vue.use(Meta)
//
// const router = new Router({
//   routes: [
//     // {
//     //   path: '/',
//     //   name:'home',
//     //   // component: Authors,
//     // },
//     {
//       path: '/authors',
//       name: 'authors',
//       component: Authors
//     }
//     // {
//     //   path: '/post/:id',
//     //   name:'post',
//     //   component: Post,
//     //   props: true,
//     // },
//   ],
//   mode: 'history'
// })

new Vue({
  el: '#app',
  render: h => h(App)
})
