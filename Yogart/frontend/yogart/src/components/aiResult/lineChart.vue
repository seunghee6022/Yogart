<template>
  <div>

      <canvas id="lineChart"></canvas>
  </div>
</template>

<script>
export default {
    name : 'lineChart',

    data() {
        return{
            lineLabels : this.$cookies.get('lineLabelStr').split(','),
            linedata : this.$cookies.get('resultPoseTimes').split('.'),
        }
    },
    mounted() {
        this.createChart()
    },
    methods : {
        createChart() {
            var ctx = document.getElementById('lineChart').getContext('2d');
            var gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
            gradientStroke.addColorStop(0, "#80b6f4");
            gradientStroke.addColorStop(1, "#f49080");
            var gradientFill = ctx.createLinearGradient(500, 0, 100, 0);
            gradientFill.addColorStop(0, "rgba(128, 182, 244, 0.6)");
            gradientFill.addColorStop(1, "rgba(244, 144, 128, 0.6)");
            var lineChart = new Chart(ctx, {
            //  multiTooltipTemplate: "<%= datasetLabel %> - <%= value %>",
                // The type of chart we want to create
                type: 'line',
                
                // The data for our dataset
                data: {
                    labels: this.lineLabels,
                    datasets: [{
                        
                        label: '소요 시간(초)',
                        data: this.linedata,
                        borderColor: gradientStroke,
                        pointBorderColor: gradientStroke,
                        pointBackgroundColor: gradientStroke,
                        pointHoverBackgroundColor: gradientStroke,
                        pointHoverBorderColor: gradientStroke,
                        pointBorderWidth: 10,
                        pointHoverRadius: 10,
                        pointHoverBorderWidth: 1,
                        pointRadius: 3,
                        fill: true,
                        backgroundColor: gradientFill,
                        borderWidth: 4,
                    }]
                },

                // Configuration options go here
                options: {
                scales: {
                        xAxes: [{
                        scaleLabel: {
                            fontWeight : 'bold',
                            fontSize : 15,
                            display: true,
                            labelString: '요가 동작'
                        }
                        }],
                        yAxes: [{
                        scaleLabel: {
                            fontWeight : 'bold',
                            fontSize : 15,
                            display: true,
                            labelString: '소요시간(초)'
                        }
                        }]
                    } ,    
                    legend: {
                        labels: {
                            // This more specific font property overrides the global property
                            fontColor: 'black',
                            fontSize : 15,
                        },

                    },
                    title : {
                        display : true,
                        fontSize : 20,
                        text : '동작 별 소요시간'
                    } ,
                    scaleLabel : {
                        display : true,
                    },
                    plugins: {
                        datalabels: {
                            color: 'blue',
                            labels: {
                            
                                title: {
                                    font: {
                                        weight: 'bold'
                                    }
                                },
                                value: {
                                    color: 'green'
                                }
                            }
                        }
                    }             
                }

            });
        }
    }
}
</script>

<style scoped>
#lineChart {
    height : 400px !important;
    width : 700px !important;
}
</style>