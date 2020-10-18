import LoginForm from "../components/LoginForm";
import Authors from "../components/Authors";
import Genres from "../components/Genres";
import Books from "../components/Books";
import BookInfo from "../components/BookInfo";
import Home from "../components/Home";
import Users from "../components/Users";


const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      authorities: ["user"]
    },
  },
  {
    path: '/login',
    name: 'login',
    component: LoginForm,
    props: true,
    meta: {
      authorities: []
    },
  },
  {
    path: '/authors',
    name: 'authors',
    component: Authors,
    meta: {
      authorities: ["user"]
    },
  },
  {
    path: '/genres',
    name: 'genres',
    component: Genres,
    meta: {
      authorities: ["user"]
    },
  },
  {
    path: '/books',
    name: 'books',
    component: Books,
    meta: {
      authorities: ["user"]
    },
  },
  {
    path: '/books/:id',
    name: 'book',
    component: BookInfo,
    props: true,
    meta: {
      authorities: ["user"]
    },
  },
  {
    path: '/users',
    name: 'users',
    component: Users,
    meta: {
      authorities: ["admin"]
    }
  },
]

export default routes;
