<template>
    <!-- 질문 리스트에서 클릭 시 하단에 질문의 detail이 표시되는 component  -->
    <div class="bottom-piece">
        <viewer :initialValue="viewerText"/>
        <!-- QnaAnswer를 추가해 답변과 답변을 다는 공간을 보여줍니다. -->
        <QnaAnswer :itemId="item.qnaId"/>
    </div>
</template>

<script>
import QnaAnswer from './QnaAnswer'
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

import { Viewer } from '@toast-ui/vue-editor';

export default {
    name: 'QnaDetail',
    props: {
        item: Object
    },
    components: {
        QnaAnswer,
        viewer: Viewer,
    },
    data() {
        return {
            viewerText: this.item.qnaContent
        }
    }

}
</script>

<style scoped>
.bottom-piece {
    padding: 1rem 0rem 0rem;
}
</style>