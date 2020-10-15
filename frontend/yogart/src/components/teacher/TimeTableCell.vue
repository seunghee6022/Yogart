<template>
    <td :class="{ select: isClicked }" @click="onClick()"></td>
</template>

<script>
export default {
    name: 'TimeTableCell',
    props: {
        day: Number,
        time: Number,
        clicked: Array,
    },
    mounted() {
        this.isClicked = this.clicked.some(dayTime => {
            return dayTime.day === this.day && dayTime.time === this.time
        })
    },
    data() {
        return {
            isClicked: null,
        }
    },
    methods: {
        onClick() {
            this.isClicked = !this.isClicked
            this.$emit('click', {isClicked: this.isClicked, day: this.day, time: this.time})
        }
    }
}
</script>

<style scoped>
.select {
    background : rgba(143, 160, 242, 0.5);
}
</style>