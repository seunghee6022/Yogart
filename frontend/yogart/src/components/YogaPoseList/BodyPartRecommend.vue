<template>
    
    <div class="m-5">
        <div id="example">
        <h3 class="my-5 y-border" style="font-weight:500px;">요가트의 추천</h3>
        <carousel-3d :controls-visible="true"  :controls-prev-html="'&#10092;'" :controls-next-html="'&#10093;'">
            <!-- figcaption -->
            <slide class="slide" :index="0">
                <figure>
                    <img :src="require('../../../public/yogaList/legs.jpg')"/>
                     <figcaption @click="clickSet(7)">
                         다리 집중 요가
                    </figcaption>
                </figure>
              
            </slide>
            <slide class="slide" :index="1" >
                <figure>
                    <img :src="require('../../../public/yogaList/arm.jpg')"/>
                     <figcaption @click="clickSet(6)">
                         팔뚝 집중 요가
                    </figcaption>
                </figure>
            </slide>
            <slide class="slide" :index="2" >
                <figure>
                    <img :src="require('../../../public/yogaList/abs.jpg')"/>
                     <figcaption @click="clickSet(5)">
                         코어 강화
                    </figcaption>
                </figure>
            </slide>
            <slide class="slide" :index="3">
                <figure>
                    <img :src="require('../../../public/yogaList/spine.jpg')"/>
                     <figcaption @click="clickSet(4)">
                         척추 강화
                    </figcaption>
                </figure>
            </slide>
            <slide class="slide" :index="4">
                <figure>
                    <img  :src="require('../../../public/yogaList/body.jpg')"/>
                     <figcaption @click="clickSet(3)">
                         전신 요가
                    </figcaption>
                </figure>
            </slide>
            <slide class="slide" :index="5">
                <figure>
                    <img :src="require('../../../public/yogaList/relax.jpg')"/>
                     <figcaption @click="clickSet(1)">
                         바디 릴렉싱
                    </figcaption>
                </figure>
              
            </slide>
            <slide class="slide" :index="6" >
                <figure>
                    <img :src="require('../../../public/yogaList/energy.jpg')"/>
                     <figcaption @click="clickSet(2)">
                         에너지 넘치는 아침
                    </figcaption>
                </figure>
            </slide>     
        </carousel-3d>
        </div>
      
    </div>
</template>

<script>
    import {Carousel3d, Slide} from 'vue-carousel-3d';
    export default {
        name : 'BodyPartRecommend',
        data() {
            return{
               
            }
        },
        components: {
            Carousel3d,
            Slide
        },
        created() {
            
        },
        methods: {
            clickSet(num){
                this.$emit("clickSet", num)
            }
        }
 
    }

</script>
    
<style scoped>
img{
    width :360px;
    height:270px;
}
/* @import url(https://fonts.googleapis.com/css?family=Anaheim);

*{
  margin: 0;
  padding: 0;
  outline: none;
  border: none;
    box-sizing: border-box;
}
*:before,
*:after{
    box-sizing: border-box;
}
html,
body{
    min-height: 100%;
}
body{
    background-image: radial-gradient(mintcream 0%, lightgray 100%);
}
h1{
    display: table;
    margin: 5% auto 0;
    text-transform: uppercase;
    font-family: 'Anaheim', sans-serif;
    font-size: 4em;
    font-weight: 400;
    text-shadow: 0 1px white, 0 2px black;
}
.container{
    margin: 4% auto;
    width: 210px;
    height: 140px;
    position: relative;
    perspective: 1000px;
}
#carousel{
    width: 100%;
    height: 100%;
    position: absolute;
    transform-style: preserve-3d;
    animation: rotation 20s infinite linear;
}
#carousel:hover{
    animation-play-state: paused;
}
#carousel figure{
    display: block;
    position: absolute;
    width: 200px;
    height: 180px;
    left: 10px;
    top: 10px;
    background: black;
    overflow: hidden;
    border: solid 5px black;
}
#carousel figure:nth-child(1){transform: rotateY(0deg) translateZ(300px);}
#carousel figure:nth-child(2) { transform: rotateY(51.5deg) translateZ(300px);}
#carousel figure:nth-child(3) { transform: rotateY(103deg) translateZ(300px);}
#carousel figure:nth-child(4) { transform: rotateY(154.5deg) translateZ(300px);}
#carousel figure:nth-child(5) { transform: rotateY(206deg) translateZ(300px);}
#carousel figure:nth-child(6) { transform: rotateY(257.5deg) translateZ(300px);}
#carousel figure:nth-child(7) { transform: rotateY(309deg) translateZ(300px);}


img{
    -webkit-filter: grayscale(1);
    cursor: pointer;
    transition: all .5s ease;
}
img:hover{
    -webkit-filter: grayscale(0);
  transform: scale(1.2,1.2);
}

@keyframes rotation{
    from{
        transform: rotateY(0deg);
    }
    to{
        transform: rotateY(360deg);
    }
} */
</style>