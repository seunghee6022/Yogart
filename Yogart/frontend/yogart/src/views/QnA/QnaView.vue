<template>
    <div class="padding-for-nav h-100">
        <div class="page-name">Q & A</div>
        <div class="btn-wrap">
            <button class="write-btn" @click="clickWriteBtn()">글 작성</button>
        </div>
        <QnaList :items="items"></QnaList>
        <div class="page">
            <div class="page-left" @click="clickPrev()"><i class="fas fa-chevron-left"></i></div>
            <div class="page-num">{{page}}</div>
            <div class="page-right" @click="clickNext()"><i class="fas fa-chevron-right"></i></div>
        </div>
        
        
        <paginate v-model="page" 
        :page-count="totalPages" 
        :page-range="0" 
        :margin-pages="0" 
        :click-handler="getQna" 
        :prev-text="'이전 페이지'" 
        :next-text="'다음 페이지'" 
        :container-class="'pagination'" 
        :page-class="'page-item'"
        style="display: none;"> </paginate>
    </div> 

</template>

<script>
import QnaList from '@/components/qna/QnaList.vue'
import axios from 'axios'
import Paginate from 'vuejs-paginate'
import Vue from 'vue'
Vue.component('paginate', Paginate)

export default {
    name: 'QnaView',
    components:{
        QnaList,
    },
    data() {
        return {
            // items: null,
            items: [],
            page: 1,
            totalPages: 10,
            SERVER_URL: this.$store.state.SERVER_URL
        }
    },
    methods: {
        clickWriteBtn() {
            this.$router.push("/qna/question")
        },
        clickPrev() {
            var target = document.getElementsByClassName('pagination')[0].firstElementChild.firstElementChild
            target.click()
        },
        clickNext() {
            var target = document.getElementsByClassName('pagination')[0].lastElementChild.firstElementChild
            target.click()
        },
        getQna(pageNum) {
            axios.get(this.SERVER_URL + `/api/qna/list/${pageNum}`)
            .then(res => {
                this.items = res.data.content
                this.totalPages = res.data.totalPages
                this.items.forEach(element => element.createDate = element.createDate.substr(0,10))
            })
            .catch(err => console.log(err))
        }

    },
    mounted() {
        this.getQna(1)
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

.write-btn {
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
.write-btn:hover {
    background: rgba(215, 159, 215, 0.9);
}
.btn-wrap {
    width: 73vw;
    margin: auto;
    text-align: right;
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
</style>