<template>
<div class="modal fade" id="loginStaticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <img class="logo-img" src="../../assets/logo.png" alt="">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <span class="login-reject" v-if="errorState">이메일 혹은 비밀번호를 다시 확인하세요.</span>
                <span class="login-reject" v-if="serverState">연결할 수 없습니다. 잠시 후 다시 시도해주세요.</span>
                <ValidationObserver v-slot="{ invalid }">
                    <br>
                    <ValidationProvider name="email" rules="required|email" v-slot="{ errors }">
                        <input class="id-input" @keyup.enter="onSubmit" v-model="loginData.userEmail" type="text" placeholder="이메일"><br>
                        <span class="login-reject text-right">{{ errors[0] }}</span>
                    </ValidationProvider>
                    <br>
                    <ValidationProvider rules="required|min:6" v-slot="{ errors }">
                        <input class="password-input" @keyup.enter="onSubmit" type="password" v-model="loginData.userPassword" placeholder="비밀번호"><br>
                        <span class="login-reject">{{ errors[0] }}</span>
                    </ValidationProvider>
                    <br>
                    <button type="submit" class="login-btn" :disabled="invalid" @click="onSubmit">로그인</button>
                </ValidationObserver>
                <div class="for-line">
                    <div class="social-text">소셜 계정으로 간편하게 로그인하세요!</div>
                </div>
                <div class="my-5">
                    <kakaoLogin @loginComplete="login" class="mb-2"></kakaoLogin>
                    <naverLogin @loginComplete="login"></naverLogin>
                </div>
                <div>
                    아직 회원이 아니신가요? <a class="signup-link" href="/accounts/signup">회원 가입하기</a>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import axios from 'axios'
import naverLogin from '@/components/sociallogin/naverLogin.vue'
import kakaoLogin from '@/components/sociallogin/kakaoLogin.vue'

export default {
    name: 'login-modal',
    data () {
        return {
            loginData: {
                userEmail: null,
                userPassword: null,
            },
            errorState: null,
            serverState: null,
            API_URL: this.$store.state.SERVER_URL,
        }
    },
    components: {
        naverLogin,
        kakaoLogin,
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()
            axios.post(this.API_URL + '/api/users/login', this.loginData)
            .then(res => {
                // 로그인이 실패했다면 errorState에 status code를 저장해 오류를 출력합니다.
                this.login(res.data)
            })
            .catch(err => {
                this.errorState = true
                this.loginData.userPassword = null
            })
        },
        login(data) {
            if (data.token === null){
                this.serverState = true
            }
            this.$store.commit('storeLogin', data)
            this.$emit('loginComplete')
        }
    },
}
</script>

<style scoped>
.modal-header {
    padding-bottom: 4rem;
    border: none;
}
.logo-img {
    position: absolute;
    text-align: center;
    height: 8vh;
    top: 3rem;
    left: calc(234px - 9vh);
}
.id-input, .password-input {
    border-color: rgba(0, 0, 0, 0.2);
    border-width: 0px 0px 1.2px;
    width: 70%;
    padding-top: 1rem;
    outline: none;
}
.login-reject {
    background-color: rgba(242,157,143,0.2);
}
.login-btn {
    width: 70%;
    height: 3rem;
    color: white;
    font-size: 17px;
    margin-top: 1rem;
    border-radius: 3rem;
    border-width: 0px 0px 1.2px;
    border-color: rgba(255, 255 255, 0.2);
    background: linear-gradient(153deg, rgba(242,157,143,0.8) 0%, rgba(143,160,242,0.8) 100%);
    outline:none;
    cursor: pointer;
}
.login-btn:hover {
    background: linear-gradient(153deg, rgba(242,157,143,0.7) 0%, rgba(143,160,242,0.7) 100%);
}
.for-line {
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
    width: 70%;
    margin: auto;
    padding-top: 1rem;
}
.social-text {
    display: inline;
    position: relative;
    color: rgba(0, 0, 0, 0.4);
    font-weight: bold;
    top: 0.8rem;
    background-color: white;
    padding: 0px 2px;
}
.signup-link {
    color: rgba(242,157,143,1);
    font-weight: bold;
}
</style>