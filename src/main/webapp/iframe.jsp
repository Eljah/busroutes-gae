<%--
  Created by IntelliJ IDEA.
  User: eljah32
  Date: 12/21/2015
  Time: 2:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Архив уровней Волги по данным водомерных постов</title>
    <meta charset="UTF-8">
    <!--Load the AJAX API-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        //Load the Visualization API and the ready-made Google table visualization
        google.load('visualization', '1', {'packages': ['corechart']});
        // Set a callback to run when the API is loaded.
        google.setOnLoadCallback(init);
        // Send the query to the data source.
        function init() {
            // Specify the data source URL.
            var query = new google.visualization.Query('visualize?count=${count}&km=${km}');
            // Send the query with a callback function.
            query.send(handleQueryResponse);
        }
        // Handle the query response.
        function handleQueryResponse(response) {
            if (response.isError()) {
                alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
                return;
            }

            var options = {
                title: 'Уровень воды, м',
                tooltip: {isHtml: true},
                //hAxis: {title: 'date',
                //    gridlines: {count: 15}},
                //vAxis: {title: 'level', minValue: 0, maxValue: 15},
                legend: 'none',
                width: 300, height: 160,
                curveType: "function",
                //trendlines: {
                //    0: {
                //        type: 'linear',
                //        color: 'green',
                //        lineWidth: 3,
                //        opacity: 0.3,
                //        showR2: true,
                //        visibleInLegend: true
                //    }
                //},
                series: {
                    0: {
                        0: {axis: 'days of measurement', targetAxisIndex: 0},
                        1: {axis: 'water level', targetAxisIndex: 1},
                        2: {}
                    },
                    1: {
                        0: {axis: 'days of measurement', targetAxisIndex: 0},
                        3: {axis: 'water level extrapolated', targetAxisIndex: 1},
                        4: {}
                    }
                }

                ,
                vAxes: {
                    0: {logScale: false},
                    1: {logScale: false, maxValue: 2}
                },
                hAxis: {
                    format: 'd/M/yy',
                    //gridlines: {count: 15}
                },
                //axes: {
                //    y: {
                //        'hours studied': {label: 'Hours Studied'},
                //        'final grade': {label: 'Final Exam Grade'}
                //    }
                //       }
            };

            // Draw the visualization.
            var data = response.getDataTable();
            //dirty hack
            //data.Ff[2].p.p={html: true};
            //data.Ff[2].p.role="tooltip";
            //data.Ff[2].p={html: true};
            //data.Ff[2].role="tooltip";
            data.setColumnProperties(2, {
                role: 'tooltip',
                html: true
            });

            data.setColumnProperties(4, {
                role: 'tooltip',
                html: true
            });

            var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
            chart.draw(data, options);

        }
    </script>
</head>
<body>
<div>
<a href="/" target="_blank"><i class="icon-cog icon-large"></i><small>Settings</small></a>
</div>
<!--Div that will hold the visualization-->
<div id="chart_div"></div>
</body>
</html>