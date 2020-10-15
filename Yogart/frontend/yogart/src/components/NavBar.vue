<template>
    <div id="navbar-container">
        <div class="navbar navbar-expand navbar-light fixed-top w-100" :class="{ 'navbar--hidden': !showNavbar, 'change--color': lastScrollPosition > 100 }">
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav nav-left">
                <li class="nav-item">
                    <router-link class="nav-link" to="/coaching/yogaposelist">AI coaching</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/classandteacher">1:1 PT</router-link>
                </li>
                </ul>
                <router-link class="navbar-brand" to="/"><img class="logo-img" src="../assets/logo.png" alt=""></router-link>
                <ul class="navbar-nav nav-right">
                <li class="nav-item dropdown">
                    <a class="nav-link-no" id="navbarDropdown" data-toggle="dropdown">
                    HELP
                    </a>
                    <div class="dropdown-menu dropdown-menu" aria-labelledby="navbarDropdown">
                        <router-link class="sub-item nav-link" to="/notice">공지사항</router-link>
                        <router-link class="sub-item nav-link" to="/qna">Q&A</router-link>
                    </div>
                </li>
                
                <li class="nav-item dropdown" v-if="isLogin">
                    <a class="nav-link" id="navbarDropdown" data-toggle="dropdown">
                    {{userNickname}}님
                    </a>
                    <div v-if="isAdmin" class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <router-link class="sub-item nav-link" to="/mypage">마이페이지</router-link>
                        <router-link class="sub-item nav-link" to="/notice/form">공지작성</router-link>
                        <router-link class="sub-item nav-link" to="/admin">강사지원관리</router-link>
                        <a class="sub-item nav-link" @click="logoutEmmit">로그아웃</a>
                    </div>
                    <div v-else-if="isTeacher" class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <router-link class="sub-item nav-link" to="/mypage">마이페이지</router-link>
                        <router-link class="sub-item nav-link" to="/teacherpage">수업관리</router-link>
                        <a class="sub-item nav-link" @click="logoutEmmit">로그아웃</a>
                    </div>
                    <div v-else class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <router-link class="sub-item nav-link" to="/mypage">마이페이지</router-link>
                        <a class="sub-item nav-link" @click="logoutEmmit">로그아웃</a>
                        <router-link class="sub-item nav-link small-size" to="/teacher-apply" >강사로 활동하기</router-link>
                    </div>
                </li>
                <li class="nav-item" v-else>
                    <a class="nav-link" @click="openLoginModal">LOGIN</a>
                </li>
                </ul>
            </div>
        </div>
    </div> 

</template>

<script>
import axios from 'axios'
export default {
    name: 'NavBar',
    data() {
        return {
            userNickname: this.$store.state.userNickname,
            userPic: "http://localhost:8000/api/users/profileImage?authToken=" + this.$cookies.get('auth-token'),
            showNavbar: true,
            lastScrollPosition: 0,
            isTeacher: null,
            isAdmin: null,
        }
    },
    methods: {
        openLoginModal() {
            if (this.isLogin) return
            $('#loginStaticBackdrop').modal('show')
        },
        logoutEmmit() {
            this.$emit('logout')
        },
        onScroll () {   // 스크롤 내리면 nav 사라지고 올리면 생기는 함수
            const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop
            if (currentScrollPosition < 0) {
                return
            }
            // Stop executing this function if the difference between
            // current scroll position and last scroll position is less than some offset
            if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
                return
            }
            this.showNavbar = currentScrollPosition < this.lastScrollPosition
            this.lastScrollPosition = currentScrollPosition
        }

    },
    mounted () {
        window.addEventListener('scroll', this.onScroll)
        // 해당 유저가 강사자격을 보유했는지 확인합니다.
        const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
        }
        const self = this
        async function getIsTeacher() {
            try {
                const res = await axios.get(self.$store.state.SERVER_URL + '/api/users/isTeacher', requestHeaders)
                self.isTeacher = res.data
            } catch(err) {
                console.error(err)
            }
        }
        async function getIsAdmin() {
            try {
                const res = await axios.get(self.$store.state.SERVER_URL + '/api/users/isAdmin', requestHeaders)
                self.isAdmin = res.data
            } catch (err) {
                console.error(err)
            }
        }
        getIsAdmin()
        getIsTeacher()
    },
    beforeDestroy () {
        window.removeEventListener('scroll', this.onScroll)
    },
    props: {
        isLogin: Boolean
    },
}

</script>

<style scoped>
#navbar-container{
    height: 8vh;
}
.logo-img {
    height: 8vh;
}
.navbar {
    transform: translate3d(0, 0, 0);
    transition: 0.1s all ease-out;
    padding: 0 !important;
}
.navbar.navbar--hidden {
  box-shadow: none;
  transform: translate3d(0, -100%, 0);
}

.navbar.change--color {
    background-color: rgba(255, 255, 255, 0.5) !important;
}

.navbar-brand {
    text-align: center;
    margin: 0 auto !important;
}
.nav-link-no {
    color: gray !important;
    position: relative;
    font-size: 1.2rem;
    font-weight: 500;
    padding: 1rem !important;
    cursor: pointer;
    display: block;
}
.nav-link-no:after {    
  content: "";
  display: block;
  height: 4px;
  background: rgba(242, 157, 143, 1);
  transition: width 0.7s ease 0s, left 0.3s ease 0s;
  width: 0;
}
.nav-link-no:hover:after { 
  width: 3rem !important;
}
.nav-link {
    color: gray !important;
    position: relative;
    font-size: 1.2rem;
    font-weight: 500;
    padding: 1rem !important;
    cursor: pointer;
}
.nav-link:after {    
  content: "";
  display: block;
  height: 4px;
  background: rgba(242, 157, 143, 1);
  transition: width 0.7s ease 0s, left 0.3s ease 0s;
  width: 0;
}
.nav-link:hover:after { 
  width: 8rem !important;
}
.router-link-active {
    color: rgba(143, 160, 242, 1) !important;
}
.nav-right {
    position: fixed;
    right: 1rem;
}
.nav-left {
    position: fixed;
}

.user-profile-img {
  display: inline-block;
  width: 200px;
  height: 200px;
  border-radius: 50%;

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
} 

.dropdown-menu {
    display: none;
    background-color: rgba(255, 255, 255, 0) !important;
    border: none !important;
    padding: 0 !important;
    top: 8vh !important;
}
.dropdown-menu-right {
    position: fixed !important;
     right: 0 !important;
}
.dropdown:hover .dropdown-menu {
    display: block;
    margin-top: 0;
 }

.sub-item {
    text-align: center;
}

.small-size {
    font-size: 14px;
    color: gray !important;
    position: relative;
    padding: 1rem !important;
    cursor: pointer;
}

.small-size:after {    
  content: "";
  display: block;
  height: 0px !important;
  width: 0px !important;
}

</style>