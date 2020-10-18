import Router from "vue-router";
import routes from "./routes";

const router = new Router({
  routes: routes,
  mode: 'history'
})
export default router;
