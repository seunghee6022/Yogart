<template>
    <!-- 예약된 모든 PT를 확인할 수 있는 곳입니다. -->
    <div class="white-box" >
        <div class="under-border">
            <h2 class="page-name">예약된 PT 조회</h2>
        </div>
        
        <div class="d-flex flex-column box" v-for="pt in ptList" :key="pt.id">
            <div class="d-flex justify-content-between" style="color: rgba(0, 0, 0, 0.6); ">
                <h4>{{ pt.ptName }}</h4>
                <p>{{ pt.dateTime }}</p>
            </div>
            <h5>{{ pt.studentName }}</h5>
           
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'PtList',
    data() {
        return {
            ptList: [],
            SERVER_URL: this.$store.state.SERVER_URL,
        }
    },
    props: {
        teacherInfo: Object,
    },
    mounted() {
        // 아직 완료되지 않은 PT 내역을 보여줍니다.
        const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
        }
        axios.get(this.SERVER_URL + '/api/teachers/pt-list', requestHeaders)
        .then(res => {
            this.ptList = res.data.ptList
        })
        .catch(err => console.error(err))
    }
}
</script>

<style scoped>
.white-box{
    padding-top: 0px;
    margin-top: 10px;
    height: 100%;
}
.under-border{
    padding-top: 30px;
    padding-bottom : 0;
}
</style>
