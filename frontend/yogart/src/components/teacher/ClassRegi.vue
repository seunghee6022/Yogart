<template>
    <div>
        <div class="white-box mb-5 ">
            <div class="under-border mb-5">
                <h2>{{ ptInfo.ptName }}</h2>
            </div>
            <div>
                <h4>{{ ptInfo.ptIntro }}</h4>
            </div>
            <div>
                <h5 style="color:#8fa0f2">{{ ptInfo.ptPrice }} 스푼으로 함께해요</h5>
            </div>
            <br>
            <label id="info">아래의 파란 박스를 클릭해서 원하는 일정의 수업을 신청해보세요</label>
            <div>
                <DatePicker id="date-picker" format="yyyy-MM-dd-D" :highlighted="highlighted" @selected="selectDate" placeholder="클릭해서 원하는 일정을 선택해보세요" :language="ko"></DatePicker>
                <div class="page-index d-flex-colum">
                    <div v-for="time in showArray" :key="time.time">
                        <PtListItem :ptInfo="ptInfo" :time="time" :clickedDate="clickedDate"></PtListItem>
                    </div>
                </div>
            </div>
        </div>
        <div class="bottom-margin"></div>
        <!-- 데이터 피커를 이용해 날짜를 받으면, 그 날짜에 열리는 모든 수업을 리스팅한다. -->
    </div>
</template>

<script>
import axios from 'axios'
import DatePicker from 'vuejs-datepicker'
import { ko } from 'vuejs-datepicker/dist/locale'
import PtListItem from './PtListItem.vue'
import ReviewList from '../review/ReviewList.vue'


export default {
    name: 'ClassRegi',
    components: {
        DatePicker,
        PtListItem,
        ReviewList,
    },
    props: {
        ptId: Number,
    },
    methods: {
        selectDate(date) {
            const today = new Date()
            this.showArray.length = 0
            if (date.getMonth() >= today.getMonth() && date.getDate() >= today.getDate()){
            // 유저가 날짜를 선택하면 그날에 열리는 수업을 보여줘야 합니다.
                this.clickedDate = date
                let day = date.getDay()
                let self = this
                if (Math.abs(this.clickedDate.getDate() - new Date().getDate()) > 15) {
                    self.clickedDate = null
                    return
                }
                this.showArray = this.ptTimes.filter(pt => {
                    let isSoldOut = true
                    for (let k = 0; k < self.soldOut.length; k++) {
                        if (date.getDate() === self.soldOut[k].getDate() && pt.ptTime === self.soldOut[k].getHours()) {
                            isSoldOut = false
                        }
                    }
                    return pt.ptDay === day && isSoldOut
                })
                this.showArray.sort(function(a, b) {
                    return a.ptTime - b.ptTime
                })
            }
        }
    },
    data() {
        return {
            highlighted: {
                dates: [],
            },
            ko: ko,
            ptInfo: {
                ptTeacher: null,
                ptId: null,
                ptName: null,
                ptPrice: null,
                ptIntro: null,
            },
            ptTimes: [],
            soldOut: [],
            showArray: [],
            clickedDate: null,
            SERVER_URL: this.$store.state.SERVER_URL,
            ptUrl: null,
        }
    },
    mounted() {
        // document.getElementById("date-picker").setAttribute("placeholder", "클릭해서 원하는 일정을 선택해보세요.");
        // 강사의 수업 정보와 이미 예약된 PT리스트를 가져옵니다.
        axios.get(this.SERVER_URL + `/api/teachers/pt/${this.ptId}`, this.ptId)
        .then(res => {
            // console.log(res)
            this.ptTimes = res.data.clicked
            this.ptInfo.ptTeacher = res.data.ptInfo.ptTeacherId
            this.ptInfo.ptId = res.data.ptInfo.ptId
            this.ptInfo.ptName = res.data.ptInfo.ptName
            this.ptInfo.ptPrice = res.data.ptInfo.ptPrice
            this.ptInfo.ptIntro = res.data.ptInfo.ptIntro
            const self = this
            res.data.soldOut.forEach(function(e){
                let yyyy = Number(e.slice(0, 4))
                let mm = Number(e.slice(5,7)) - 1
                let dd = Number(e.slice(8, 10))
                let hh = Number(e.slice(11, 13))
                self.soldOut.push(new Date(yyyy, mm, dd, hh))
            })
            const today = new Date() // 오늘의 날짜
            for (let i = 0; i < 14; i++) {
                let cnt = 0
                let nextDay = new Date(today)
                nextDay.setDate(today.getDate() + i) // 오늘 + i 날
                let day = nextDay.getDay()
                for (let j = 0; j < self.ptTimes.length; j++) {
                    if (self.ptTimes[j].ptDay === day) {
                        cnt++
                    }
                }
                for (let j = 0; j < self.soldOut.length; j++) {
                    if (nextDay.getDate() === self.soldOut[j].getDate()) {
                        cnt--
                    }
                }
                if (cnt > 0) {
                    self.highlighted.dates.push(nextDay)
                }
            }
        })
        .catch(err => console.error(err))
    }
}
</script>

<style>
.bottom-margin {
    margin: 300px;
}
#date-picker {
    background-color: rgba(143, 160, 242, 0.5);
    border : 2px solid rgba(0,0,0,0);
    border-bottom : 1px solid black;
    width : 100%;
}
#info{
    color : gray;
    font-size : 10px;
}

</style>