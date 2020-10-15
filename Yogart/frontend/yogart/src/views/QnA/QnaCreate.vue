<template>
    <div class="padding-for-nav">
        <div class="qna-page-name">질문 작성</div>
        <form @submit="onSubmit">
            <input class="title" type="text" v-model="questionTitle" placeholder="제목을 입력해 주세요.">
            <editor class="editor"
                :initialValue="editorText"
                ref="toastuiEditor"
                height="500px"
                initialEditType="wysiwyg"
                previewStyle="vertical"
            />
            <button class="write-btn" type="submit">작성완료</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios'
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

import { Editor } from '@toast-ui/vue-editor';

export default {
    name: 'QnaCreate',
    components: {
        editor: Editor,
    },
    created() {
        window.scrollTo(0,0);
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()
            this.getHtml()
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token')
                }
            }
            const questionData = {
                qnaTitle: this.questionTitle,
                qnaContent: this.questionContent,
            }
            if (this.questionContent !== null && this.questionTitle !== null) {
                this.isEmpty = false
            }
            // 데이터 형식 및 URL 체크
            if (!this.isEmpty) {
                axios.post(this.SERVER_URL + '/api/qna/make', questionData, requestHeaders)
                .then(res => {
                    history.back()
                })
                .catch(err => console.error(err))
            } else {
                return
            }
        },
        getHtml() {
            let html = this.$refs.toastuiEditor.invoke('getHtml')
            this.questionContent = html
        },
    },
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL,
            questionTitle: null,
            editorText: '',
            questionContent: null,
            isEmpty: true,
        }
    },
}
</script>

<style scoped>
.qna-page-name {
    margin: -8rem auto 2rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}
.title {
    outline: none;
    width: 80%;
    height: 3rem;
    padding: 0rem 1rem;
    border: 1px solid rgba(0, 0, 0, 0.2);
}
.editor {
    width: 80%;
    margin: 0.5rem auto;
}
.write-btn {
    width: 15rem;
    height: 3rem;
    margin-top: 2rem;
    margin-bottom: 3rem;
    color: white;
    font-size: 17px;
    border-radius: 3rem;
    border-width: 0px 0px 1.2px;
    border-color: rgba(255, 255 255, 0.2);
    background: rgba(215, 159, 215, 1);
    outline:none;
    cursor: pointer;
}
.write-btn:hover {
    background: rgba(215, 159, 215, 0.9);
}
</style>