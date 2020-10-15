<template>
    <div class="padding-for-nav">
        <div class="page-name">{{ notice.noticeTitle }}</div>
        <div class="notice-date-content">
            <div class="notice-date">등록일 : {{ notice.createDate }}</div>
            <div class="notice-content">
                <viewer v-if="notice.noticeContent !== null" :initialValue="notice.noticeContent"/>
            </div>
        </div>

        <div class="my-5">
            <button class="list-btn" @click="toNoticeList()">목록으로</button>
        </div>
    </div>
     
</template>

<script>
// @ is an alias to /src
import axios from 'axios'
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/vue-editor';

export default {
    name: 'NoticeDetailView',
    components: {
        viewer: Viewer
    },
    data() {
      return {
        notice: {
            noticeTitle: null,
            noticeContent: null,
            noticeId: null,
            createDate: null,
        },
        SERVER_URL: this.$store.state.SERVER_URL
      }
    },
    created() {
        window.scrollTo(0,0);
    },
    methods: {
        getNotice() {
            var noticeId = document.location.href.split("notice/")[1]
            axios.get(`${this.SERVER_URL}/api/notice/${noticeId}`)
            .then(response => {
            this.notice.noticeTitle = response.data.noticeTitle
            this.notice.noticeContent = response.data.noticeContent
            this.notice.noticeId = response.data.noticeId
            this.notice.createDate = response.data.createDate
            this.notice.createDate = this.notice.createDate.substr(0,10)
            })
        },
        toNoticeList() {
            this.$router.push(`/notice`)
        }
    },
    created() {
        this.getNotice()
    },

}
</script>

<style scoped>
.page-name {
    width: 70%;
    margin: -4rem auto 0rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
    border-top: 2px solid rgba(215, 159, 215, 1);
    border-bottom: 2px solid rgba(215, 159, 215, 1);
    padding: 0.5rem;
    background-color: rgba(255, 255, 255, 0.5);
}
.notice-date {
    margin: 0rem 0.5rem;
    padding-top: 1.5rem;
    text-align: right;
}
.notice-content {
    margin: 0rem auto 3rem;
    text-align: left;
    padding: 0rem 0.5rem;
    min-height: 20rem;
}
.notice-date-content {
    width: 70%;
    margin: 0rem auto;
    border-bottom: 2px solid rgba(215, 159, 215, 1);
    background-color: rgba(255, 255, 255, 0.8);
}

.list-btn {
    width: 7rem;
    height: 3rem;
    margin-bottom: 1rem;
    color: white;
    font-size: 17px;
    border-radius: 3rem;
    border-width: 0px 0px 1.2px;
    border-color: rgba(255, 255 255, 0.2);
    background: rgba(215, 159, 215, 1);
    outline:none;
    cursor: pointer;
}
.list-btn:hover {
    background: rgba(215, 159, 215, 0.9);
}

</style>