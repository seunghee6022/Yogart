<template>
  <div class="AICoachingPage m-5">

    <AI/>
  

  </div>
</template>

<script>
// @ is an alias to /src
import AI from '@/components/AI.vue'



export default {
  name: 'AICoachingPage',
  components: {
    AI,
  },
  data() {
    return {
      test: ''
    }
  },
  created() {
    this.test = this.$store.state.test 
  },
  
}

</script>
<style >
.highlight {
    background-image: linear-gradient(to right, #efaa91 0%, #efaa91 100%);
    background-position: 0 0.70em;
    background-repeat: repeat-x;
    background-size: 1px 14px;
    font-weight: bold !important;
}
/* .AICoachingPage{
  background-color: rgba(256, 256, 256, 0.3);
  width : 75%;
  font-family: "futura-pt", sans-serif;
  font-style: normal;
  font-weight: 500;
  letter-spacing: 2px;
  line-height: 1.2;
  border-radius: 40px;
  margin : auto;
  
} */
#AI{
  padding : 5px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 10px;
  width : 50%;
  height :550px;
  font-weight: 500;
  letter-spacing: 2px;
  line-height: 1.2;
  /* margin : 0;
  margin-bottom: 10px; */
  margin : 10px;
}
.btn2{
    position: absolute;
    /* top: 50%;
    left: 50%; */
    transform: translate(-50%,-50%);
    width: 300px;
    height: 50px;
    text-align: center;
    line-height: 50px;
    color: #fff;
    font-size: 24px;
    text-transform: uppercase;
    text-decoration: none;
    box-sizing: border-box;
    background: linear-gradient(90deg,#5bcedf, #f96161, #fc8f46, #36e494);
    background-size: 400%;
    border-radius: 30px;
    z-index: 1;
}
.btn2:hover{
    text-decoration: none;
    color : white;
    animation: start2-animate 8s linear infinite;
}
.btn2:before{
    content: '';
    position: absolute;
    top: -5px;
    left: -5px;
    right: -5px;
    bottom: -5px;
    z-index: -1;
    background: linear-gradient(90deg,#5bcedf, #f96161, #fc8f46, #36e494);
    background-size: 400px;
    border-radius: 40px;
    opacity: 0;
    transition: 0.5s;
}
.btn2:hover:before{
    filter: blur(10px);
    opacity: 1;
}

@keyframes start2-animate {
    0%{
        background-position: 0%;
    }
    100%{
        background-position: 400%;
    }
}
.round-image {
  display: inline-block;
  width: 200px;
  height: 200px;
  border-radius: 50%;

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
}
</style>
