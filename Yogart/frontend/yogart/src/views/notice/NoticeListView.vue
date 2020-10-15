<template>
    <div class="padding-for-nav">
        <h1 class="page-name">공지사항</h1>
        <table class="table m-auto">
            <thead class="container p-0">
                <tr class="head-tr row m-0">
                    <th class="col-9">제목</th>
                    <th class="col-3">등록일</th>
                </tr>
            </thead>
            <tbody class="container p-0">
                <tr class="table-list row m-0" v-for="notice in notices" :key="notice.noticeId" @click="callNoticeDetail(notice.noticeId)">
                    <td class="col-9 title">{{ notice.noticeTitle }}</td>
                    <td class="col-3">{{ notice.createDate }}</td>
                </tr>
            </tbody>
        </table>
        <div class="page">
            <div class="page-left" @click="clickPrev()"><i class="fas fa-chevron-left"></i></div>
            <div class="page-num">{{page}}</div>
            <div class="page-right" @click="clickNext()"><i class="fas fa-chevron-right"></i></div>
        </div>

        <paginate v-model="page" 
        :page-count="totalPages" 
        :page-range="0" 
        :margin-pages="0" 
        :click-handler="getNotices" 
        :prev-text="'이전 페이지'" 
        :next-text="'다음 페이지'" 
        :container-class="'pagination'" 
        :page-class="'page-item'"
        style="display: none;"> </paginate>


    </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios'
import Paginate from 'vuejs-paginate'
import Vue from 'vue'
Vue.component('paginate', Paginate)

export default {
    name: 'NoticeListView',
    data() {
        return {
            notices: null,
            searchNotice: "", 
            page: 1,
            totalPages: 10,
            SERVER_URL: this.$store.state.SERVER_URL,
        }
    },
    created() {
        window.scrollTo(0,0);
    },
    methods: {
        clickPrev() {
            var target = document.getElementsByClassName('pagination')[0].firstElementChild.firstElementChild
            target.click()
        },
        clickNext() {
            var target = document.getElementsByClassName('pagination')[0].lastElementChild.firstElementChild
            target.click()
        },
        getNotices(pageNum) {
            axios.get(`${this.SERVER_URL}/api/notice/list/${pageNum}`)
            .then(response => {
                this.notices = response.data.content
                this.totalPages = response.data.totalPages
                this.notices.forEach(element => element.createDate = element.createDate.substr(0,10))
            })
            .catch(err => console.log(err))

        },
        callNoticeDetail(noticeId) {
            this.$router.push(`/notice/${noticeId}`)
        },
        callNoticeForm() {
            this.$router.push('/notice/form')
        },

    },
    mounted() {
        this.getNotices(1)
    }


}
</script>


<style scoped>
.page-name {
    margin: -4rem auto 2rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}
.page {
    padding: 1.8rem 0rem;
}
.page-left, .page-right {
    display: inline-block;
    cursor: pointer;
}
.page-num {
    display: inline-block;
    font-size: 1rem;
    font-weight: bold;
    color: rgba(215, 159, 215, 1);
    padding: 0rem 1rem;
}
table {
    width: 80%;
}
.table-list {
    background-color: rgba(255, 255, 255, 0.9);
}
.table-list:hover {
    background-color: rgba(255, 255, 255, 0.6);
    cursor: pointer;
}
th {
    border: none !important;
    /* background-color: rgba(215, 159, 215, 1) !important; */
}
.head-tr {
    background-color: rgba(215, 159, 215, 1) !important;
}

</style>
