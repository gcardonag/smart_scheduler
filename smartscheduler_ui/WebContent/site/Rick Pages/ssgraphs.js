var pomodoro
 ,  chart1;

    $(document).ready(function() {
        pomodoro = new Highcharts.Chart({
            chart: {
                renderTo: 'pomodoro',
                type: 'bar'
            },
            title: {
                text: 'Pomodoro Cycles'
            },
            xAxis: {
                categories: ['First cycle', 'Second cycle', 'Third cycle', 'Fourth cycle']
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Time (minutes)'
                }
            },
            legend: {
                backgroundColor: '#FFFFFF',
                reversed: true
            },
            credits: {
               enabled: false
            },
            tooltip: {
                formatter: function() {
                  if(this.series.name == 'Elapsed time so far')
                    return 'Total elapsed time after '+
                        this.x.toLowerCase() +': '+ this.y +' minutes';
                  else
                    return ''+
                        this.series.name +': '+ this.y +' minutes';
                }
            },
            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },
                series: [{
                name: 'Elapsed time so far',
                data: [30, 60, 90, 140]
            }, {
                name: 'Rest period',
                data: [5, 5, 5, 25]
            }, {
                name: 'Study period',
                data: [25, 25, 25, 25]
            }]
        });
    });
