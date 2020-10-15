<template>
    <div class="padding-for-nav">
        <div>결제에 실패했습니다.</div>
        <button class="back-to-pay" @click="backToPay()">결제 페이지로 돌아가기</button>
    </div>
</template>

<script>
export default {
    name: 'PayFail',
    methods: {
        backToPay() {
            this.$router.push("/spoons")
        }
    },

}
</script>

<style scoped>
.back-to-pay {
    width: 17rem;
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
.back-to-pay:hover {
    background: rgba(215, 159, 215, 0.9);
}
</style>