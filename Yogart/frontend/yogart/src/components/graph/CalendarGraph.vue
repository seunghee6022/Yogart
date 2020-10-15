<template>
    <div class="timeGraph">
        <div class="graph-name">일별 운동량</div>
        <div class="wrapper">
            <div id="no-data">AI 코칭을 이용하고 운동량을 확인해보세요</div>
            <GChart id="chart"
            :settings="{packages: ['calendar']}"
            type="Calendar"
            :data="chartData"
            :options="chartOptions"
            />
        </div>
    </div>
</template>

<script>
    import { GChart } from "vue-google-charts";
    export default {
        name: 'TimeGraph',
        components: {
            GChart
        },
        data() {
            return {
                chartData: [
                ['a','b'],// 데이터(날짜, 운동시간) 들어가는 부분
                // [new Date(2020, 8, 17), 20]
                ],
                chartOptions: {

                title: "",
                calendar: { cellSize: 11 },
                height: 150

                },
                isEmpty : true
            };
        },
        props: {
            calendar: Object,
        },
        watch: {
            calendar() {
                var calendarArr = Object.entries(this.calendar)
                if (calendarArr.length === 0) {
                    this.isEmpty = false
                    document.getElementById('no-data').style = "display: block;"
                    document.getElementById('chart').style = "display: none;"
                } else {
                    calendarArr.forEach(e => {
                    var date = e[0].split('-')
                    // console.log('날짜',date)
                    var run = e[1]*1
                    this.chartData.push([new Date(date[0]*1, (date[1]*1-1), date[2]*1), run])
                    document.getElementById('no-data').style = "display: none;"
                    document.getElementById('chart').style = "display: block;"
                    });
                }
                
                
            }
        },

    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.wrapper {
    width: 95%;
    margin: auto;
    padding-top: 1.5rem;
}
.graph-name {
    margin: 2rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}
#chart {
    display: none;
}
#no-data {
    display: block;
}
</style>