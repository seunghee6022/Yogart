<template>
    <div class="padding-for-nav">
        <div class="page-name">스푼 구매</div>
        <div class="spoon-back">
            <div class="d-inline-block border-none">당신의 마음에 요가트 </div>
            <div class="form-group d-inline-block border-none">
                <select v-model="selected" class="form-control mx-3" id="sel1">
                    <option :value="0">10</option>
                    <option :value="1">50</option>
                    <option :value="2">110</option>
                    <option :value="3">220</option>
                </select>
            </div>
            <div class="d-inline-block p-4"> 스푼 충전하세요!</div>

            <div class="my-4">결제금액 : <strong>{{ price[selected] }}원</strong></div>
            <button class="spoon-btn" @click="onClick">구매하기</button>
        </div>
        
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'SpoonPurchase',
    data() {
        return {
            selected: 0,
            price: [1000, 5000, 10000, 20000],
            spoons: [10, 50, 110, 220],
            SERVER_URL: this.$store.state.SERVER_URL,
        }
    },
    methods: {
        onClick(e) {
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token')
                }
            }
            const purchaseData = {
                quantity: String(this.spoons[this.selected]),
                price: String(this.price[this.selected]),
            }
            axios.post(this.SERVER_URL + '/api/users/pay', purchaseData, requestHeaders)
            .then(res => {
                // 카카오페이 결제창 오픈
                this.$cookies.set(' ', res.data.tid)
                window.open(res.data.next_redirect_pc_url)
            })
            .catch(err => console.error(err))
        }
    },
    created(d) {
        window.scrollTo(0,0);
    },

}
</script>

<style scoped>
.page-name {
    margin: -4rem auto 2rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}
.spoon-back {
    background-color: rgba(255, 255, 255, 0.6);
    width: 50%;
    margin: 0rem auto;
    padding: 2rem;
}
.spoon-btn {
    width: 7rem;
    height: 3rem;
    margin-bottom: 0.5rem;
    color: white;
    font-size: 17px;
    border-radius: 3rem;
    border-width: 0px 0px 1.2px;
    border-color: rgba(255, 255 255, 0.2);
    background: rgba(215, 159, 215, 1);
    outline:none;
    cursor: pointer;
}
.spoon-btn:hover {
    background: rgba(215, 159, 215, 0.9);
}
</style>