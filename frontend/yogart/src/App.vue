<template>
    <div id="app">
            <login-modal v-if="!isLogin" @loginComplete="loginComplete"></login-modal>
            <NavBar @logout="logout" :isLogin="isLogin"/>
            <div v-if="isPC">
                <router-view class="full-page" @submit-signup-data="signup" />
            </div>
            <div id="forMobileDiv" v-else class="text-align-left">
                <p class="mt-5">요가트는 크롬 브라우저에 최적화 되어 있습니다.</p>
                <p>모바일을 위한 서비스는 준비중입니다.</p>
            </div>
            <Footer/>
    </div>
</template>

<script>
import axios from 'axios'
import NavBar from '@/components/NavBar.vue'
import Footer from '@/components/Footer.vue'

export default {
    name: "App",
    data() {
        return {
            isLogin: this.$store.state.isLogin,
            SERVER_URL: this.$store.state.SERVER_URL,
            isPC: true,
        }
    },
    components: {
        NavBar,
        Footer,
    },
    methods: {
        signup(signupData) {
        axios.post(`${this.SERVER_URL}/api/users/signup`, signupData)
            .then(response => {
                if (response.data.statusCode === 200) {
                    this.$store.commit('storeLogin', response.data)
                    this.loginComplete()
                }
                else {
                    if (response.data.statusCode === 403) {
                        if (response.data.message === 'email') {
                            alert('이메일이 이미 존재합니다.')
                        } else if (response.data.message === 'nickname') {
                            alert('닉네임이 이미 존재합니다.')
                        } else {
                            alert('이메일과 닉네임이 이미 존재합니다.')
                        }
                    }
                    else {  
                        alert('회원가입 실패')
                    }
                } 
            })
            .catch(err => {
                if (err.data.statusCode === 403) {
                    if (err.data.message === 'email') {
                        alert('이메일이 이미 존재합니다.')
                    } else if (err.data.message === 'nickname') {
                        alert('닉네임이 이미 존재합니다.')
                    } else {
                        alert('이메일과 닉네임이 이미 존재합니다.')
                    }
                }
                else {  
                    alert('회원가입 실패')
                }
            
            })
        },
        showLoginModal() {
			// 로그인 모달 창을 보여줍니다.
			$('#loginStaticBackdrop').modal('show')
		},
		loginComplete() {
			this.isLogin = true
			$('#loginStaticBackdrop').modal('hide')
			this.$router.push({ name: 'Home' })
			window.location.reload()
		},
		logout() {
			// 로그아웃은 쿠키를 삭제하는 것으로 마무리합니다.
			this.isLogin = false
			this.$store.commit('storeLogout')
			// 로그아웃이 완료되면 사용자를 홈페이지로 던집니다.
			this.$router.push({ name: 'Home' })
			window.location.reload()
		},
		setSize() {
			let size = window.innerWidth
			if (size < 800) {
				this.isPC = false
			} else {
				this.isPC = true
			}
		}
    },
    created() {
        Kakao.init('688de69414ec5331cee58badb1cad1ea')
    },
    mounted() {
        window.addEventListener('resize', this.setSize)
    },
    updated() {
        window.scrollTo(0,0)
    }
}
</script>


<style>
*:not(i) {
  font-family: 'Gothic A1', sans-serif !important;
}

#app {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    background-image: url("./assets/back.jpg");
    background-repeat: repeat;
    background-size: 20%;
    color: #2c3e50;
}
.y-border{

  border-top : 2px solid black;
  border-bottom: 2px solid black;
  padding : 10px;
  width : 85%;
  margin : auto;
  margin-top : 30px;
}
.white-box{
    padding : 2rem;
    width : 75%;
    margin-top : 35px;
    background-color: rgba(255,255,255,0.5);
    margin : auto;
    border-radius: 5px;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}
.under-border{
   padding-bottom: 20px;
    margin : 30px auto;
    border-bottom : 2px solid rgba(143, 160, 242, 0.7);
}
.full-page {
    min-height: 100vh;
}
.padding-for-nav {  /* 다른 views에서 사용 */
    padding-top: 22vh;
    padding-bottom: 6vh;
}
.custom-break-word { /* 글자가 박스를 벗어나지 않도록 하기위해 사용 */
    word-wrap: break-word;
    white-space: pre-wrap;
    white-space: -moz-pre-wrap;
    white-space: -pre-wrap;
    white-space: -o-pre-wrap;
    word-break: break-all;
}
.tui-editor-contents > p { /* 글자가 박스를 벗어나지 않도록 하기위해 사용 */
    word-wrap: break-word;
    white-space: pre-wrap;
    white-space: -moz-pre-wrap;
    white-space: -pre-wrap;
    white-space: -o-pre-wrap;
    word-break: break-all;
    padding: 0rem 1rem;
}
pre {
    margin: 0.5rem 0rem 0rem;
}
.box {
  margin-top : 3px;
}

.margin-top {
    margin-top: 100px !important;
}

.user-profile {
  display: inline-block;
  width: 150px;
  height: 150px;
  border-radius: 50%;

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
} */
.shadow-box-pink{
    background-color: rgba(255,255,255,0.3);
    border: 1px solid;
    padding: 10px;
    box-shadow: 5px 10px #f29d8f;
}
.shadow-box{
  
    margin : 5px;
    background-color: rgba(255,255,255,0.5);
    border-radius: 10px;
    border: 1px solid white;
    box-shadow: 3px 3px rgba(0,0,0,0.1);
}

.user-level {
  position: absolute;
  top: 60px;
  left: 80px;
}


.btn-blue {
  transition-duration: 0.4s;
  background-color:rgba(143, 160, 242, 1);
  border: 3px solid rgba(143, 160, 242, 1);
  color: white;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;

  font-size: 24px;
  margin: 4px 2px;
  transition:800ms ease all;
  outline:none;
}
.btn-blue:hover {
  background-color:  rgba(255,255,255,0.3);
  color: rgba(143, 160, 242, 1);
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  margin: 4px 2px;
  cursor: pointer;
}



.btn1 {
  transition-duration: 0.4s;
  /* background-color:rgba(255,255,255,0.3);
  border: 3px solid #f29d8f;
  color: #f29d8f; */
  background-color: rgba(242, 157, 143,0.3);
  border: 5px solid #f29d8f;
  color: #f29d8f;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;

  font-size: 24px;
  margin: 4px 2px;
  transition:800ms ease all;
  outline:none;
}
.btn1:hover {
  background-color:  #2c3e50;
  border: 5px solid #2c3e50;
  color: #f29d8f;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  margin: 4px 2px;
  cursor: pointer;
}
.btn1:before,button:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: rgba(242, 157, 143,0.3);
  transition:400ms ease all;
}
.btn1:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.btn1:hover:before,.btn1:hover:after{
  width:100%;
  transition:800ms ease all;
}


.btn-white {
  transition-duration: 0.4s;
  /* background-color:rgba(255,255,255,0.3);
  border: 3px solid #f29d8f;
  color: #f29d8f; */
  background-color:rgba(255,255,255,0.3);
  border: 3px solid #f29d8f;
  color: #f29d8f;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  margin: 4px 2px;
  transition:800ms ease all;
  outline:none;
}
.btn-white:hover {
  background-color: #f29d8f;
  color: white;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  margin: 4px 2px;
  cursor: pointer;
}
.btn-white:before,.btn-white:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: rgba(242, 157, 143,0.3);
  transition:400ms ease all;
}
.btn-white:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.btn-white:hover:before,.btn-white:hover:after{
  width:100%;
  transition:800ms ease all;
}

.btn4 {
  transition-duration: 0.4s;
  /* background-color:rgba(255,255,255,0.3);
  border: 3px solid #f29d8f;
  color: #f29d8f; */
  background-color:#f29d8f;
  border: 3px solid #f29d8f;
  color: white;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;

  font-size: 24px;
  margin: 4px 2px;
  transition:800ms ease all;
  outline:none;
}
.btn4:hover {
  background-color:  rgba(255,255,255,0.3);
  color: #f29d8f;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  margin: 4px 2px;
  cursor: pointer;
}
.btn4:before,button:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: rgba(242, 157, 143,0.3);
  transition:400ms ease all;
}
.btn4:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.btn4:hover:before,.btn4:hover:after{
  width:100%;
  transition:800ms ease all;
}
.box {
  position: relative;
  display: inline-block;
  width: 450px;
  /* height: 100px; */
  border-radius: 5px;
  background-color: rgba(255, 255, 255, 0.0);
  box-shadow: 0 1px 2px rgba(0,0,0,0.15);
  transition: all 0.3s ease-in-out;
}

/* Create the hidden pseudo-element */
/* include the shadow for the end state */
.box::after {
  /* content: ''; */
  position: absolute;
  z-index: -1;
  width: 100%;
  height: 100%;
  opacity: 0;
  border-radius: 5px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
  transition: opacity 0.3s ease-in-out;
}
/* Scale up the box */
.box:hover {
  transform: scale(1.2, 1.2);
}

/* Fade in the pseudo-element with the bigger shadow */
.box:hover::after {
  opacity: 1;
}

::selection {
    background-color: rgba(242, 157, 143, 0.7);
    ;
    color: white;
}

::-webkit-scrollbar {
    width: 16px;
}

::-webkit-scrollbar-thumb {
    background-color: rgba(242, 157, 143, 0.3);
    background-clip: padding-box;
    border: 0.05em solid rgba(235, 235, 235, 0.5);
}

::-webkit-scrollbar-track {
    background-color: rgba(235, 235, 235, 0.5);
}

/* Buttons */
::-webkit-scrollbar-button:single-button {
    background-color: rgba(235, 235, 235, 0.5);;
    display: block;
    border-style: solid;
    height: 13px;
    width: 16px;
}

/* Up */
::-webkit-scrollbar-button:single-button:vertical:decrement {
    border-width: 0 8px 8px 8px;
    border-color: transparent transparent gray transparent;
}

/* Down */
::-webkit-scrollbar-button:single-button:vertical:increment {
    border-width: 8px 8px 0 8px;
    border-color: gray transparent transparent transparent;
}
.user-profile-small {
  display: inline-block;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border : 2px solid rgba(242, 157, 143);

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
}
.user-profile-mid {
  display: inline-block;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border : 2px solid rgba(242, 157, 143);

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
}
.user-profile-mid {
  display: inline-block;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border : 2px solid rgba(242, 157, 143);

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
}
#forMobileDiv {
  width: 800px;
  height: 2000px;
}
</style>

