import LoginForm from "../components/LoginForm";
import Authors from "../components/Authors";
import Genres from "../components/Genres";
import Books from "../components/Books";
import BookInfo from "../components/BookInfo";
import Home from "../components/Home";


const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
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
]

export default routes;
