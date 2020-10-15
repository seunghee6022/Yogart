import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'
import LoginModal from './components/login/LoginModal.vue'
import { ValidationObserver, ValidationProvider, extend } from 'vee-validate'
import { required, email, min } from 'vee-validate/dist/rules'
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Carousel3d from 'vue-carousel-3d';


Vue.use(Carousel3d);
// Install BootstrapVue
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)


Vue.config.productionTip = false
Vue.use(VueCookies)
Vue.use(Editor)
Vue.use(BootstrapVue)

Vue.component(LoginModal.name, LoginModal)
Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)

extend('email', {
    ...email,
    message: '올바르지 않은 이메일 형식입니다.'
})
  
extend('required', {
    ...required,
    message: '필수 입력 항목입니다.'
})

extend('min', {
    ...min,
    message: '비밀번호는 8자리 이상'
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
