<template>
    <div class="user" id="join"> 
        <div class="wrapC table">
        <form class="form-wrap">
            <div class="middle">
                <h1>회원가입</h1>
                <!-- <div class="form-wrap"> -->
                 
                    <div class="input-wrap">
            
                        <input v-model="this.formdata.nickname"
                            id="nickname"
                            placeholder="닉네임을 입력해주세요" type="text"/>
                    </div>

                    <div class="input-wrap">
                        <input v-model="this.formdata.email" 
                            id="email"
                            placeholder="이메일을 입력해주세요"
                            type="text"/>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="this.formdata.password"
                            id="password" 
                            :type="passwordType"
                            placeholder="비밀번호를 입력해주세요"/>
                        <span :class="{active : passwordType==='text'}">
                                <i class="fas fa-eye"></i>
                            </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="this.formdata.passwordConfirm" 
                            id="password-confirm"
                            :type="passwordConfirmType"
                            placeholder="비밀번호를 한번 더 입력해주세요"/>
                        <span :class="{active : passwordConfirmType==='text'}">
                                <i class="fas fa-eye"></i> 
                            </span>
                    </div>
                </div>

                <label>
                    <input v-model="isTerm" type="checkbox" id="term"/>
                    <span>약관에 동의합니다</span>
                </label>

                <span class="go-term">약관 보기</span>

                <button class="btn" @click="submit">
                    <span>
                        작성완료
                    </span>
                </button>
            </form>
            <!-- </div> -->


        </div> 
        

    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import axios from 'axios'
    

    export default {
        components: {
        },
        created(){

        },
        methods: {
            checkTerm() {
                this.formdata.isTerm = !this.formdata.isTerm
            },
            submit() {
                console.log('회원가입합니다.')

                // const {nickname, email, password, passwordConfirm } = this.formdata
                // console.log(this.formdata)
                // console.log(this.nickname,this.email,this.password,this.passwordConfirm)

                if (!this.formdata.nickname || !this.formdata.email || !this.formdata.password || !this.formdata.passwordConfirm) {
                    if (!this.formdata.nickname) {console.log('nickname을 입력해주세요')}
                    else if (!this.formdata.email) {console.log('email을 입력해주세요')}
                    else if (!this.formdata.password) {console.log('password을 입력해주세요')}
                    else {console.log('passwordConfirm을 입력해주세요')}
                    alert('모든 항목을 입력해주세요!')

                }
                if (this.formdata.password !== this.formdata.passwordConfirm) {
                    alert('비밀번호가 일치하지 않습니다!')
                    return               
               }
            //    let formdata = new formData();
            //    formdata.append("nickname", this.nickname,"email", this.email, "password",this.password);
                
                axios.post('https://localhost:8080/account/signup', this.formdata)
                
                
                
                .then(res=> {
                    console.log(res)
                    alert(res,'회원가입이 완료되었습니다.')
                    this.$router.push('/')
                })
                .catch(err=> {
                    alert(err.response.data.msg)
                })
                
                
               
            }
        },
        watch: {
        },
        data: () => {
            return {
               formdata : {
                email: '',
                nickname: '',
                password: '',
                passwordConfirm: '',
                isTerm: false,
            },
                passwordType:"password",
                passwordConfirmType:"password",
                
            }
        }

    }

</script>


