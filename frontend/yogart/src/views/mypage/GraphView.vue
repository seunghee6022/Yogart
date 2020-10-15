<template>
    <div style="margin: 50px; ">
        <carousel-3d :controls-visible="true" :width="720" :height="450">
            <slide class="slide" :index="0">
                <figure>
                    <TimeGraph :time="time" />
                </figure>
            </slide>
            <slide class="slide" :index="1">
                <figure>
                    <CalendarGraph :calendar="calendar" />
                </figure>
            </slide>
            <slide class="slide" :index="2">
                <figure>
                    <BodyGraph :body="body" />
                </figure>
            </slide>        
        </carousel-3d>
    </div>
</template>

<script>
// @ is an alias to /src
import TimeGraph from '@/components/graph/TimeGraph.vue'
import CalendarGraph from '@/components/graph/CalendarGraph.vue'
import BodyGraph from '@/components/graph/BodyGraph.vue'
import axios from 'axios'
import {Carousel3d, Slide} from 'vue-carousel-3d';

export default {
    name: 'GraphView',
    components: {
        TimeGraph,
        CalendarGraph,
        BodyGraph,
        Carousel3d,
        Slide
    },
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL,
            time: null,
            calendar: null,
            body: null,
        }
    },
    created() {
      this.getData()  
    },
    methods: {
        getData() {
             axios.get(this.SERVER_URL + `/api/mypage/graph`, { 'headers': { 'auth-token': window.$cookies.get('auth-token') } })
                .then(res => {
                    this.time = res.data.timeCount
                    this.calendar = res.data.attendance
                    this.body = res.data.tags
                })
                .catch(err => {
                    console.error(err)
                })
        }
    },
}
</script>

<style scoped>

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
