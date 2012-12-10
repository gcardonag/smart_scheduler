var timeStats
 ,  pomodoro;

$(document).ready(function() {
    timeStats = new Highcharts.Chart({
        chart: {
            renderTo: 'timeStats',
            type: 'bar'
        },
        title: {
            text: 'Time Management Statistics'
        },
        subtitle: {
            text: 'Testing a variety of the bar graph. Please ignore.'
        },
        xAxis: {
            categories: ['Categorize themselves as disorganized', 'Have missed vital work deadlines', 'IT project failures caused by missed deadlines.'],
            title: {
                text: 'American Population'
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Percentage of population',
/*                align: 'low'*/
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            formatter: function() {
                return ''+
                    this.series.name +': '+ this.y +' %';
            }
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -100,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: '#FFFFFF',
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Percentage of the population',
            data: [43, 21, 75]
        }]
    });
});

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
