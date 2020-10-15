
<template>
    <!-- 질문 상세 하단에 댓글 방식으로 달리는 답변과, 답변을 다는 기능을 가지는 component // 답변 작성은 관리자 혹은 질문글 작성자만 -->
    <div class="answer-box container">
        <div class="row" v-for="answer in answers" :key="answer.qnaReplyId">
            <pre class="col-10 text-left custom-break-word">{{ answer.qnaReplyContent }}</pre>
            <pre class="col-2 text-right custom-break-word">{{ answer.createDate }}</pre>  
        </div>
        <form class="row" v-if="isAdmin" @submit="onSubmit">
            <textarea class="answer-input col-10 col-xl-11" v-model="ReplyContent"></textarea>
            <button class="answer-input-btn col-2 col-xl-1" type="submit">작성</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'QnaAnswer',
    props: {
        itemId: Number,
    },
    data() {
        return {
            ReplyContent: '',
            answers: [],
            isAdmin: true,
            SERVER_URL: this.$store.state.SERVER_URL,
            answerData: this.ReplyContent,
        }
    },
    mounted() {
        const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
        }
        axios.get(this.SERVER_URL + `/api/qna/reply/list/${this.itemId}`, requestHeaders)
        .then(res => {
            this.answers = res.data.list
            this.isAdmin = res.data.admin
        })
        .catch(err => console.log(err))
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token')
                }
            }
            const replycontents = {
                ReplyContent: this.ReplyContent,
                qnaId: this.itemId
            }
            // 데이터 형식 및 URL 체크  
            if (this.answerData !== null && this.isAdmin === true) {
                axios.post(this.SERVER_URL + '/api/qna/reply/make', replycontents, requestHeaders)
                .then(res => {
                    this.answers = res.data
                    this.ReplyContent = null
                })
                .catch(err => console.error(err))
            }
        }
    },
}
</script>

<style scoped>
.answer-box {
    margin: 3rem 0rem 0rem;
    padding-top: 2rem;
    border-top: 1px solid rgba(215, 159, 215, 1);
}
.answer-input {
    height: 5rem;
    border: none;
    border-top: 1px solid rgba(215, 159, 215, 1);
    outline: none;
}
.answer-input-btn {
    outline: none;
    border: none;
    background-color: rgba(215, 159, 215, 0.6);
}
.answer-input-btn:hover {
    background-color: rgba(215, 159, 215, 0.5);
}
</style>