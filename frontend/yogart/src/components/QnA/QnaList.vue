<template>
    <div class="noticeList">
        <div class="big-piece" v-for="item in items" :key="item.qnaId">
            <QnaListItem :item="item"/>
        </div>
    </div>
</template>

<script>
import QnaListItem from './QnaListItem.vue'

export default {
    name: 'QnaList',
    components: {
        QnaListItem,
    },
    props: {
        items: Array,
    }
}
</script>

<style scoped>
.noticeList{
    width: 75vw;
    height : 100%;
    margin: auto;
}
</style>
