<template>
  <div class="top-75">
        <div class="under-border">
            <h2 class="page-name text-left">수업 개설</h2>
        </div>
        
        <div class="d-flex mb-3 justify-content-center">
        <div class="mr-3">
            <div class="field-row">
                <input name="class_name" id="class_name" v-model="ptName" type="text" required autofocus/>
                <label for="class_name">수업명</label> 
            </div>
            <div class="field-row">
                <input name="class_price" id="class_price" v-model="ptPrice" type="text" required/>
                <label for="class_price">회당 스푼</label> 
            </div>
            <div class="field-row">
                <input name="class_intro" id="class_intro" v-model="ptIntro" type="text" required/>
                <label for="class_intro">수업 소개</label> 
            </div>
        </div>
        <div style="margin-top:30px;">
            <div id="makeClassBtn" @click="onSubmit">수업 생성</div>
        </div>
    </div>


     <p  class="info">* 원하는 요일과 시간대를 클릭해서 수업을 스케줄링해보세요.</p>
    
        <table class="table">
        <thead>
            <tr>
            <th scope="col">시</th>
            <th scope="col">월</th>
            <th scope="col">화</th>
            <th scope="col">수</th>
            <th scope="col">목</th>
            <th scope="col">금</th>
            <th scope="col">토</th>
            <th scope="col">일</th>
            </tr>
        </thead>
        <tbody>
            <TimeTable class="time-table" v-for="time in times" :key="time" :time="time" :clicked="clicked" @cellClicked="cellClicked"></TimeTable>
        </tbody>
        </table>
    </div>
</template>

<script>
import axios from 'axios'
import TimeTable from './TimeTable.vue'

export default {
    name: 'ClassSetting',
    components: {
        TimeTable
    },
    props: {
        teacherInfo: Object,
    },
    data() {
        return {
            times: [12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
            clicked: [],
            ptName: null,
            ptPrice: null,
            ptIntro: null,
            SERVER_URL: this.$store.state.SERVER_URL,
        }
    },
    methods: {
        cellClicked(payload) {
            if (payload.isClicked) {
                this.clicked.push({day: payload.day, time: payload.time})
            } else {
                const idx = this.clicked.findIndex(dayTime => {
                    return dayTime.day === payload.day && dayTime.time === payload.time
                })
                if (idx > -1) this.clicked.splice(idx, 1)
            }
        },
        onSubmit(e) {
            e.preventDefault()
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token')
                }
            }
            if (this.ptPrice < 0) {
                alert('돈을 주면서 수업 하시게요?')
                return
            }
            const ptData = {
                ptName: this.ptName,
                ptPrice: this.ptPrice,
                ptIntro: this.ptIntro,
                clicked: this.clicked,
            }
            // console.log(ptData)
            // 데이터 저장을 요청합니다. 데이터를 저장하며 clicked에 맞춰 수업을 생성해야 합니다.
            axios.post(this.SERVER_URL + '/api/teachers/pt-create', ptData, requestHeaders)
            .then(res => {
                // console.log(res)
                // 강사 상세페이지로 이동
                // console.log(this.teacherInfo)
                document.getElementById('class_name').value = ''
                document.getElementById('class_price').value = ''
                document.getElementById('class_intro').value = ''
                alert(`${ptData.ptName} 수업이 생성되었습니다.`)
                this.$router.push(`/classandteacher`)
            })
            .catch(err => console.error(err))
        }
    },
}
</script>

<style scoped>
.top-75{
    width:75%;
    margin : auto;
    margin-top: 40px;
}
.info{
    color: rgba(0, 0, 0, 0.6);
    font-size: 10px;
    text-align: left;
} 
.under-border{
    padding : 0;
}
#makeClassBtn{
    width : 100px;
    height : 190px;
    line-height: 190px;
    background-color: rgba(143, 160, 242, 1);
    color : white;
    border :2px solid white;
    border-radius : 10px; 
    font-size: 1.2rem;
    font-weight: 500px;
}
#makeClassBtn:hover{
    cursor: pointer;
    background-color: rgba(255, 255, 255, 0.5);
    color : rgba(143, 160, 242, 1);
    border :2px solid rgba(143, 160, 242, 1);
}
th {
    background-color: rgba(143, 160, 242, 1);
    color : white;
    border :2px solid white;
    height : 5px;
    line-height: 25px;
    font-size : 15px;
}
@import 'https://fonts.googleapis.com/css?family=Titillium+Web';
.field-row {
	font-family: 'Titillium Web', sans-serif;
	margin:30px 0 0 10px;
	position:relative;

}
label {
	font-size:16px;
	color:#888;
	position:absolute;
	top: 10px;
    bottom: 0;
    left: 10px;
	transition: .3s ease;
	cursor:text;
}
input {
    width : 400px;
    background :rgba(255, 255, 255, 0.6);
	font-size:16px;
	line-height:18px;
	padding: 10px 10px 10px 0;
	border:0;
	border-bottom:1px solid #ccc;
	outline:none;
    padding-left : 5px;
	
}
input:focus {
	border-color:#888;
}
input:focus ~ label, input:valid ~ label {
	font-size:15px;
	
	color:black;
	transform: translate3d(0, -30px, 0);
}
.select{
    background-color: rgba(143, 160, 242, 0.6);
}
.time-table {
    background-color: rgba(143, 160, 242, 0.3);
}
.time-table:hover {
    cursor: pointer;
    background-color: rgba(143, 160, 242, 0.6);
}
</style>
