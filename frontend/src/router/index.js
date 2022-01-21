import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Main/Home.vue";
import Nickname from "@/views/Main/Nickname.vue";
import Member from "@/views/Main/Member.vue"; 
import JoinRoom from "@/views/Main/JoinRoom.vue"; 

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/nickname",
    name: "Nickname",
    component: Nickname,
  },
  {
    path: "/member",
    name: "Member",
    component: Member,
  },
  {
    path: "/joinroom",
    name: "JoinRoom",
    component: JoinRoom,
  },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;