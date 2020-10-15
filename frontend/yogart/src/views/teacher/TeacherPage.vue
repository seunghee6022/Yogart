<template>
    <div>
        <!-- 수업 설정, 수업 진행, 수업 현황 -->
        <div class="m-5">
            <b-tabs content-class="mt-3">
                <b-tab title="오늘의 수업" active><PtAdmin /></b-tab>
                <b-tab title="예약된 수업"><PtList /></b-tab>
                <b-tab title="수업 추가"><ClassSetting /></b-tab>
            </b-tabs>
        </div>
    </div>
</template>

<script>
import PtAdmin from '@/components/teacher/PtAdmin.vue'
import PtList from '@/components/teacher/PtList.vue'
import ClassSetting from '@/components/teacher/ClassSetting.vue'

export default {
    name: 'TeacherPage',
    components: {
        PtAdmin,
        PtList,
        ClassSetting,
    },
    data() {
        return {
            teacherInfo: null,
            SERVER_URL: this.$store.state.SERVER_URL,
        }
    },
}
</script>

<style>
.page-name {
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}
</style>