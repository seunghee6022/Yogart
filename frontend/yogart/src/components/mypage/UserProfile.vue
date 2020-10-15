<template>
<div class="padding-for-nav">
    <div class="profile-box">
        <div class="img-wrap">
            <img v-if="imgSrc" class="user-profile-img" :src="imgSrc" alt="user profile image">
            <!-- <img v-else class="user-profile-img" src="../../assets/userDefault.jpg" alt="user profile image"> -->
        </div>
        <div v-if="!introDown" class="intro-wrap">
            <div class="box sb2">{{userIntro}}</div>
        </div>
        <div v-else >
            <div class="box mt-0 mb-2">{{userIntro}}</div>
        </div>
        <div class="profile-content">
            <div class="nickname">{{ userNickname }}</div>
            <i class="fas fa-pencil-alt" @click="clickUpdate()"></i>
            <div class="email">{{ userEmail }}</div>
            <div class="spoon-wrap" @click="clickSpoon()">
                <i class="fas fa-utensil-spoon fa-1x d-inline-block pr-3"></i><div class="spoon d-inline-block">{{userSpoon}} 스푼 </div>
            </div>
        </div>

        

    </div>
    
</div>    
</template>

<script>
import axios from 'axios'

export default {
    name : 'UserProfile',
    data() {
        return {
            SERVER_URL : this.$store.state.SERVER_URL,
            introDown: false,
            userName: '',
            userNickname: this.$store.state.userNickname,
            userEmail: '',
            userProfile : '',
            userIntro : '',
            id : Number,
            imgSrc : `${this.$store.state.SERVER_URL}/api/users/profileImage?authToken=` + this.$cookies.get('auth-token'),
            userSpoon : 0,
        }
    },
    mounted() {
        this.getUserData()
        window.addEventListener('resize', this.setSize)
    },
    created() {
        window.scrollTo(0,0);
    },
    methods : {
        clickUpdate() {
            this.$router.push(`/mypage/update`)
        },
        clickSpoon() {
            this.$router.push(`/spoons`)
        },
        getUserData() {
            const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
            }
            axios.get(this.SERVER_URL + '/api/users/myInfo/profile', requestHeaders)
            .then(res => {
                this.userName = res.data.userName
                this.id = res.data.id
                this.userNickname = res.data.userNickname
                this.userEmail = res.data.userEmail
                this.userProfile = res.data.userProfile
                this.userIntro = res.data.userIntro
                this.userSpoon = res.data.userSpoon
                if (this.userProfile === null) {
                    this.userProfile = 'userDefault'
                }
                if (this.userIntro === null) {
                    this.userIntro = '상태 메세지를 적어보세요.'
                }
            })
            .catch(err => console.error(err))
        },
        setSize() {
			let size = window.innerWidth
			if (size < 770) {
				this.introDown = true
			} else {
				this.introDown = false
			}
		},
    }
}
</script>

<style scoped>
.profile-box {
    margin: -5rem auto 0rem;
    padding: 30px;
    background-color: rgba(143, 160, 242, 0.1);
    width: 500px;
    border-radius: 200px;
}
.box {
    width: 300px;
    margin: 50px auto;
    background: rgba(143, 160, 242, 1);
    padding: 20px;
    text-align: center;
    font-weight: 900;
    color: #fff;
    font-family: arial;
    position: relative;
}
.sb2:before {
    content: "";
    width: 0px;
    height: 0px;
    position: absolute;
    border-left: 10px solid transparent;
    border-right: 10px solid rgba(143, 160, 242, 1);
    border-top: 10px solid rgba(143, 160, 242, 1);
    border-bottom: 10px solid transparent;
    left: -19px;
    top: 6px;
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
.img-wrap {
    position: relative;
    display: inline-block;
    margin-bottom: 1rem;
}
.intro-wrap {
    position: absolute;
    display: inline-block;
    margin: 2rem -1.5rem;
}
.nickname {
    font-size: 20px;
    font-weight: bold;
    display: inline-block;
    padding-left: 2rem;
}
.fa-pencil-alt {
    display: inline-block;
    margin-left: 0.5rem;
    cursor: pointer;
}
.fa-pencil-alt:hover {
    color: rgba(242, 157, 143, 1);
}
.email {
    font-weight: bold;
}
.spoon {
    font-size: 15px;
}
.spoon-wrap {
    display: inline-block;
    padding: 0.3rem;
    background-color: rgba(242, 157, 143, 1);
    border-radius: 1rem;
}
.spoon-wrap:hover {
    background-color: rgba(242, 157, 143, 0.8);
    cursor: pointer;
}
</style>