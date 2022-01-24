import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Main/Home.vue";
import redirect from "@/views/Main/redirect.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: '/redirect',
    name: 'redirect',
    component: redirect
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;