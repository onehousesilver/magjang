import { createApp } from 'vue'
import App from './App.vue'

// bootstrap
import BootstrapVue3 from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

import router from './router'
import store from './store'

// alert
import VueSweetalert2 from 'vue-sweetalert2'
import 'sweetalert2/dist/sweetalert2.min.css'

//mitt
import mitt from './mitt/mitt.js'

createApp(App).use(store).use(router).use(BootstrapVue3).use(VueSweetalert2).use(mitt).mount('#app')
