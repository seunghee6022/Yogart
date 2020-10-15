<template>
    <div>
        <div class="teacherBgd my-3">
            <img v-if="teacherInfo.teacherImage" :src="teacherInfo.teacherImage" alt="" class="user-profile m-5">
            <h2 class="name">{{ teacherInfo.teacherName }}</h2>
            <div class="intro-box">
            <h5 class="intro" v-if="teacherInfo.teacherIntro">{{ teacherInfo.teacherIntro }}</h5>
            <h5 class="intro" v-else>등록된 소개가 없습니다.</h5>
            </div>
            <br>
            <h5>{{ptList.length}} 수업</h5>
        </div>
        <div class="m-5">
            <p class=info>* 강사님의 모든 강의를 한 눈에 보고 간편하게 수강신청을 할 수 있습니다.</p>
        <b-tabs content-class="mt-3">
            <b-tab v-for="pt in ptList" :key="pt.ptId" :title="pt.ptName">
                <ClassRegi :ptId="pt.ptId"/>
            </b-tab>
        </b-tabs>
    </div>
</div>
</template>

<script>
import axios from 'axios'
import ClassRegi from '@/components/teacher/ClassRegi.vue'

export default {  
    name: 'TeacherDetail',
    components: {
        ClassRegi,
    },
    data() {
        return {
            teacherId: this.$route.params.teacher_id,
            SERVER_URL: this.$store.state.SERVER_URL,
            teacherInfo: {
                teacherName: null,
                teacherIntro: null,
                teacherImage: null,
            },
            ptList: [],
        }
    },
    mounted() {
        axios.get(this.SERVER_URL + `/api/teachers/list/detail/${this.teacherId}`, this.teacherId)
        .then(res => {
            console.log(res)
            this.teacherInfo.teacherName = res.data.teacherInfo.userNickname
            this.teacherInfo.teacherIntro = res.data.teacherInfo.userIntro
            this.teacherInfo.teacherImage = `${this.$store.state.SERVER_URL}/api/users/profileImageByEmail?userEmail=${res.data.teacherInfo.userEmail}`
            this.ptList = res.data.ptList
        })
        .catch(err => console.error(err))
        const links = document.querySelectorAll('a')
        links.forEach(e => {
            e.setAttribute('style', 'color: black !important')
        })
    },
    beforeDestroy() {
        const links = document.querySelectorAll('a')
        links.forEach(e => {
            e.setAttribute('style', 'color: gray')
        })
    }
}
</script>

<style scoped>
.name{
    margin-bottom: 10px;
    font-size : 40px;
}
.intro{
    font-size : 20px;
}
.intro-box{
    margin : auto;
    padding : 10px;
    width : 70%;
    border-top : 1px solid white;
    border-bottom : 1px solid white;
}
.teacherBgd{
    /* background-color: rgba(143, 160, 242, 0.7); */
    height : 450px;
    font-family: 'Montserrat', sans-serif;
    background-image: url('../../assets/beach.jpg');
    color : white;
}

.info{
    color: rgba(0, 0, 0, 0.6);
    font-size: 10px;
    text-align: left;
}

</style>