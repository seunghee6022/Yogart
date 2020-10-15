<template>
    <div class="updateView padding-for-nav">
        <div class="page-name">프로필 변경</div>
        <div class="img-wrap">
            <img v-if="userImageUrl" @click="callImgChangeBtn()" class="user-profile-img" :src="userImageUrl" alt="user profile image">
            <img v-else @click="callImgChangeBtn()" class="user-profile-img" src="../../assets/userDefault.jpg" alt="user profile image">
        </div>
        <i @click="callImgChangeBtn()" class="camera fas fa-camera fa-3x"></i>
        <div class="input-wrap">
            <input @change="fileChange" type="file" ref="userImage" class="img-change-btn" id="user-image" accept=".jpg, .jpeg, .png">
        </div>

        <div class="info-pack container justify-content-center">
            <div class="d-block">
                <div class="form-group">
                    <label for="userNickname">닉네임</label>
                    <input class="info-input-box" type="text" id="userNickname" v-model="userNickname">
                    <label for="userIntro">한마디</label>
                    <input class="info-input-box" type="text" id="userIntro" v-model="userIntro">
                </div>
                <button @click="update" class="update-btn">수정</button>
                <div class="leave" @click="withdrawl">회원 탈퇴를 원하시나요?</div>
            </div> 
        </div>


        <b-modal ref="my-modal" hide-footer hide-header>
            <div class="ment-box">
                <h3>{{ ment }}</h3>
            </div>
            <b-button v-if="leave" class="mt-3" variant="outline-info" block @click="cancelDelete">남는다</b-button>
            <b-button v-if="leave" class="mt-3" variant="outline-danger" block @click="hideModal">떠난다</b-button>
            <b-button v-if="stay" class="mt-3" variant="outline-info" block @click="stayHere">그래</b-button>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'


export default {
    name: 'UserUpdate',
    data() {
        return {
            userName: null,
            id: 0,
            userNickname: null,
            userImageUrl: null,
            userIntro: null,
            SERVER_URL: this.$store.state.SERVER_URL,
            ment: "진짜 탈퇴를 원하시나요?",
            confirm: 0,
            leave: true,
            stay: false,
        }
    },
    created() {
        // 유저 정보를 받아옵니다.
        const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
        }
        axios.get(this.SERVER_URL + '/api/users/myInfo', requestHeaders)
        .then(res => {
            this.userName = res.data.userName
            this.id = res.data.id
            this.userNickname = res.data.userNickname
            this.userProfile = res.data.userProfile
            this.userIntro = res.data.userIntro
            this.userImageUrl = `${this.$store.state.SERVER_URL}/api/users/profileImage?authToken=` + this.$cookies.get('auth-token')
        })
        .catch(err => console.error(err))
    },
    methods: {
        callImgChangeBtn() {
            var myinput = document.getElementById('user-image')
            myinput.click()
        },
        withdrawl() {
            this.$refs['my-modal'].show()
        },
        stayHere() {
            this.$refs['my-modal'].hide()
            this.ment = "진짜 탈퇴를 원하시나요?"
            this.leave = true
            this.stay = false
        },
        cancelDelete() {
            this.$refs['my-modal'].hide()
            this.stay = true
            this.leave = false
            this.confirm = 0
            this.ment = "더 건강한 서비스로 보답하겠습니다. 감사합니다."
            this.$refs['my-modal'].show()
        },
        hideModal(){
            if (this.confirm === 0) {
                this.confirm++
                this.$refs['my-modal'].hide()
                this.ment = "진짜 진짜로 ( ˃̣̣̥᷄⌓˂̣̣̥᷅ ) ???"
                this.$refs['my-modal'].show()
            } else if (this.confirm === 1) {
                this.confirm++
                this.$refs['my-modal'].hide()
                this.ment = "알겠습니다.. 보내드릴게요... 아프지말고... 건강하고...잘 가요."
                this.$refs['my-modal'].show()
            } else if (this.confirm === 2) {
                const requestHeaders = {
                    headers: {
                        Authorization: this.$cookies.get('auth-token'),
                    }
                }
                axios.delete(this.SERVER_URL + '/api/users/delete', requestHeaders)
                .then(res => {
			        this.$store.commit('storeLogout')
                    this.$router.push('/')
                    window.location.reload()
                })
                .catch(err => console.error(err))
            }
        },
        update() {
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token'),
                }
            }
            let fd = new FormData()
            fd.append('userName', this.userName)
            fd.append('id', this.id)
            fd.append('userNickname', this.userNickname)
            fd.append('userProfile', this.userProfile)
            fd.append('userIntro', this.userIntro)

            axios.put(this.SERVER_URL + '/api/users/myInfo/update', fd, requestHeaders)
            .then(res => {
                this.$router.replace({ name: 'MyPage' })
            })
            .catch(err => console.error(err))
        },
        fileChange(e) {
            var file = e.target.files[0]
            if (file && file.type.match(/^image\/(png|jpeg)$/)) {
                this.userImageUrl = window.URL.createObjectURL(file)
            }
            this.userImage = this.$refs.userImage.files[0]
            this.imageSubmit(e)
        },
        imageSubmit(e) {
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token'),
                    'Content-Type' : 'multipart/form-data',
                }
            }
            let fd = new FormData()
            fd.append('userImage', this.userImage)
            axios.post(this.SERVER_URL + '/api/users/profileUpload', fd, requestHeaders)
            .then(res => {
                alert('변경이 완료되었습니다.')
            })
            .catch(err => console.error(err))
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
.user-profile-img {
    display: inline-block;
    width: 200px;
    height: 200px;
    border-radius: 50%;
    border: 1px solid rgba(0, 0, 0, 0.2);
    background-repeat: no-repeat;
    background-position: center center;
    background-size: cover;
    cursor: pointer;
}
.camera {
    position: relative;
    padding: 15px;
    border-radius: 45px;
    background-color: white;
    top: -10vh;
    left: 10vh;
    cursor: pointer;
}
.camera:hover {
    background-color: rgba(190, 190, 190, 1);
}
.info-pack {
    margin-top: -5rem;
    width: 500px !important;
    text-align: left;
}

.input-wrap {
    display: none;
}

.info-input-box {
    background-color: rgba(0, 0, 0, 0);
    border-style: solid;
    border-color: rgba(0, 0, 0, 0.7);
    border-width: 0px 0px 1.2px;
    width: 500px;
    padding-top: 0rem;
    margin-bottom: 1rem;
    outline: none;
}
.update-btn {
    width: 500px;
    height: 3rem;
    color: white;
    font-size: 17px;
    margin-top: 1rem;
    border-radius: 3rem;
    border-width: 0px 0px 1.2px;
    border-color: rgba(255, 255 255, 0.2);
    background: linear-gradient(153deg, rgba(242,157,143,0.8) 0%, rgba(143,160,242,0.8) 100%);
    outline:none !important;
    cursor: pointer;
}
.update-btn:hover {
    background: linear-gradient(153deg, rgba(242,157,143,0.7) 0%, rgba(143,160,242,0.7) 100%);
}
.leave {
    color : gray;
    text-decoration: underline;
    cursor: pointer;
    text-align: right;
}
.ment-box {
    text-align: center;
}



</style>
