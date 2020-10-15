import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import store from '../store'
import Home from '../views/Home.vue'
import SignupView from '../views/accounts/SignupView.vue'
import GraphView from '../views/mypage/GraphView.vue'
import Callback from '../components/sociallogin/naverLogin.vue'
import QnaView from '../views/qna/QnaView.vue'
import QnaCreate from '../views/qna/QnaCreate.vue'
import NoticeListView from '../views/notice/NoticeListView.vue'
import NoticeDetailView from '../views/notice/NoticeDetailView.vue'
import NoticeFormView from '../views/notice/NoticeFormView.vue'
import MyPage from '../views/mypage/MyPage.vue'
import UserUpdate from '../views/mypage/UserUpdate.vue'
import TeacherList from '../views/teacher/TeacherList.vue'
import TeacherDetail from '../views/teacher/TeacherDetail.vue'
import TeacherApply from '../views/teacher/TeacherApply.vue'
import AICoachingPage from '../views/coaching/AICoachingPage.vue'
import AICoachingResultPage from '../views/coaching/AICoachingResultPage.vue'
import YogaPoseListPage from '../views/coaching/YogaPoseListPage.vue'
import YogaPoseListDetailPage from '../views/coaching/YogaPoseListDetailPage.vue'
import YogaPosePage from '../views/coaching/YogaPosePage.vue'
import ClassSetting from '../components/teacher/ClassSetting.vue'
import ClassAndTeacherList from '../views/ClassAndTeacherList.vue'
import TeacherPage from '../views/teacher/TeacherPage.vue'
import SpoonPurchase from '../views/spoon/SpoonPurchase.vue'
import PayComplete from '../views/kakaopay/PayComplete.vue'
import PayCancel from '../views/kakaopay/PayCancel.vue'
import PayFail from '../views/kakaopay/PayFail.vue'
import AdminPage from '../views/admin/AdminPage.vue'

var getCookie = function(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
    }

const requireAuth = () => (from, to, next) => {
    if (store.state.isLogin) return next()
    $('#loginStaticBackdrop').modal('show')
}

const requireUNAuth = () => (from, to, next) => {
    if (!store.state.isLogin) return next()
    return next('/')
}

const requireTeacher = () => (from, to, next) => {
    // 해당 유저가 강사자격을 보유했는지 확인합니다.
    const requestHeaders = {
        headers: {
            Authorization: getCookie('auth-token')
        }
    }
    axios.get(store.state.SERVER_URL + '/api/users/isTeacher', requestHeaders)
    .then(res => {
        if (res.data) {
            return next()
        }
        return next('/')
    })
    .catch(err => {
        console.error(err)
        next(false)
    })
}

const requireAdmin = () => (from, to, next) => {
    const requestHeaders = {
        headers: {
            Authorization: getCookie('auth-token')
        }
    }
    axios.get(store.state.SERVER_URL + '/api/users/isAdmin', requestHeaders)
    .then(res => {
        if (res.data) {
            return next()
        }
        return next('/')
    })
    .catch(err => {
        console.error(err)
        next(false)
    })
}

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    // Kakao pay
    {
        path: '/kakaoPay/Success',
        name: 'PayComplete',
        component: PayComplete
    },
    {
        path: '/kakaoPay/Cancel',
        name: 'PayCancel',
        component: PayCancel
    },
    {
        path: '/kakaoPay/Fail',
        name: 'PayFail',
        component: PayFail
    },
    // Spoon
    {
        path: '/spoons',
        name: 'SpoonPurchase',
        component: SpoonPurchase,
        beforeEnter: requireAuth()
    },
    // All class
    {
        path: '/classandteacher',
        name: 'ClassAndTeacherList',
        component: ClassAndTeacherList
    },
    // Teacher
    {
        path: '/teacherpage',
        name: 'TeacherPage',
        component: TeacherPage,
        beforeEnter: requireTeacher()
    },
    {
        path: '/teachers',
        name: 'TeacherList',
        component: TeacherList,
    },
    {
        path: '/teachers/:teacher_id',
        name: 'TeacherDetail',
        component: TeacherDetail,
    },
    {
        path: '/teacher-apply',
        name: 'TeacherApply',
        component: TeacherApply,
        beforeEnter: requireAuth()
    },
    {
        path: '/naver/callback',
        name: 'Callback',
        component: Callback,
    },
    {
        path: '/accounts/signup',
        name: 'SignupView',
        component: SignupView,
    },
    {
        path: '/mypage',
        name: 'MyPage',
        component: MyPage,
        beforeEnter: requireAuth(),
    },    
    {
        path: '/mypage/graph',
        name: 'Graph',
        component: GraphView,
        beforeEnter: requireAuth(),
    },
    {
        path: '/mypage/update',
        name: 'UserUpdate',
        component: UserUpdate,
        beforeEnter: requireAuth(),
    },
    // Notice
    {
        path: '/notice',
        name: 'NoticeList',
        component: NoticeListView
    },
    {
        path: '/notice/form',
        name: 'NoticeForm',
        component: NoticeFormView,
        beforeEnter: requireAdmin()
    },
    {
        path: '/notice/:notice_id',
        name: 'NoticeDetail',
        component: NoticeDetailView
    },
    // Q&A
    {
        path: '/qna',
        name: 'QnaView',
        component: QnaView
    },
    {
        path: '/qna/question',
        name: 'QnaCreate',
        component: QnaCreate,
        beforeEnter: requireAuth(),
    },
    // AI coaching
    {
        path: '/coaching',
        name: 'AICoachingPage',
        component: AICoachingPage,
        beforeEnter: requireAuth(),
    },
    {
        path: '/coaching/result',
        name: 'AICoachingResultPage',
        component: AICoachingResultPage,
        beforeEnter: requireAuth(),
      },
      {
        path: '/coaching/yogaposelist',
        name: 'YogaPoseListPage',
        component: YogaPoseListPage,
        beforeEnter: requireAuth(),
      },
      {
        path: '/coaching/yogaposelist/detail',
        name: 'YogaPoseListDetailPage',
        component: YogaPoseListDetailPage,
        beforeEnter: requireAuth(),
      },
      {
        path: '/coaching/yogapose',
        name: 'YogaPosePage',
        component: YogaPosePage,
        beforeEnter: requireAuth(),
      },
      // Admin
      {
          path: '/admin',
          name: 'AdminPage',
          component: AdminPage,
          beforeEnter: requireAdmin()
      }
]

    const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
    })

export default router