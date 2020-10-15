<template>
    <tr>
        <th scope="row">{{ time }}</th>
        <time-table-cell v-for="day in days" :clicked="clicked" :time="time" :key="day" :day="day" @click="cellClicked"></time-table-cell>
    </tr>
</template>

<script>
import axios from 'axios'
import TimeTableCell from './TimeTableCell.vue'

class dayTime {
    constructor(day, time) {
        this.day = day
        this.time = tiem
    }
}

export default {
    name: 'TimeTable',
    props: {
        time: Number,
        clicked: Array,
    },
    components: {
        TimeTableCell,
    },
    methods: {
        cellClicked(payload) {
            this.$emit('cellClicked', payload)
        }
    },
    data() {
        return {
            // 월, 화, 수, 목, 금, 토, 일
            days: [1, 2, 3, 4, 5, 6, 0],
        }
    },
}
</script>

<style scoped>

tr, td {
    border : 2px solid white;
}
th {
    background-color: rgba(143, 160, 242, 1);
    color : white;
    height : 5px;
    line-height: 25px;
    font-size : 15px;
}
td{
    background-color: rgba(255, 255, 255, 0.5);
}
</style>