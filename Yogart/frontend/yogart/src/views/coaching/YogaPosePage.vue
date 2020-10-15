<template>
    <div class=" my-5" >
        <h1 style="font-weight:700px;" class="my-5">나만의 코스 만들기</h1>

        <div id="course-list">
            <div class="field-row mx-5">
                <input name="courseName" id="courseName" v-model="courseName" type="text" required @keyup.enter="makeList(courseName)"/>
                <label for="courseName">코스명 + Enter</label>   
            </div>
       
            <p style="color:#888; margin-top:5px;">포즈를 선택해 주세요( 최대 7개 )<span @click="reset" class="reset-btn">Reset</span></p>
            <div class="d-flex justify-content-around">

                <div class="d-flex flex-column" v-for="idx in poseIndexList" :key="idx">
                    <div>
                        <img class="image user-profile-small m-2" :src="require(`../../../public/photos/${posefiles[idx].file_reference}`)">
                        <span id="delete" @click="deleteItem(posefiles[idx])">x</span>
                    </div>
                    <p>{{posefiles[idx].korean_pose_name}}</p>
                </div>         
            </div>    
        </div>

        <div class="container">
            <div>
                <div class="d-flex justify-content-around">
                    <div id="all-btn" class="btn-white"  :class="{ active: all }" @click="allBtn">전체</div>
                    <div id="beg-btn" class="btn-white" :class="{ active: beginner }" @click="beginnerBtn">초급</div>
                    <div id="int-btn" class="btn-white" :class="{ active: intermediate }" @click="intermediateBtn">중급</div>
                    <div id="exp-btn" class="btn-white"  :class="{ active: expert }" @click="expertBtn">고급</div>
                </div>
           
            
                <div v-if="all">
                    <div  class="row">       
                        <div @click="poseChoose(posefile)" class="col-sm-2 box all-box" :id="posefile.id" v-for="posefile in posefiles" :key="posefile.pose_name">  
                            <div class="pose-img">
                                <img :title="posefile.korean_pose_name"  class="user-profile-mid m-3" :src="require(`../../../public/photos/${posefile.file_reference}`)">
                            </div> 
                            <p>{{posefile.korean_pose_name}}</p>
                        </div>      
                    </div>
                </div>
                <div v-if="beginner" >
                    <div class="row">
                        <div @click="poseChoose(posefile)" class="col-sm-2 box beginner-box" :id="posefile.id" v-for="posefile in beginnerPosefiles" :key="posefile.pose_name">  
                            <div class="pose-img" >
                                <img  class="user-profile-mid m-3" :src="require(`../../../public/photos/${posefile.file_reference}`)">
                            </div> 
                            <p>{{posefile.korean_pose_name}}</p>
                       
                        </div>      
                    </div>
                </div>
                <div v-if="intermediate">
                    <div class="row">
                        <div @click="poseChoose(posefile)" class="col-sm-2 box intermediate-box" :id="posefile.id" v-for="posefile in intermediatePosefiles" :key="posefile.pose_name">  
                            <div class="pose-img">
                                <img class="user-profile-mid m-3" :src="require(`../../../public/photos/${posefile.file_reference}`)">
                            </div> 
                            <p>{{posefile.korean_pose_name}}</p>
                        </div>      
                    </div>
                </div>
                <div v-if="expert" >
                    <div class="row">
                        <div @click="poseChoose(posefile)"  class="col-sm-2 box expert-box" :id="posefile.id" v-for="posefile in expertPosefiles" :key="posefile.pose_name">  
                            <div class="pose-img">
                                <img class="user-profile-mid m-3" :src="require(`../../../public/photos/${posefile.file_reference}`)">
                            </div> 
                            <p>{{posefile.korean_pose_name}}</p>
                        </div>
                    </div>      
                </div>
            </div>  
        </div>
    </div>
</template>

<script>
import posefiles from '../../../public/json.js'
import axios from 'axios'
var allItems = document.getElementsByClassName("all-box");
var beginnerItems = document.getElementsByClassName("beginner-box");
var intermediateItems = document.getElementsByClassName("intermediate-box");
var expertItems = document.getElementsByClassName("expert-box");

export default {
    name : 'YogaPosePage',

    data() {
        return {
            tempPosefiles : posefiles,
            posefiles : posefiles,
            count : 0,
            poseList : [],
            poseIndexList : [],
            max : 7,
            beginnerPosefiles : [],
            intermediatePosefiles : [],
            expertPosefiles : [],
            all : true ,
            beginner : false,
            intermediate : false,
            expert : false,
            SERVER_URL : this.$store.state.SERVER_URL
            
            
        }
    },
    mounted() {
        this.difficultyDistribution()
        this.renew()
    },
    methods : {
        renew(){
            this.allSelected()
            this.beginnerSelected()
            this.intermediateSelected()
            this.expertSelected()
        },
        deleteItem(btnPose) {
            var idx = this.poseList.indexOf(btnPose.korean_pose_name)
            this.poseList.splice(idx,1);
            this.poseIndexList.splice(idx,1);
            this.renew()
        },
        reset(){
            this.poseList = []
            this.poseIndexList = []
            this.renew()
        },
        allSelected() {
            for(var i=0; i < allItems.length; i++) {
                //만약 이미 리스트에 있으면
                if (this.poseIndexList.includes(parseInt(allItems[i].id))){
                     allItems[i].style.backgroundColor = 'rgba(242, 157, 143,0.5)'
                    }
                else{
                    allItems[i].style.backgroundColor = 'rgba(0,0,0,0)'
                }       
            }
        },
        beginnerSelected() {
            for(var i=0; i < beginnerItems.length; i++) {
                //만약 이미 리스트에 있으면
                if (this.poseIndexList.includes(parseInt(beginnerItems[i].id))){
                    beginnerItems[i].style.backgroundColor = 'rgba(242, 157, 143,0.5)'
                    }
                else{
                    beginnerItems[i].style.backgroundColor = 'rgba(0,0,0,0)'
                }       
            }
        },
        intermediateSelected() {
            for(var i=0; i < intermediateItems.length; i++) {
                //만약 이미 리스트에 있으면
                if (this.poseIndexList.includes(parseInt(intermediateItems[i].id))){
                     intermediateItems[i].style.backgroundColor = 'rgba(242, 157, 143,0.5)'
                    }
                else{
                    intermediateItems[i].style.backgroundColor = 'rgba(0,0,0,0)'
                }       
            }
        },
        expertSelected() {
            for(var i=0; i < expertItems.length; i++) {
                //만약 이미 리스트에 있으면
                if (this.poseIndexList.includes(parseInt(expertItems[i].id))){
                     expertItems[i].style.backgroundColor = 'rgba(242, 157, 143,0.5)'
                    }
                else{
                    expertItems[i].style.backgroundColor = 'rgba(0,0,0,0)'
                }       
            }
        },

        difficultyDistribution() {
            this.posefiles.forEach(function(posefile) {
            if (posefile.difficulty === 'Beginner') {
                    this.beginnerPosefiles.push(posefile)
                }
                else if (posefile.difficulty === 'Intermediate') {
                    this.intermediatePosefiles.push(posefile)
                }
                else if (posefile.difficulty === 'Expert') {
                    
                    this.expertPosefiles.push(posefile)
                    
                }
        }.bind(this))
        },
        tagDistribution() {
            const tags_idx = ['전신','팔','다리','복근','에너지','릴렉싱','척추']
            var tags_val = [0,0,0,0,0,0,0]
            var error = 0
            this.posefiles.forEach(function(posefile) {
                // var pose = posefile
                posefile.tag.forEach(function(tag){

                if (tag === '전신') {
                        tags_val[0] ++
                        
                    }
                    else if (tag === '팔') {
                        tags_val[1] ++
                    }
                    else if (tag === '다리') {
                        tags_val[2] ++
                    }
                    else if (tag === '복근') {
                        tags_val[3] ++
                    }
                    else if (tag === '에너지') {
                        tags_val[4] ++
                    }
                    else if (tag === '릴렉싱') {
                        tags_val[5] ++
                    } 
                    else if (tag === '척추') {
                        tags_val[6] ++
                    } 
                    else {
                        error ++
                    }
                    
                })
        }.bind(this))
        },
        poseChoose(btnPose) {
            
            if (this.poseList.includes(btnPose.korean_pose_name)){
                var idx = this.poseList.indexOf(btnPose.korean_pose_name)
                this.poseList.splice(idx,1);
                    this.poseIndexList.splice(idx,1);
                
            }
            else{
                if (this.poseList.length < this.max ) {
                    this.poseList.push(btnPose.korean_pose_name)
                    this.poseIndexList.push(btnPose.id)
                }
                else {
                    alert('최대 7개의 동작만 가능합니다.')
                }
            }
            
            this.renew()

        },
        makeList(courseName) {
            let poseCourse = this.poseIndexList.join(',')
            window.confirm(`${courseName} 리스트를 만드시겠습니까?`);
            axios.post(this.SERVER_URL + '/api/aicoach/list/create',
            { 'headers': { 'auth-token': window.$cookies.get('auth-token')},'courseName' : courseName, 'poseCourse': poseCourse } )
            .then(res => {
                //이 안에서 코스 pk뽑아오기
                // 쿠키에 저장
                this.$cookies.set('coaching-list', res.data)
                this.$router.push(`/coaching/yogaposelist`)
            })
            .catch(err => {console.log(err)})   
        },
        allBtn() {
            this.all = true
            this.beginner = false
            this.intermediate = false
            this.expert = false
            this.allSelected()
        },
        beginnerBtn() {
            this.all = false
            this.beginner = true
            this.intermediate = false
            this.expert = false
            this.beginnerSelected()
            
        },
        intermediateBtn() {
            this.all = false
            this.beginner = false
            this.intermediate = true
            this.expert = false
            this.intermediateSelected()
                 
        },
        expertBtn() {
            this.all = false
            this.beginner = false
            this.intermediate = false
            this.expert = true
            this.expertSelected()
           
        },
        
    }
}



</script>

<style scoped>
/* .overlay-image .normal {
 transition: .5s ease;
}
.overlay-image:hover .normal {
 opacity: 0;
}
.overlay-image .hover {
 background-color: rgba(0,0,0,0.5);
} */
.box{
    /* margin : 5px; */
    background-color: rgba(255, 255, 255, 0.0);
}
.box:hover{
    border : 2px solid white;
    cursor: pointer;
}
p {
    font-weight: 500;
}
.btn-white{
    margin-bottom : 10px;
    font-size:20px;
    border-radius: 5px;
    width : 150px;
    height: 40px;
    line-height: 15px;
}
.btn-white:visited{
    background-color: #f29d8f;
    color : white;
}
#delete{
    /* position : relative;
    top : 0;
    right : 0; */
    background-color:  red;
    border : 1.5px solid black;
    color:black;
    font-size : 5px;
    min-width: 6px;
    min-height: 6px;
}
#delete:hover{
    cursor: pointer;

}
.reset-btn{
    padding : 3px;
    background-color:  rgba(255, 255, 255, 0.5);
    border : 1.5px solid #f29d8f;
    color : #f29d8f;
    font-size:10px;
    border-radius: 5px;
    height:20px; 
    width:50px; 
    line-height:10px;
}
.reset-btn:hover{
    cursor: pointer;
    background-color: #f29d8f;
    color : white;
}


#course-list{
    width:1140px; 
    background-color: rgba(242, 157, 143,0.5);
    margin : auto;
    margin-bottom: 3rem;
    padding-top: 5px;
}


@import 'https://fonts.googleapis.com/css?family=Titillium+Web';
.field-row {
	font-family: 'Titillium Web', sans-serif;
	margin:30px 0 0 10px;
	position:relative;
}
label {
	font-size:16px;
	color:#888;
	position:absolute;
	top: 10px;
    bottom: 0;
    left: 310px;
	transition: .3s ease;
	cursor:text;
}
input {
    width : 450px;
    background :rgba(255, 255, 255, 0.5);
	font-size:16px;
	line-height:18px;
	padding: 10px 10px 10px 0;
	border:0;
	border-bottom:1px solid #ccc;
	outline:none;
	
}
input:focus {
	border-color:#888;
}
input:focus ~ label, input:valid ~ label {
	font-size:15px;
	
	color:black;
	transform: translate3d(0, -30px, 0);
}
.active{
    background :#f29d8f;
    color : white;
}
</style>