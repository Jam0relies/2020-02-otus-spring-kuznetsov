import Router from "vue-router";
import LoginForm from "./components/LoginForm";
import Authors from "./components/Authors";
import Genres from "./components/Genres";
import Books from "./components/Books";
import BookInfo from "./components/BookInfo";

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: LoginForm
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
    {
      path: '/books',
      name: 'books',
      component: Books
    },
    {
      path: '/books/:id',
      name: 'book',
      component: BookInfo,
      props: true
    },
    {
      path: '/login',
      name: 'login',
      component: LoginForm,
      props: true
    },
  ],
  mode: 'history'
})
export default router;
