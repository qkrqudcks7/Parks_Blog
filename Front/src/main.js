// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VeeValidate from 'vee-validate'
import Notification from 'vue-notification'
import Pagination from 'vue-pagination-2'
import CKEditor from 'ckeditor4-vue'
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VeeValidate)
Vue.use(Notification)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(Pagination)
Vue.use(CKEditor)
Vue.config.productionTip = false

/* eslint-disable no-new */
const app = new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
export default app
