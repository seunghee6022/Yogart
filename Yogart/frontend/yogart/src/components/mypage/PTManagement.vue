<template>
    <div v-if="ptManagement">
        <span>
            <button class="past-btn" @click="pastPT()">과거 수업 내역</button>
            <button class="today-btn active" @click="todayPT()">오늘 진행 수업</button>
            <button class="future-btn" @click="futurePT()">진행 예정인 수업</button>
        </span>
        <div class="my-3">
            <div v-if="today">
                <table class="table">
                    <thead class="t-head container">
                        <tr class="row m-0">
                            <th class="col-2">강사명</th>
                            <th class="col-5">수업명</th>
                            <th class="col-3">수업일시</th>
                            <th class="col-2"></th>
                        </tr>
                    </thead>
                    <tbody class="container">
                        <tr class="row m-0" v-for="todayCourse in todayCourses" :key="todayCourse.courseID">
                            <td class="col-2">{{todayCourse.ptClickedName.ptTeacherId.userName}}</td>
                            <td class="col-5 text-left">{{todayCourse.ptClickedName.ptName}}</td>
                            <td class="col-3">{{todayCourse.dateTime}}</td>
                            <td class="col-2"><button @click="goPt(todayCourse)" class="enter-btn">입장</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div v-if="past">
                <table class="table">
                    <thead class="t-head container">
                        <tr class="row m-0">
                            <th class="col-2">강사명</th>
                            <th class="col-5">수업명</th>
                            <th class="col-3">수업일시</th>
                            <th class="col-2">출석현황</th>
                        </tr>
                    </thead>
                    <tbody class="container">
                        <tr class="row m-0" v-for="pastCourse in pastCourses" :key="pastCourse.courseID">
                            <td class="col-2">{{pastCourse.ptClickedName.ptTeacherId.userName}}</td>
                            <td class="col-5 text-left">{{pastCourse.ptClickedName.ptName}}</td>
                            <td class="col-3">{{pastCourse.dateTime}}</td>
                            <td class="col-2">{{pastCourse.isAttend}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div v-if="future">
                <table class="table">
                    <thead class="t-head container">
                        <tr class="row m-0">
                            <th class="col-3">강사명</th>
                            <th class="col-6">수업명</th>
                            <th class="col-3">수업일시</th>
                        </tr>
                    </thead>
                    <tbody class="container">
                        <tr class="row m-0" v-for="futureCourse in futureCourses" :key="futureCourse.courseID">
                            <td class="col-3">{{futureCourse.ptClickedName.ptTeacherId.userName}}</td>
                            <td class="col-6 text-left">{{futureCourse.ptClickedName.ptName}}</td>
                            <td class="col-3">{{futureCourse.dateTime}}</td>
                        </tr>
                    </tbody>
                </table>
            </div> 
        </div>   
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name : 'PTManagement',
    props : {
        ptManagement : Boolean,
    },
    data() {
      return {
        SERVER_URL : this.$store.state.SERVER_URL,
        past : false,
        today : true,
        future : false,
        pastCourses : [],
        todayCourses : [],
        futureCourses : [],

      }
    },
    mounted() {
      this.getPT()
    },
    methods : {
      goPt(todayCourse) {
        window.open(`https://i3d202.p.ssafy.io:8080/livePT.html?room=${todayCourse.ptClickedName.ptTeacherId.teacherCode}`)
      },
      getPT() {
        //오늘 날짜 기준으로 과거,오늘,미래로 나눠서 1:1 PT수업리스트를 받는 함수
        // user(auth-token을 보내주면 그 유저에 연결된 1:1 pt 수업을 보내주세요 - 내용 : 수업명, 수업id, 강사명, 출석현황(isAttend),수업날짜(dataTime) 포함된 object의 리스트)
        const RequestHeaders = {
          headers : {
            Authorization : this.$cookies.get('auth-token')
          }
        }
        axios.get(this.SERVER_URL + '/api/mypage/ptlist', RequestHeaders)
        .then(res => {
          //과거 pt 기록들 날짜,시간순으로 배열로 넘겨주세요
          this.pastCourses = res.data.pastCourses
          this.pastCourses.forEach(pastCourse => {
              if (pastCourse.isAttend === true) {
                  pastCourse.isAttend = '출석'
              } else {
                  pastCourse.isAttend = '결석'
              }
          });
          //오늘 pt 기록들 시간순으로 배열로 넘겨주세요
          this.todayCourses = res.data.todayCourses
        //   console.log(this.todayCourses)
          //미래 pt 기록들 날짜,시간순으로 배열로 넘겨주세요
          this.futureCourses = res.data.futureCourses
        })
        .catch(err => console.error(err))
      },
      pastPT() {
        this.past = true,
        this.today = false,
        this.future = false
        document.getElementsByClassName('active')[0].classList.remove('active')
        document.getElementsByClassName('past-btn')[0].classList.add('active')
      },
      todayPT() {
        this.past = false,
        this.today = true,
        this.future = false
        document.getElementsByClassName('active')[0].classList.remove('active')
        document.getElementsByClassName('today-btn')[0].classList.add('active')
      },
      futurePT() {
        this.past = false,
        this.today = false,
        this.future = true
        document.getElementsByClassName('active')[0].classList.remove('active')
        document.getElementsByClassName('future-btn')[0].classList.add('active')
      }
    }
}
</script>

<style scoped>
.past-btn {
    border: 2px solid rgba(143, 160, 242, 0.8);
    border-radius: 10px 0px 0px 10px;
    background: rgba(255, 255, 255, 0);
    height: 40px;
    outline: none;
}
.today-btn {
    border-top: 2px solid rgba(143, 160, 242, 0.8);
    border-bottom: 2px solid rgba(143, 160, 242, 0.8);
    border-left: none;
    border-right: none;
    background: rgba(255, 255, 255, 0);
    height: 40px;
    outline: none;
}
.future-btn {
    border: 2px solid rgba(143, 160, 242, 0.8);
    border-radius: 0px 10px 10px 0px;
    background: rgba(255, 255, 255, 0);
    height: 40px;
    outline: none;
}
.active {
    background-color: rgba(143, 160, 242, 0.8) !important;
    color: white !important;
}
.t-head {
    border-top: 2px solid rgba(143, 160, 242, 0.8);
}
th {
    border: none;
}
tbody {
    border-bottom: 2px solid rgba(143, 160, 242, 0.8);
}
.enter-btn {
    width: 60px;
    border-radius: 10px;
    border: 0px;
    background-color: rgba(143, 160, 242, 0.8);
    color: white;
}
.enter-btn:hover {
    background-color: rgba(143, 160, 242, 0.6);
}
</style>