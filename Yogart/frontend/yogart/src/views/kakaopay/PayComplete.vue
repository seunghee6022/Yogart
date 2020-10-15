<template>
    <div class="padding-for-nav">
        <div class="page-name">
            {{quantity}} 스푼이 결제 완료되었습니다.
        </div>
        <button class="back-to-class" @click="backToClass()">수업 구매하러 가기</button>
    </div>
</template>

<script>
import axios from 'axios'

function getUrlParams() {
    var params = {};
    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
    return params;
}

export default {
    name: 'PayComplete',
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL,
            quantity: 10,
        }
    },
    mounted() {
        const kakaoData = getUrlParams()
        const tid = this.$cookies.get('kakao-tid')
        this.$cookies.remove('kakao-tid')
        const pgToken = kakaoData.pg_token
        const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
        }
        const paymentData = {
            tid: tid,
            pgToken: pgToken
        }
        axios.post(this.SERVER_URL + '/api/users/paymentSuccess', paymentData, requestHeaders)
        .then(res => {
            this.quantity = res.data.quantity
        })
        .catch(err => console.error(err))
    },
    methods: {
        backToClass() {
            this.$router.push("/classandteacher")
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
.back-to-class {
    width: 17rem;
    height: 3rem;
    margin-bottom: 1rem;
    color: white;
    font-size: 17px;
    border-radius: 3rem;
    border-width: 0px 0px 1.2px;
    border-color: rgba(255, 255 255, 0.2);
    background: rgba(215, 159, 215, 1);
    outline:none;
    cursor: pointer;
}
.back-to-pay:hover {
    background: rgba(215, 159, 215, 0.9);
}
</style>