<template>   
    <div class="m-5"> 
        <div id="AI" v-if="!loading && !aiPage" class="mx-auto">
           
            <h1 class="m-5" style="font-size : 50px">AI COACHING SERVICE</h1>
            <h5>{{cur+1}}번째 {{course[cur].korean_pose_name}} 포즈부터 시작합니다</h5>
            <img class="round-image" :src="require(`../../public/photos/${posefiles[course[cur]].file_reference}`)" alt="">
            <div class="m-5">
                <a v-if="startBtn" @click="clickStart()" class="btn2 m-3">START</a>
            </div>
        </div>
       
        <div id="loading m-5" v-if="loading" class="y-border">
            <h1 class="m-5">AI 요가 코칭 서비스를 시작합니다</h1>
            <h5>{{courseName}} 코스 준비중</h5>
    
            <i class="fa fa-spinner fa-pulse fa-5x fa-fw m-5" ></i>
            <span class="sr-only">Loading...</span>

            <h2 class="m-5">웹캠을 켜주시고 잠시만 기다려 주세요</h2>
            <h5> AI Yoga Coaching Service is running, Please turn on your webcam and wait</h5>
        </div>
        <div class="d-flex justify-content-around" style="margin-top:50px;">
            <div v-if="aiPage" class="shadow-box">
                <div id="pose-data">
                    <img :src="require(`../../public/photos/${posefiles[course[cur]].file_reference}`)" alt="">
                    <h3 style="font-weight: 500; width : 300px; margin:auto;" class="my-3 highlight">{{ posefiles[course[cur]].korean_pose_name }} 포즈</h3>
                    <h4>
                        <span class="badge mr-1" style="background-color : rgba(44, 62, 80,0.4); color:white">#난이도</span>
                        <span class="badge" style="background-color : rgba(44, 62, 80,0.4); color:white">#{{posefiles[course[cur]].difficulty}}</span>
                    </h4>   
                    <h4>
                        <span class="badge mr-1" style="background-color : rgba(242, 157, 143,0.3)">#부위</span>
                        <span class="badge" style="background-color : rgba(242, 157, 143,0.3)">#{{posefiles[course[cur]].category}}</span>
                    </h4>   
                    
                </div>
               
            </div>
            <div class="shadow-box" id="tm-shadow-box">
                <div ><canvas id="canvas" style="border : 7px solid rgba(242, 157, 143,0.3)"></canvas></div>
                
                <div id="label-container" class="m-auto" style="width : 400px;">
                    <h2 id="good" style="color:green">GOOD</h2>
                    <h2 id="bad" style="color:red">BAD</h2>
                </div>
              
               
            </div>
            <div class="shadow-box" id="coaching-data">
                <h2 class="watch highlight mx-auto" style="width:150px;">{{ watchMin }}:{{ watchSec}}</h2>
                <div id="pie-chart" class="pie-chart my-5"><span class="center" id="seconds-counter">30</span></div>
                <div>
                    <div @click="next" class="btn4">Next Yoga Posture</div>
                </div>
            </div>
        </div>  
    </div>
</template>

<script>
    import axios from 'axios'
    import '@tensorflow/tfjs'
    import * as tmPose from "@teachablemachine/pose"
    import posefiles from "../../public/json"
    // npm install --save @tensorflow/tfjs @teachablemachine/pose
    let model, webcam, ctx, labelContainer, maxPredictions;

    
    export default {
        name : 'Yoga1',
        data() {
            return{
                SERVER_URL: this.$store.state.SERVER_URL,
                startBtn : true,
                loading : false,
                aiPage : false,
                startTime : true,
                endTime : true,
                requestId : undefined,
                course : [1,2,3],
                courseName : 'courseName',
                cur : 0,
                flag : false,
                seconds : 30,
                stopBtn : false,
                restartBtn : false,
                counter : undefined,
                posefiles : posefiles,
                startDateTime: '',
                watch: '',
                watchStamp: 0,
                watchMin: '00',
                watchSec: '00',
                poseTimes: [],
                scores:[],
                SERVER_URL : this.$store.state.SERVER_URL


            }
        },
        mounted(){
            this.getCourse()
            document.getElementById('good').style.display= 'none'
            document.getElementById('bad').style.display= 'none'
        },
        beforeDestroy() {
            window.location.reload()
        },
        methods: {
             getCourse() {
                const courseID = this.$cookies.get('coaching-list')   
                axios.get(this.SERVER_URL + `/api/aicoach/list/${courseID}`)
                .then(res => {
                    //코스 이름과 코스 리스트 save
                    this.courseName = res.data.courseName
                    
                    const Course =  res.data.course.split(',') 
                    const filteredCourse =  []
                    Course.forEach(function(courseID){
                        if (courseID !== "1000"){
                            filteredCourse.push(courseID)
                        }
                    })
                    this.course = filteredCourse
                })
                .catch(err => {
                    this.course = [2,7,11]
                })
            },
            calculateScores() {
                this.poseTimes.forEach(function(poseTime){
                    if (poseTime < 30){
                        this.scores.push(0)
                    }
                    else {
                        var score = Math.floor(30/poseTime*100)
                        this.scores.push(score)
                    }
                }.bind(this))
            },
            clickStart() {
                this.startDateTime = new Date();
                this.$cookies.set('startDateTime', this.startDateTime)
                this.init()
            },
            incrementSeconds() {
                this.seconds--;
                document.getElementById('seconds-counter').innerText = this.seconds
                document.getElementById('pie-chart').style.background = `conic-gradient(rgba(256, 256, 256, 0.3) 0% ${100-100*(this.seconds/30)}%, rgba(242, 157, 143,0.3) ${100-100*(this.seconds/30)}% 100%)`
                if (this.seconds===0) {
                    clearInterval(this.counter) 
                    this.next()
                } 
            },
            next() {
                this.cur++;
                if (this.cur < this.course.length){
                    clearInterval(this.counter) 
                    this.flag = !this.flag
                    var t = this.watch-this.watchStamp
                    this.poseTimes.push(t)
                    this.watchStamp = this.watch
                    document.getElementById('seconds-counter').innerText = 30
                    document.getElementById('pie-chart').style.background = "rgba(256, 256, 256, 0.3)"
                    this.init(this.course[this.cur])

                }
                else {
                    var t = this.watch-this.watchStamp
                    this.poseTimes.push(t)
                    const runTime = this.watchMin+'.'+this.watchSec
                    this.calculateScores()
                    this.$cookies.set('resultScores', this.scores.join("."))
                    this.$cookies.set('resultPoseTimes', this.poseTimes.join("."))
                    this.$cookies.set('resultRunTime', runTime )
                    this.$router.push("/coaching/result")
                }
                
            },
            stop() {
                this.stopBtn = true;
                this.restartBtn = false;     
                if (this.flag && this.stopBtn && this.counter!==undefined) {
                    clearInterval(this.counter)
                    this.counter = undefined
                }
            },
            restart() {
                this.stopBtn = false;
                this.restartBtn = true;
                if (!this.requestId) {
                    this.requestId = window.requestAnimationFrame(this.loop);
                }
                if(this.flag && this.restartBtn && this.counter===undefined) {
                    this.counter = setInterval(this.incrementSeconds,1000)
                } 
            },

            timerCustom(num) {
                num = num + '';
                if (num.length < 2) {
                    num = '0' + num
                }
                return num
            },

            async init() {
                this.startBtn = false;
                // 로딩이 시작
                this.loading = true;
                
                const URL = `../YogaPoses/${this.course[this.cur]}/`;
                const modelURL = URL + "model.json";
                const metadataURL = URL + "metadata.json";
    
                // load the model and metadata
                // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
                // Note: the pose library adds a tmPose object to your window (window.tmPose)
              
                model = await tmPose.load(modelURL, metadataURL);
                maxPredictions = model.getTotalClasses();
        
                // Convenience function to setup a webcam
                //여기 웹캠 사이즈
                const width = 400;
                const height = 400;
                const flip = true; // whether to flip the webcam
                webcam = new tmPose.Webcam(width, height, flip); // width, height, flip
                await webcam.setup(); // request access to the webcam
                await webcam.play();
                // this 붙여줘야!!!!!!!!!!!
                this.requestId  = window.requestAnimationFrame(this.loop);
                // append/get elements to the DOM
                const canvas = document.getElementById("canvas");
                canvas.width = width; 
                canvas.height = height;
                ctx = canvas.getContext("2d");
                labelContainer = document.getElementById("label-container");
                for (let i = 0; i < maxPredictions; i++) { // and class labels
                    labelContainer.appendChild(document.createElement("div"));
                }
            },
            async loop(timestamp) {
                this.loading = false;
                this.aiPage = true;
                document.getElementById('tm-shadow-box').style.visibility= 'visible'
                document.getElementById('coaching-data').style.visibility= 'visible'
                this.startTime = false;

                webcam.update(); // update the webcam frame
                //this.predict 로 this 붙여줘야!!!!!!!!!!!
                await this.predict();

                // stop&restart제어하기
                if (this.requestId) {
                    this.requestId = window.requestAnimationFrame(this.loop);
                }

                var nowDateTime = new Date();
                this.watch = Math.floor((nowDateTime-this.startDateTime)/(1000))
                this.watchMin = this.timerCustom(Math.floor(this.watch/60))
                this.watchSec = this.timerCustom(this.watch-this.watchMin*60)
            },
    
            async predict() {
                // Prediction #1: run input through posenet
                // estimatePose can take in an image, video or canvas html element
                const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
                // Prediction 2: run input through teachable machine classification model
                const prediction = await model.predict(posenetOutput);

                // Rule : class1은 항상 요가포즈, class2는 에러, class3은 
                for (let i = 0; i < maxPredictions; i++) {
                    document.getElementById('label-container').firstChild.style.display = 'none'
                    //90퍼이상 일치하는 동작의 클래스명을 보여준다.
                    if (prediction[i].probability.toFixed(2) >= 0.9){
                        // labelContainer.childNodes[0].innerHTML = prediction[i].className;
                        //만약 제대로된 요가동작이 인식되면 밑에 카운트 시작 메세지가 같이 뜬다
                        if (prediction[i].className === String(this.course[this.cur]) && !this.flag ) {
                            this.flag = true;
                            document.getElementById('good').style.display = 'inline'
                            document.getElementById('bad').style.display = 'none'
                            this.seconds = 30;
                            this.counter = setInterval(this.incrementSeconds,1000)  
                        
                        } else if (prediction[i].className === String(this.course[this.cur])) {
                            document.getElementById('good').style.display = 'inline'
                            document.getElementById('bad').style.display = 'none'
                            this.restart()
                        } else {
                            document.getElementById('good').style.display = 'none'
                            document.getElementById('bad').style.display = 'inline'
                            this.stop()
                        }
                    } else {
                        document.getElementById('good').style.display = 'none'
                        document.getElementById('bad').style.display = 'inline'
                    }                    
                 } 

                this.drawPose(pose);
            },
        
            drawPose(pose) {
                if (webcam.canvas) {
                    ctx.drawImage(webcam.canvas, 0, 0);
                }
            }
        }
 
    }


  

</script>
    
<style scoped>
.y-border{
    padding : 1rem;
    padding-bottom: 3rem;
}
.shadow-box{
    padding:10px;
    height : 490px;
    width : 450px;
    margin : 5px;
    margin-top : 30px;
    background-color: rgba(255,255,255,0.5);
    border-radius: 10px;
    border: 1px solid white;
    box-shadow: 3px 3px rgba(0,0,0,0.1);
}
.watch {
    font-size: 2rem;
}
.pie-chart {
  position: relative;
  display:inline-block;
  width: 210px;
  height: 210px;
  border-radius: 50%;
  background: rgba(256, 256, 256, 0.3);
  border: 7px solid rgba(242, 157, 143,0.3);
  /* border: 7px solid #2c3e50; */
}
span.center{
    /* color : #f29d8f; */
    display : block;
    position: absolute;
    top:50%; left:50%;
    width:200px; 
    height:200px;
    border-radius: 50%;
    text-align:center; 
    line-height: 200px;
    font-size:70px;
    transform: translate(-50%, -50%);
}
#coaching-data, #tm-shadow-box {
    visibility: hidden;
}
#pose-data > img {
    width : 300px;
    border : 7px solid rgba(242, 157, 143,0.3);
    border-radius : 10px;
    
}
.box {
  position: relative;
  display: inline-block;
  width: 450px;
  /* height: 100px; */
  border-radius: 5px;
  background-color: rgba(255,255,255,0.5);
  box-shadow: 0 1px 2px rgba(0,0,0,0.15);
  transition: all 0.3s ease-in-out;
}

/* Create the hidden pseudo-element */
/* include the shadow for the end state */
.box::after {
  /* content: ''; */
  position: absolute;
  z-index: -1;
  width: 100%;
  height: 100%;
  opacity: 0;
  border-radius: 5px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
  transition: opacity 0.3s ease-in-out;
}
/* Scale up the box */
.box:hover {
  transform: scale(1.2, 1.2);
}

/* Fade in the pseudo-element with the bigger shadow */
.box:hover::after {
  opacity: 1;
}
</style>