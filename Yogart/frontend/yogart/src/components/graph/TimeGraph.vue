<template>
    <div class="timeGraph">
        <div class="graph-name">시간별 트레이닝 횟수</div>
        <div class="wrapper">
            <canvas id="myChart"></canvas>
        </div>
    </div>
</template>

<script>
    import LineChart from './LineChart.js'
    export default {
        name: 'TimeGraph',
        components: {
            LineChart
        },
        data() {
            return {
            }
        },
        props: {
            time: Object,
        },
        watch: {
            time() {
                this.fillData()
            }
        },
        methods: {
        fillData () {
            var ctx = document.getElementById('myChart').getContext("2d");

            var gradientStroke = ctx.createLinearGradient(600, 0, 0, 0);
            gradientStroke.addColorStop(0, '#80b6f4');
            gradientStroke.addColorStop(1, '#f49080');

            var gradientFill = ctx.createLinearGradient(600, 0, 0, 0);
            gradientFill.addColorStop(0, "rgba(128, 182, 244, 0.6)");
            gradientFill.addColorStop(1, "rgba(244, 144, 128, 0.6)");

            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: ["06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00", "01", "02", "03", "04", "05",],
                    datasets: [{
                        label: "시간 별 트레이닝 횟수",
                        borderColor: gradientStroke,
                        pointBorderColor: gradientStroke,
                        pointBackgroundColor: gradientStroke,
                        pointHoverBackgroundColor: gradientStroke,
                        pointHoverBorderColor: gradientStroke,
                        pointBorderWidth: 10,
                        pointHoverRadius: 10,
                        pointHoverBorderWidth: 1,
                        pointRadius: 1,
                        fill: true,
                        backgroundColor: gradientFill,
                        borderWidth: 4,
                        data: [this.time['21'], this.time['22'], this.time['23'], this.time['0'], this.time['1'], this.time['2'], this.time['3'], this.time['4'], this.time['5'], 
                        this.time['6'], this.time['7'], this.time['8'], this.time['9'], this.time['10'], this.time['11'], this.time['12'], this.time['13'], this.time['14'], 
                        this.time['15'], this.time['16'], this.time['17'], this.time['18'], this.time['19'], this.time['20']] // 데이터 (시간(06시~05시) 별 횟수) 들어가는 부분
                    }]
                },
                options: {
                    legend: {
                        position: "bottom"
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                fontColor: "rgba(0,0,0,0.5)",
                                fontStyle: "bold",
                                beginAtZero: true,
                                maxTicksLimit: 5,
                                padding: 20
                            },
                            gridLines: {
                                drawTicks: false,
                                display: false
                            }

                        }],
                        xAxes: [{
                            gridLines: {
                                zeroLineColor: "transparent"
                            },
                            ticks: {
                                padding: 20,
                                fontColor: "rgba(0,0,0,0.5)",
                                fontStyle: "bold"
                            }
                        }]
                    }
                }
            });
        },

        }

        
    }
</script>

<style scoped>
.wrapper {
    width: 80%;
    margin: auto;
}
.graph-name {
    margin: 2rem;
    font-size: 4vh;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.6);
}

</style>