<template>
    <div class="">
        <h1 class="my-5 y-border">{{courseName}} 코스</h1>
         
        <p class="mb-5">다음 동작 순서대로 AI 코칭이 진행됩니다.</p>
     
        <div class="box d-flex justify-content-around">
            <div v-for="poseId in yogaList" :key="poseId">
                <div>
                    <img class="user-profile m-2" :src="require(`../../../public/photos/${posefiles[poseId].file_reference}`)">
                </div> 
                <h5>{{posefiles[poseId].korean_pose_name}}</h5>
            </div>           
        </div>
        <div class="d-flex justify-content-center my-5">
            <div class="btn-white m-5" @click="goBackToList()">다른 코스 둘러보기</div>
            <div class="btn-white  m-5" @click="clickStart()">시작할래요</div>
        </div>
    </div>
</template>

<script>
import posefiles from '../../../public/json.js'
import axios from 'axios'

export default {
    name : 'YogaPoseListDetailPage',
    data() {
        return {
            posefiles : posefiles,
            listId : this.$cookies.get('coaching-list'),
            backCommingString: '',
            yogaList: [],
            courseName: '',
            SERVER_URL: this.$store.state.SERVER_URL
        }
    },
    components: {
        
    },
    methods: {
        getCourseList() {
            axios.get(this.SERVER_URL + `/api/aicoach/list/${this.listId}`)
            .then(response => {
                this.courseName = response.data.courseName
                if (this.courseName === 'Abs') {
                    this.courseName = '코어 강화'
                } else if (this.courseName === 'leg') {
                    this.courseName = '다리 집중'
                } else if (this.courseName === 'arm') {
                    this.courseName = '팔뚝 집중'
                } else if (this.courseName === 'spine') {
                    this.courseName = '척추 강화'
                } else if (this.courseName === 'Whole Body') {
                    this.courseName = '전신 요가'
                } else if (this.courseName === 'Good Morning') {
                    this.courseName = '에너지 넘치는 아침'
                } else if (this.courseName === 'Mental and physical stability') {
                    this.courseName = '바디 릴렉싱'
                }
                this.backCommingString = response.data.course
                this.StringToArr()
            })
            .catch(err => console.error(err))
        },
        StringToArr() {
            const Course =  this.backCommingString.split(',')
            const filteredCourse =  []
            Course.forEach(function(courseID){
                if (courseID !== "1000"){
                    filteredCourse.push(courseID)
                }
            })
            this.yogaList = filteredCourse
        },
        clickStart() {
            this.$cookies.set('coaching-list', this.listId)
            this.$router.push(`/coaching`)
        },
        goBackToList(){
            this.$router.push(`/coaching/yogaposelist`)
        }

    },
    created() {
        this.getCourseList()
    },
}
</script>

<style scoped>
.btn-white{
    width : 30%;
}
.box{
    padding : 10px;
    margin : auto;
    width : 80%;
    background-color: rgba(255, 255, 255, 0.5);
}
.box:hover{
    border : 2px solid white;
    cursor: pointer;
}
.user-profile{
    box-shadow: 3px 3px rgba(0,0,0,0.6);
}
.y-border{
    border-top : 2px solid #f29d8f;
    border-bottom : 2px solid #f29d8f;
}
</style>