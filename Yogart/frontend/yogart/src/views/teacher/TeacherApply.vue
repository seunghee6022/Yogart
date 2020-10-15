<template>
    <div class="m-5 white-box mx-auto">
        <h1 class="mx-5 mb-5 under-border">요가지도자 자격 인증</h1>
        <h5>요가 수업을 진행하기 위해서는 요가지도자 자격을 인증하셔야 합니다.</h5>
        <h5>이를 위해 아래에 해당하는 이미지를 첨부해주세요.</h5>
        <p>( 모든 첨부 파일들을 하나의 이미지에 담아주세요 )</p>
        <div class=" mx-5 under-border"></div>
        <div >
            <h5>1. 신분증 사본</h5>
            <h5 class="my-3">2. 요가지도자 자격증 사본(종류 무관)</h5>
        </div>
        <input type="file" ref="tI" id="t-i" accept=".jpg, .jpeg, .gif, .png" multiple="multiple">
        <p class="info">모든 정보는 인증 후 파기됩니다.</p>
        <button class="btn-blue" @click="onSubmit">등록</button>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'TeacherApply',
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL
        }
    },
    methods: {
        onSubmit() {
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token'),
                    'Content-Type' : 'multipart/form-data',
                    'Access-Control-Allow-Origin': '*',
                }
            }
            // 확인 필요
            let fd = new FormData();
            // let file = [];
            for (let i = 0; i < this.$refs.tI.files.length; i++) {
                // file.push(this.$refs.tI.files[i]);
                let file = this.$refs.tI.files[i];
                fd.append('files', file)
            }

            axios.post(this.SERVER_URL + '/api/users/imageUpload', fd, requestHeaders)
            .then(res => {
                this.$router.push('/')
            }) //다른 곳으로 이동 필요
            .catch(err => console.error(err))
        },
    }

}
</script>

<style scoped>
.white-box{
    min-height : 550px;
}
.btn-blue{
    /* margin-top: 20px; */
    border-radius: 10px;
    height : 50px;
    line-height: 20px;
}
.info{
    margin-top : 30px;
    font-size : 13px;
    color : rgba(255,0,0,1);
}
#t-i{
    border: 1.5px solid black;
    border-radius: 5px;
}
</style>
