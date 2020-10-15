import Vue from 'vue'
import Vuex from 'vuex'
import VueCookies from 'vue-cookies'

Vue.use(Vuex)
Vue.use(VueCookies)

export default new Vuex.Store({
    state: {
        isLogin: !!window.$cookies.get('auth-token'),
        SERVER_URL: 'http://i3d202.p.ssafy.io:8000',
        LOCAL_URL: 'http://i3d202.p.ssafy.io',
        userNickname: window.$cookies.get('userNickname'),
    },
    mutations: {
        storeLogin(state, payload) {
            window.$cookies.set('auth-token', payload.token)
            window.$cookies.set('userNickname', payload.user.userNickname)
            state.isLogin = true
            state.userNickname = payload.user.userNickname
            state.imageUrl = payload.user.imageUrl
        },
        storeLogout(state) {
            state.isLogin = false
            state.userNickname = null
            state.imageUrl = null
            window.$cookies.remove('auth-token')
            window.$cookies.remove('userNickname')
        },
    },
    actions: {
    },
    modules: {
    }
  
})
