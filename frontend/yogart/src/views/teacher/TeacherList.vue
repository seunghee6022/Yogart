<template>
    <div style="margin-top:50px;">
        <div class="page-index">
            <div class="under-border text-left">
                <h2 class="page-name pb-2">강사 목록</h2>
            </div>
            <TeacherCard v-for="teacher in teachers" :key="teacher.id" :teacher="teacher" @click.native="onClick(teacher.id)"></TeacherCard>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import TeacherCard from '@/components/teacher/TeacherCard'

export default {
    name: 'TeacherList',
    components: {
        TeacherCard,
    },
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL,
            teachers: null,
        }
    },
    created() {
        axios.get(this.SERVER_URL + '/api/teachers/list')
        .then(res => {
            this.teachers = res.data
        })
        .catch(err => cosole.error(err))
    },
    methods: {
        onClick(teacherId) {
            this.$router.push(`/teachers/${teacherId}`)
        }
    }
}
</script>

<style scoped>

.page-index {
  
    width: 75%;
    margin: auto;
    margin-bottom : 1rem;
    /* border-bottom: 2px solid rgba(143, 160, 242, 0.5); */
}
.page-name {
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
    line-height: 100%;
    
}
.under-border{
    padding : 0;
}
</style>