<template>
  <div> 
      <div id="AI"  v-if="!loading">
            <h1>AI Coaching Service</h1>
            <button v-if="startBtn" class="w3-btn w3-round-xlarge w3-red w3-xlarge m-5" type="button" @click="init">Get Start!</button>
             </div>
       
       <div id="loading" v-if="loading">
           <h3 class="m-5">AI 요가 코칭 서비스를 시작합니다</h3>

           <!-- <i class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i>
          <span class="sr-only">Loading...</span> -->
          
          <i class="fa fa-spinner fa-pulse fa-5x fa-fw m-5"></i>
      `   <span class="sr-only">Loading...</span>
      
            <!-- <i class="fa fa-refresh fa-spin fa-3x fa-fw" aria-hidden="true"></i>
            <span class="sr-only">Refreshing...</span> -->

            <h3 class="m-5">웹캠을 켜주시고 잠시만 기다려 주세요</h3>
            <p> AI Yoga Coaching Service is running, Please turn on your webcam and wait</p>


        </div>
       <div v-if="aiPage" class="m-1">
           <p>AI 코칭을 경험해 보세요</p>
           <button v-if="!startBtn" class="w3-button w3-teal" type="button" @click="restart">Restart</button>
            <button v-if="!startBtn" class="w3-button w3-black" type="button" @click="stop">End</button>
      
        </div>
        <div><canvas id="canvas"></canvas></div>
        <div id="label-container"></div>
  </div>
</template>

<script>
    import '@tensorflow/tfjs'
    import * as tmPose from "@teachablemachine/pose"
    const URL = "../my_model/";
    let model, webcam, ctx, labelContainer, maxPredictions;
    console.log(URL)
    export default {
        name : 'AI',
        data() {
            return{
                startBtn : true,
                loading : false,
                aiPage : false,
                predictionTime : 3,
                startTime : true,
                endTime : true,
                requestId : undefined,
            }
        },
        async created() {
            
        },
        async mounted() {
     
            // // load the model and metadata
            // // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
            // // Note: the pose library adds a tmPose object to your window (window.tmPose)
            // model = await tmPose.load(modelURL, metadataURL);
            // console.log("model",model)
            // maxPredictions = model.getTotalClasses();
            // console.log("this.prediction",this.prediction)
            // this.prediction = maxPredictions
            // console.log("maxPredictions",maxPredictions)
            // console.log("this.prediction",this.prediction)
    
            // // Convenience function to setup a webcam
            // //여기 웹캠 사이즈
            // const width = 200;
            // const height = 200;
            // const flip = true; // whether to flip the webcam
            // webcam = new tmPose.Webcam(width, height, flip); // width, height, flip
            // await webcam.setup(); // request access to the webcam
            // await webcam.play();
            // // this 붙여줘야!!!!!!!!!!!
            // window.requestAnimationFrame(this.loop);
    
            // // append/get elements to the DOM
            // const canvas = document.getElementById("canvas");
            // canvas.width = width; 
            // canvas.height = height;
            // ctx = canvas.getContext("2d");
            // labelContainer = document.getElementById("label-container");
            // for (let i = 0; i < maxPredictions; i++) { // and class labels
            //     labelContainer.appendChild(document.createElement("div"));
            //     }
            
            },
        methods:{
            stop() {
                console.log("click stop btn",this.requestId)
                if (this.requestId) {
                    window.cancelAnimationFrame(this.requestId);
                    this.requestId = undefined;
                    console.log("click stop btn",this.requestId)
                }   
            },
            restart() {
                console.log("click restart btn",this.requestId)
                if (!this.requestId) {
                    this.requestId = window.requestAnimationFrame(this.loop);
                    console.log("click restart btn",this.requestId)
                }
            },

            async init() {
                this.startBtn = false;
               
                console.log("click the start btn")
                // 로딩이 시작
                this.loading = true;

                
                const modelURL = URL + "model.json";
                const metadataURL = URL + "metadata.json";
    
                // load the model and metadata
                // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
                // Note: the pose library adds a tmPose object to your window (window.tmPose)
                model = await tmPose.load(modelURL, metadataURL);
                console.log("model",model)
                maxPredictions = model.getTotalClasses();

                // 예상 소요시간 > 디폴트소요시간이면 갱신
                console.log("this.prediction",this.predictionTime)
                if (this.predictionTime < maxPredictions) {
                    this.predictionTime = maxPredictions
                }
                console.log("this.prediction",this.predictionTime)
                console.log("maxPredictions",maxPredictions)
               
        
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
                console.log("Here is init",this.requestId)
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
                
                if (this.startTime){
                    console.log("starttime",timestamp)
                }
                this.startTime = false;

                webcam.update(); // update the webcam frame
                //this.predict 로 this 붙여줘야!!!!!!!!!!!
                await this.predict();

                // stop&restart제어하기
                if (this.requestId) {
                    this.requestId = window.requestAnimationFrame(this.loop);
                }
                
                console.log(this.requestId)
                },
    
            async predict() {
                // Prediction #1: run input through posenet
                // estimatePose can take in an image, video or canvas html element
                const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
                // Prediction 2: run input through teachable machine classification model
                const prediction = await model.predict(posenetOutput);
        
                for (let i = 0; i < maxPredictions; i++) {
                    const classPrediction =
                        prediction[i].className + ": " + prediction[i].probability.toFixed(2);
                    labelContainer.childNodes[i].innerHTML = classPrediction;
                }
        
                // finally draw the poses
                //this 붙여줘야!!!!!!!!!!!
                this.drawPose(pose);
            },
        
            drawPose(pose) {
                if (webcam.canvas) {
                    ctx.drawImage(webcam.canvas, 0, 0);
                    // draw the keypoints and skeleton
                    if (pose) {
                        const minPartConfidence = 0.5;
                        tmPose.drawKeypoints(pose.keypoints, minPartConfidence, ctx);
                        tmPose.drawSkeleton(pose.keypoints, minPartConfidence, ctx);
                    }
                }
            }
        }
        //     async  loop(timestamp) {
        //     webcam.update(); // update the webcam frame
        //     await predict();
        //     window.requestAnimationFrame(loop);
        //     },
    
        //     async  predict() {
        //         // Prediction #1: run input through posenet
        //         // estimatePose can take in an image, video or canvas html element
        //         const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
        //         // Prediction 2: run input through teachable machine classification model
        //         const prediction = await model.predict(posenetOutput);
        
        //         for (let i = 0; i < maxPredictions; i++) {
        //             const classPrediction =
        //                 prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        //             labelContainer.childNodes[i].innerHTML = classPrediction;
        //         }
        
        //         // finally draw the poses
        //         drawPose(pose);
        //     },
        
        //     drawPose(pose) {
        //         if (webcam.canvas) {
        //             ctx.drawImage(webcam.canvas, 0, 0);
        //             // draw the keypoints and skeleton
        //             if (pose) {
        //                 const minPartConfidence = 0.5;
        //                 tmPose.drawKeypoints(pose.keypoints, minPartConfidence, ctx);
        //                 tmPose.drawSkeleton(pose.keypoints, minPartConfidence, ctx);
        //             }
        //         }
        //     }
        // }
    }
</script>
    
<style>

</style>