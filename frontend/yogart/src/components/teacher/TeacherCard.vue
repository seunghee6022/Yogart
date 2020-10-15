<template>
  <div class="col-sm-4 box">
          <div class="d-flex flex-column align-items-center">
            <img v-if="teacher.userProfile" :src="imgUrl(teacher.userEmail)" class="user-profile" alt="...">
            <img v-else src="../../assets/userDefault.jpg" class="user-profile" alt="...">
            <h5>{{ teacher.userName }}</h5>
          <p v-if="teacher.userIntro">{{ teacher.userIntro }}</p>
          <p v-else>강사 소개를 해주세요.</p>
          </div>
      </div>
</template>

<script>
export default {
    name: 'TeacherCard',
    props: {
        teacher: Object,
    },
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL
        }
    },
    mounted() {
      // console.log(this.teacher)
    },
    methods: {
        imgUrl(userEmail) {
            // console.log(userEmail)
            return `${this.SERVER_URL}/api/users/profileImageByEmail?userEmail=${userEmail}`
        },
    }

}
</script>

<style scoped>
.user-profile {
  display: inline-block;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border : 2px solid rgba(255, 255, 255, 0.5);
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  margin-bottom: 10px;
}
.box{
    padding :10px;
    background-color: rgba(255, 255, 255, 0.3);
    box-shadow: 3px 3px rgba(143, 160, 242, 0.5);
}

.box:hover{
    cursor:pointer;
    background-color: rgba(143, 160, 242, 0.2);
    border : 2px solid rgba(143, 160, 242, 0.5);
}
.container{
  padding : 0
}
</style>
