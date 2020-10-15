<template>
    <div class="padding-for-nav">
        <div class="page-name">회원가입</div>
        <div class="container d-flex justify-content-center text-left milky-back">
            <div class="signup-pack">
                <div class="form-group">
                    <div class="form-mid-group">
                        <input class="signup-input" @keyup.enter="signup" placeholder="이메일" type="text" id="userEmail" v-model="signupData.userEmail" @keyup="checkEmail">
                    </div>
                </div>
                <br>
                <div class="form-group">
                    <div class="form-mid-group">
                        <input class="signup-input" @keyup.enter="signup" placeholder="이름" type="text" id="userName" v-model="signupData.userName" @change="checkName">
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-mid-group">
                        <input class="signup-input" @keyup.enter="signup" placeholder="닉네임" type="text" id="userNickname" v-model="signupData.userNickname" @change="checkNickname">
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-mid-group">
                        <input class="signup-input" @keyup.enter="signup" placeholder="비밀번호" type="password" id="userPassword" v-model="signupData.userPassword" @keyup="checkPassword">
                        <span class="allIcon" id="userPasswordCheckIcon"><i class="fas fa-check fa-2x"></i></span>
                    </div>
                    <small class="form-text text-muted">숫자, 영문 포함 8자 이상</small>
                </div>
                <div class="form-group">
                    <div class="form-mid-group">
                        <input class="signup-input" @keyup.enter="signup" placeholder="비밀번호 확인" type="password" id="userRePassword" v-model="signupData.userRePassword" @keyup="checkRePassword">
                        <span class="allIcon" id="userRePasswordCheckIcon"><i class="fas fa-check fa-2x"></i></span>
                    </div>
                </div>
                <button @click="signup" class="signup-btn">회원가입</button>
            </div> 
        </div>
    </div>
</template>

<script>

export default {
    name: 'SignupView',
    data() {
        return {
            signupData: {
                userEmail: '',
                userName: '',
                userNickname: '',
                userPassword: '',
                userRePassword: '',
            },
            formCheck: {
                userEmail: false,
                userName: false,
                userNickname: false,
                userPassword: false,
                userRePassword: false,
            },
        }
    },
    methods: {
        signup() {
            // App.vue로 이벤트 발생

            if (this.formCheck.userEmail === false) {
                alert('올바른 이메일을 입력해주세요.')
            } else if (this.formCheck.userName === false) {
                alert('올바른 이름을 입력해주세요.')
            } else if (this.formCheck.userNickname === false) {
                alert('올바른 닉네임을 입력해주세요.')
            } else if (this.formCheck.userPassword === false) {
                alert('숫자, 영문 포함 8자 이상 비밀번호를 입력해주세요')
            } else if (this.formCheck.userRePassword === false) {
                alert('비밀번호가 일치하지 않습니다.')
            } else {
                this.$emit('submit-signup-data', this.signupData)
            }

        },
        checkEmail() {
            // trim으로 공백 제거
            this.signupData.userEmail = this.signupData.userEmail.trim()
            // 이메일 형식(@와 .)을 갖추고 있고 45자 이하이면 체크 아이콘 색상 변경
            if (this.signupData.userEmail.includes('@') & this.signupData.userEmail.includes('.') & this.signupData.userEmail.length < 46) {      
                this.formCheck.userEmail = true
            } else {
                this.formCheck.userEmail = false
            }
        },
        checkName() {
            // trim으로 공백 제거
            this.signupData.userName = this.signupData.userName.trim()
            // 1~45자 사이면 체크 아이콘 색상 변경
            if (this.signupData.userName.length > 0 & this.signupData.userName.length < 46) {
                this.formCheck.userName = true
            } else {
                this.formCheck.userName = false
            }
        },
        checkNickname() {
            // trim으로 공백 제거
            this.signupData.userNickname = this.signupData.userNickname.trim()
            // 1~45자 사이면 체크 아이콘 색상 변경
            if (this.signupData.userNickname.length > 0 & this.signupData.userNickname.length < 46) {
                this.formCheck.userNickname = true
            } else {
                this.formCheck.userNickname = false
            }
        },
        checkPassword() {
            // 입력을 시작하면 체크 아이콘이 보이게 함
            document.getElementById('userPasswordCheckIcon').style.visibility = 'visible'
            // 영문과 숫자 체크
            var checkNumber = this.signupData.userPassword.search(/[0-9]/g);
            var checkEnglish = this.signupData.userPassword.search(/[a-z]/ig);
            // 영문과 숫자를 포함하고 8~45자 사이면 체크 아이콘 색상 변경
            if (checkNumber !== -1 & checkEnglish !== -1 & this.signupData.userPassword.length > 7 & this.signupData.userPassword.length < 46) {
                document.getElementById('userPasswordCheckIcon').style.color = '#08bf42'
                this.formCheck.userPassword = true
            } else {
                document.getElementById('userPasswordCheckIcon').style.color = '#bf0823'
                this.formCheck.userPassword = false
            }
        },
        checkRePassword() {
            // 입력을 시작하면 체크 아이콘이 보이게 함
            document.getElementById('userRePasswordCheckIcon').style.visibility = 'visible'
            // 비밀번호가 일치하면 체크 아이콘 색상 변경
            if (this.signupData.userRePassword === this.signupData.userPassword) {
                document.getElementById('userRePasswordCheckIcon').style.color = '#08bf42'
                this.formCheck.userRePassword = true
            } else {
                document.getElementById('userRePasswordCheckIcon').style.color = '#bf0823'
                this.formCheck.userRePassword = false
            }
        }
    },
}
</script>

<style scoped>
.page-name {
    margin: -4rem auto 2rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}
.form-group {
    display: inline-block;
    width: 100%;
    
}
.form-mid-group {
    width: 100%;
    border-style: solid;
    border-color: rgba(0, 0, 0, 0.2) !important;
    border-width: 0px 0px 1.2px !important;
}
.signup-pack {
    width: 500px;
    margin: 3rem auto;
    
}
.allIcon {
    color: #bf0823;
    visibility: hidden;
}
.signup-input {
    border: none;
    width: 450px;
    padding-top: 1rem;
    background-color: rgba(0, 0, 0, 0);
    outline: none;
}

.signup-btn {
    width: 500px;
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
.signup-btn:hover {
    background: linear-gradient(153deg, rgba(242,157,143,0.7) 0%, rgba(143,160,242,0.7) 100%);
}
.milky-back {
    width: 600px;
    background-color: rgba(255, 255, 255, 0.5) !important;
    border-radius: 5rem;
}
</style>