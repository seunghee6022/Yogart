<template>
	<div class="main">
		<div class="index-image1"></div>
		<div class="loader1"></div>
		<div class="index1-btn button-container-1">
			<span class="mas1">코칭 시작</span>
			<button id='work' type="button" name="Hover" @click="clickAiCoaching()">START</button>
		</div>
		
		<div class="index-image2"></div>
		<div class="loader2"></div>
		<div class="index2-btn button-container-2">
			<span class="mas2">PT 시작</span>
			<button id='work' type="button" name="Hover" @click="clickPT()">START</button>
		</div>
	</div>  
</template>

<script>
export default {
    name : 'Home',
    methods: {
        clickAiCoaching() {
            this.$router.push('/coaching/yogaposelist')
        },
        clickPT() {
            this.$router.push('/classandteacher')
        }
    },
}
</script>

<style>
.index1-btn {
    float: right;
    position: absolute;
    bottom: 65vw;
    right: 10%;
}

.index2-btn {
    float: right;
    position: absolute;
    bottom: 15vw;
    left: 10%;
}

.button-container-1 {
	width: 10vw;
	height: 10vw;

	overflow: hidden;
	font-weight: 300;
	font-size: 2vw;
	border-radius: 10vw;
}

.button-container-2 {
	width: 10vw;
	height: 10vw;

	overflow: hidden;
	font-weight: 300;
	font-size: 2vw;
	border-radius: 10vw;
}

.button-container-1 button {
	width: 100%;
	height: 100%;
	font-weight: 300;
	font-size: 2vw;
	font-weight: bold;
	background: rgba(242, 157, 143, 1);
	-webkit-mask: url("https://raw.githubusercontent.com/robin-dela/css-mask-animation/master/img/nature-sprite.png");
	mask: url("https://raw.githubusercontent.com/robin-dela/css-mask-animation/master/img/nature-sprite.png");
	-webkit-mask-size: 2300% 100%;
	mask-size: 2300% 100%;
	border: none;
	color: #fff;
	cursor: pointer;
	-webkit-animation: ani2 0.7s steps(22) forwards;
	animation: ani2 0.7s steps(22) forwards;
}

.button-container-2 button {
	width: 100%;
	height: 100%;
	font-weight: 300;
	font-size: 2vw;
	font-weight: bold;
	background: rgba(143, 160, 242, 1);
	-webkit-mask: url("https://raw.githubusercontent.com/robin-dela/css-mask-animation/master/img/nature-sprite.png");
	mask: url("https://raw.githubusercontent.com/robin-dela/css-mask-animation/master/img/nature-sprite.png");
	-webkit-mask-size: 2300% 100%;
	mask-size: 2300% 100%;
	border: none;
	color: #fff;
	cursor: pointer;
	-webkit-animation: ani2 0.7s steps(22) forwards;
	animation: ani2 0.7s steps(22) forwards;
}

.button-container-1 button:hover {
	-webkit-animation: ani 0.7s steps(22) forwards;
	animation: ani 0.7s steps(22) forwards;
}

.button-container-2 button:hover {
	-webkit-animation: ani 0.7s steps(22) forwards;
	animation: ani 0.7s steps(22) forwards;
}

.mas1 {
	position: absolute;
    margin-top: 4vw;
    width: 10vw;
	color: #fff;
	text-align: center;
	font-weight: 300;
	position: absolute;
	font-size: 1.5vw;
	overflow: hidden;
	font-weight: bold;
}

.mas2 {
	position: absolute;
    margin-top: 4vw;
    width: 10vw;
	color: #fff;
	text-align: center;
	font-weight: 300;
	position: absolute;
	font-size: 1.5vw;
	overflow: hidden;
	font-weight: bold;
}

@keyframes ani {
	from {
		-webkit-mask-position: 0 0;
		mask-position: 0 0;
	}

	to {
		-webkit-mask-position: 100% 0;
		mask-position: 100% 0;
	}
}

@keyframes ani2 {
	from {
		-webkit-mask-position: 100% 0;
		mask-position: 100% 0;
	}

	to {
		-webkit-mask-position: 0 0;
		mask-position: 0 0;
	}
}

.loader1{
    float: right;
    position: absolute;
    bottom: 64.5vw;
    right: 9.5%;
    width: 11vw;
    height: 11vw;
    border-radius: 11vw;
    border: 0.5vw dashed rgba(255, 255, 255, 0.5);

    -webkit-transform-origin: 50% 50%;
            transform-origin: 50% 50%;
    -webkit-animation: loader1 3s linear infinite;
            animation: loader1 3s linear infinite;
}

.loader2{
    float: right;
    position: absolute;
    bottom: 14.5vw;
    left: 9.5%;
    width: 11vw;
    height: 11vw;
    border-radius: 11vw;
    border: 0.5vw dashed rgba(255, 255, 255, 0.5);

    -webkit-transform-origin: 50% 50%;
            transform-origin: 50% 50%;
    -webkit-animation: loader1 3s linear infinite;
            animation: loader1 3s linear infinite;
}

@-webkit-keyframes loader1{
    0%{-webkit-transform:rotate(0deg);}
    100%{-webkit-transform:rotate(360deg);}
}

@keyframes loader1{
    0%{transform:rotate(0deg);}
    100%{transform:rotate(360deg);}
}

.index-image1 {
    background-image: url("../assets/index/main1.jpg");
    height: 56vw;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
  
}
.index-image2 {
    background-image: url("../assets/index/main2.jpg");
    height: 56vw;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
}
.main {
    position: relative;
    top: -8vh;
}
</style>