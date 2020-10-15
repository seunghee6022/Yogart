<template>
<div style="margin: 50px; ">
    <h2 class="m-3">{{this.courseName}} 코칭 결과</h2>
    <h5>{{this.courseName}} 코스</h5>
   <span class="badge badge-success" style="font-size:1rem; background:rgba(242, 157, 143,1);">총 소요시간 {{this.minutes}} : {{this.seconds}}</span>

     <carousel-3d :controls-visible="true" :width="720" :height="450">
        <slide class="slide" :index="0">
            <figure>
                <lineChart/>
            </figure>
        </slide>
        <slide class="slide" :index="1">
            <figure>
                <dougnutChart/>
            </figure>
        </slide>
        <slide class="slide" :index="2">
            <figure class="m-5">
                <h3>자세 결과 테이블</h3>
                 <table class="table">
                    <thead>
                        <tr>
                        <th scope="col">자세</th>
                        <th scope="col">결과</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="poseId in course"  :key="poseId">
                        <td>{{ posefiles[poseId].korean_pose_name }}</td>
                        <td>{{ scores[`${poseId}`] }}</td>
                        </tr>
                    </tbody>
                </table>
            </figure>
        </slide>
    </carousel-3d>
    
    <div id="goMypage" class="btn4  m-5" @click="goMypage()">마이페이지</div>
   
</div>
</template>

<script>
import posefiles from '../../../public/json.js'
import 'chartjs-plugin-labels'
import lineChart from '../../components/aiResult/lineChart.vue'
import dougnutChart from '../../components/aiResult/dougnutChart.vue'
import axios from 'axios'
import {Carousel3d, Slide} from 'vue-carousel-3d';
export default {
    name : 'AICoachingResultPage',
    components : {
        lineChart,
        dougnutChart,
        Carousel3d,
        Slide
    },
    data() {
        return {
            posefiles : posefiles,
            courseId : 0,
            courseName : '',
            course : [], //course순서 array
            dougnutdata : [0,0,0,0,0,0,0],
            scores: {},
            minutes : 0,
            seconds : 0,        
            SERVER_URL : this.$store.state.SERVER_URL
              
        }  
    },
    created() {
        window.scrollTo(0,0);
    },
    mounted(){
        this.getCourse()
        this.splitTotaltime()
        this.saveResult()
    },
    methods : {
        goMypage(){
            this.$router.push('/mypage')
        },
        splitTotaltime() {
            const totalTime = this.$cookies.get('resultRunTime').split('.')
            this.minutes = totalTime[0]
            this.seconds = totalTime[1]
          
        },
        getScores() {
            var scoreArr = this.$cookies.get('resultScores').split('.')
            var cnt = 0
            var scores = {}
            this.course.forEach(function(courseId){
                if (scoreArr[cnt] < 30) {
                    scores[`${courseId}`] = 'BAD'
                } else if (scoreArr[cnt] < 60) {
                    scores[`${courseId}`] = 'NOT BAD'
                } else if (scoreArr[cnt] < 80) {
                    scores[`${courseId}`] = 'GOOD'
                } else {
                    scores[`${courseId}`] = 'VERY GOOD'
                }
                cnt++
            })
            this.scores = scores

        },
        getCourse() {
            this.courseId = this.$cookies.get('coaching-list')   
            axios.get(this.SERVER_URL + `/api/aicoach/list/${this.courseId}`)
            .then(res => {
                //코스 이름과 코스 리스트 save
                this.courseName = res.data.courseName
                
                this.course =  res.data.course.split(',')
                console.log("결과",this.course)
                this.createLineLabels(this.course)
                this.createDougnutData(this.course)
                this.getScores()
                //
            })
            .catch(err => {
                console.error(err)
            })
        },
        createLineLabels(courses) { //라벨 - 동작이름들
            const lineLabels = []
            // console.log("라인라벨",courses)
            courses.forEach(function (poseID){
                lineLabels.push(posefiles[poseID].korean_pose_name)
            }.bind(this))
            const lineLabelStr = lineLabels.join(',')
            this.$cookies.set('lineLabelStr',lineLabelStr) 
        },
        createDougnutData(courses) { //data - 태그별 카운트 
            courses.forEach(function (poseID){
                this.posefiles[poseID].tag.forEach(function(tag){
                    if (tag === '전신') {
                        this.dougnutdata[0] ++       
                    }
                    else if (tag === '척추') {
                        this.dougnutdata[1] ++
                    }
                    else if (tag === '복근') {
                        this.dougnutdata[2] ++
                    }
                    else if (tag === '팔') {
                        this.dougnutdata[3] ++
                    }
                    else if (tag === '다리') {
                        this.dougnutdata[4] ++
                    }
                    else if (tag === '릴렉싱') {
                        this.dougnutdata[5] ++
                    } 
                    else if (tag === '에너지') {
                        this.dougnutdata[6] ++
                    } 
                }.bind(this))         
            }.bind(this))
            const dougnutdataStr = this.dougnutdata.join(',')
            this.$cookies.set('dougnutdataStr',dougnutdataStr)
        },
        
        saveResult() {
            //오늘 날짜
            const startDateTime = this.$cookies.get('startDateTime');
            //총 경과 시간
            const totalTime = String(60 * this.minutes + this.seconds)
            //부위별 태그 횟수
            const tagCounting = this.$cookies.get('dougnutdataStr')
            //db에 오늘날짜, 경과"시간,부위별 태그횟수 보내서 저장
            
            axios.post(this.SERVER_URL + `/api/aicoach/result`,
                { 'headers': { 'auth-token': window.$cookies.get('auth-token')},
                'totalTime' : totalTime, 
                'startDateTime': startDateTime, 
                'tagCounting' : tagCounting,
                }
            )
            .then(res => {
                // console.log(res) 
            })
            .catch(err => {console.log(err)})
        }
    },
  
 
}
</script>

<style scoped >
#goMypage{
    width : 150px;
    height : 40px;
    line-height:18px;
    border : 3px solid rgba(242, 157, 143,0.3);
    border-radius: 10px;
    font-size : 15px;
    font-weight: 700;
    position : absolute;
    top : 10%;
    right : 5%;
}
.box{
    width : 100%;
}
.carousel-3d-container
.carousel-3d-container figure {
  margin: 0;
}

.carousel-3d-container figcaption {
  
  position: absolute;
  background-color: rgba(255, 255, 255, 0.5);
  color: #fff;
  bottom: 0;
  position: absolute;
  bottom: 0;
  padding: 15px;
  font-size: 30px;
  min-width: 100%;
  min-height:100%;
  line-height : 270px;
  box-sizing: border-box;
}
.carousel-3d-container figcaption:hover {
  cursor: pointer;
  background-color: rgba(0, 0, 0, 0);
  
}
.slide{
    border-radius: 7px;
    background-color: rgba(255, 255, 255, 0.5);
}
.slide.current{
    background-color: rgba(255, 255, 255, 0.9);
}
tr, td, th{
    border : 2px solid rgba(0, 0, 0, 0.5);
}
th{
    background-color: rgba(242, 157, 143, 0.5) ;
}
</style>
