<template>   
    <div class="mt-5 box" @click="showModal">
        <div class="d-flex justify-content-between px-5 py-2">
            <span>{{ ptInfo.ptName }} (  {{ time.ptTime }}시 )</span> 
             <span class="apply">신청할래요~ Click</span>
            <span> {{ ptInfo.ptPrice }}스푼</span> 
        </div>
        <b-modal ref="my-modal" hide-footer :title="modalTitle">
            <div class="d-block text-center">
                <h3>{{ptInfo.ptName}}<br>
                {{ dDay }} {{ time.ptTime }}시</h3>
            </div>
                <b-button class="mt-3" variant="outline-danger" @click="ptRegi" block>신청하기</b-button>
        </b-modal>
    </div>
     
  
</template>

<script>
import axios from 'axios'

export default {
    name: 'PtListItem',
    props: {
        ptInfo: Object,
        time: Object,
        clickedDate: Date,
    },
    data() {
        return {
            modalTitle: `${ this.ptInfo.ptPrice } 스푼을 사용하시겠습니까?`,
            SERVER_URL: this.$store.state.SERVER_URL,
            ptDate: null,
        }
    },
    mounted() {
        this.clickedDate.setHours(this.time.ptTime)
        this.ptDate = new Date(this.clickedDate.getYear()+1900, this.clickedDate.getMonth(), this.clickedDate.getDate(),this.clickedDate.getHours())
    },
    methods: {
        ptRegi() {
            // back으로 가격, clickedDate, ptInfo 보냅니다. 
            // 수업 생성하고, 유저 스푼 차감합니다.
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token')
                }
            }
            const ptData = {
                ptInfo: this.ptInfo,
                day: this.clickedDate.getDay(),
                time: this.ptDate,
            }
            axios.post(this.SERVER_URL + '/api/teachers/pt-regist', ptData, requestHeaders)
            .then(res => {
                this.$refs['my-modal'].hide()
                this.$router.push('/mypage')
            })
            .catch(err => {
                this.$router.push('/spoons')
            })
        },
        showModal() {
            this.$refs['my-modal'].show()
        },
    },
    computed: {
        dDay() {
            let year = this.clickedDate.getYear()
            let month = this.clickedDate.getMonth()
            let date = this.clickedDate.getDate()
            let day = this.clickedDate.getDay()
            const days = ['일', '월', '화', '수', '목', '금', '토']
            return `${year+1900}년 ${month+1}월 ${date}일 ${days[day]}요일`
        }
    }
}
</script>

<style scoped>
.box{
    width : 100%;
    box-shadow: 3px 3px rgba(143, 160, 242, 0.5);
    border-radius: 0;
}
.box:hover{
    cursor: pointer;
    background-color: rgba(143, 160, 242, 0.5);
}
/* .apply-btn{
    color : white;
    background-color:rgba(143, 160, 242, 0.7);
    border : 2px solid rgba(143, 160, 242, 0.7);
} 
.apply-btn:hover{
    cursor: pointer;
    background-color: rgba(0,0,0,0);
} */
.apply{
    visibility: hidden;
}
.box:hover .apply {
    color : #f29d8f;
    visibility: visible;
}


</style>