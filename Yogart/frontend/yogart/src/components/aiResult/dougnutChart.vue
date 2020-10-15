<template>
  <div>
      <canvas id="dougnutChart"></canvas>
  </div>
</template>

<script>
export default {
    name : 'dougnutChart',
    data() {
        return{
            dougnutdata : this.$cookies.get('dougnutdataStr').split(','),
        }
    },
    mounted() {
        this.createChart()
    },
    methods : {
        createChart() {
             var ctx2 = document.getElementById('dougnutChart').getContext('2d');
            var dougnutChart = new Chart(ctx2, {
                // The type of chart we want to create
                type: 'doughnut',

                // The data for our dataset
                data: {
                    labels: ['팔', '다리', '복근', '척추','전신', '에너지','릴렉스'],
                    datasets: [{
                        label: '부위 포함',
                        hoverBackgroundColor : '#F2E6FF',
                        data: this.dougnutdata,
                        backgroundColor: [
                         
                            // E5CCFF 229, 204, 255
                            // D9B3FF 217, 179, 255
                            // CC99FF 204, 153, 255
                            // BF80FF 204, 153, 255
                            // B266FF 178, 102, 255
                            // A54DFF 165, 77, 255
                            // 9933FF 153, 51, 255

                            'rgba(242, 157, 143,0.6)',
                            'rgba(242, 157, 143,0.6)',
                            'rgba(173, 94, 165,0.6)',
                            'rgba(215, 159, 215, 0.6)',
                            'rgba(168, 136, 242, 0.6)',
                            'rgba(148, 162, 242, 0.6)',
                            'rgba(148, 209, 242, 0.6)'
                                     
                            
                        ]
                    
                    }]      
                
                },

                options: {
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
                        text : '태그별 비율'
                    } ,
                    plugins: {
                    
                        labels: [
                            {
                            fontColor : 'black',
                            fontSize : 13,
                            render: 'label',
                            position: 'outside'
                            },
                            {
                            fontColor : 'black',
                            fontSize : 13,
                            render: 'percentage'
                            }
                        ]
                    }
                
                }

            });
        }
    }
}
</script>

<style scoped>
#dougnutChart {
    height : 400px !important;
    width : 700px !important;
    margin : 0;
   
}
</style>