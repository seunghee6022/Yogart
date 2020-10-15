<template>
    <div class="py-5">
        <UserProfile/>
        <div class="">
            <div class="btn-wrap">
                <button class="ptBtn" @click="onPTMang">PT관리</button>
                <button class="logBtn" @click="onExerHis">운동기록</button>
            </div>
            <div id="schedule" class="schedule ptBorder">
                <PTManagement :ptManagement="ptManagement"/>
                <ExerciseHistory :exerciseHistory="exerciseHistory"/>
            </div>
        </div>
        
    </div>
</template>

<script>
import UserProfile from '@/components/mypage/UserProfile.vue'
import PTManagement from '@/components/mypage/PTManagement.vue'
import ExerciseHistory from '@/components/mypage/ExerciseHistory.vue'

export default {
    name : 'MyPage',
    components : {
        UserProfile,
        PTManagement,
        ExerciseHistory
    },
    data() {
        return {
            ptManagement : true,
            exerciseHistory : false,
        }
    },
    mounted() {
        if (!this.$store.state.isLogin) {
            $('#loginStaticBackdrop').modal('show')
        }
    },
    methods : {
   
        onPTMang() {
            this.ptManagement = true
            this.exerciseHistory = false
            document.getElementById('schedule').classList.remove('logBorder')
            document.getElementById('schedule').classList.add('ptBorder')

 
        },
        onExerHis() {
            this.ptManagement = false
            this.exerciseHistory = true
            document.getElementById('schedule').classList.remove('ptBorder')
            document.getElementById('schedule').classList.add('logBorder')

        },
    }
}
</script>

<style scoped>
.schedule {
    margin-left: 10vw;
    margin-right: 10vw;
    padding : 1rem;
    min-height: 500px;
    background-color: rgba(255, 255, 255, 0.7);
    border-radius: 0px 30px 30px 30px;
}
.ptBorder {
    border : 3px solid rgba(143, 160, 242, 1);
}
.logBorder {
    border : 3px solid rgba(242, 157, 143, 1);
}
.ptBtn {
    background-color: rgba(143, 160, 242, 1);
    color: white;
    border: none;
    border-radius: 30px 0px 0px 0px;
    outline: none !important;
    width: 150px;
    height: 50px;
}
.ptBtn:hover {
    background-color: rgba(143, 160, 242, 0.8);
}
.logBtn {
    background-color: rgba(242, 157, 143, 1);
    color: white;
    border: none;
    border-radius: 0px 30px 0px 0px;
    outline: none !important;
    width: 150px;
    height: 50px;
}
.logBtn:hover {
    background-color: rgba(242, 157, 143, 0.8);
}
.btn-wrap {
    margin-left: 10vw;
    margin-right: 10vw;
    text-align: left;
}


</style>
