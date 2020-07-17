<template>
    <div class="user" id="login">
        <div class="wrapC table">
            <div class="middle">
                <h1>SS_log</h1>
                <!-- <form @submit.prevent="onSubmit(email, password)"> -->
                <div class="input-wrap">
                    <input v-model="email"
                        id="email" 
                        placeholder="이메일을 입력해주세요"
                        type="text"/>
                </div>
                <div class="input-wrap">
                    <input v-model="password" type="password"
                        id="password"
                        placeholder="영문, 숫자 혼용 8자 이상"/>
                </div>
                <button class="btn btn--back btn--login">
                    로그인 하기
                </button>
                <div class="add-option">
                    <div class="wrap">
                        <p>아직 회원이 아니신가요?</p>
                        <router-link v-bind:to="{name:constants.URL_TYPE.USER.JOIN}" class="btn--text">
                            회원가입
                        </router-link>
                    </div>

                </div>
                <!-- </form> -->
            </div>
            
        </div>

    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import constants from '../../lib/constants'
    
    export default {
        components: {
        },
        created(){
        },
        watch: {
        },
        methods: {
            onSubmit(email, password) {
                this.$stord.dispatch('LOGIN',{email, password})
                .then(()=> this.redirect())
                .catch(({message})=> this.msg = message)
            },
            redirect() {
                const {search} = window.location
                const tokens = search.replace('/^\/','').split('&')
                const {returnPath} = tokens.reduce((qs,tkn) =>{
                    const pair = tkn.split('=')
                    qs[pair[0]] = decodeURIComponent(pair[1])
                    return qs
                },{})

                this.$router.push(returnPath)
            }
        },
        data: () => {
            return {
                constants,
                email: '',
                password: '',
                msg:''

            }
        }

    }

</script>


