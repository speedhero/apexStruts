
function initPageAss(jsonActArr,jsonNameArr,jsonTrgArr,pageArr) {
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        timeline:{
            data:pageArr,
            label : {
                formatter : function(s) { return "第"+s+"页"; }
            },
            autoPlay : true,
            playInterval : 2000,
            tooltip:{formatter : function(s) {return "第"+s.value+"页"; }}
        },
        options:[
            {
                title : {
                    'text':'开门红储蓄存款日均'
                },
                tooltip : {'trigger':'axis'},
             /*   tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    // formatter:'{c}%'　　　　//这是关键，在需要的地方加上就行了
                    formatter: function(params) {
                        params=params[0];
                        return '任务值:'+params.data.ftask + '</br>' + '实际值：' + params.data.factual + '</br>' + '应达值：' + params.data.ftarget + '<br/>'  + '完成率：' + params.value+'%' ;
                    }
                },*/
                legend : {
                    /*   orient: 'vertical',
                        left:'center',
                        bottom:'bottom',*/
                    'data':['任务值','已完成']
                },
                calculable : true,
                grid : {'y2':80},
                xAxis : [{
                    'type':'category',
                    //'axisLabel':{'interval':0},
                    'data':jsonNameArr[0]
                }],
                yAxis : [
                    {
                        'type':'value',
                        'name':'存款日均（万元）',
                        'max':120000
                    }
                ],
                series : [
                    {
                        'name':'任务值','type':'bar',
                        'data': jsonTrgArr[0]
                    },
                    {
                        'name':'已完成','type':'bar',
                        'data': jsonActArr[0]
                    } ]
            }
        ]
    };

    for(i=1;i<pageArr.length;i++){//去除第一组
        option['options'].push( {
            series : [
                {'data': jsonTrgArr[i]},
                {'data': jsonActArr[i]}
            ],
            xAxis:[{'data':jsonNameArr[i]}]
        })
    }




    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    console.log(option);
}

function initSlider(jsonNameArr,percentArr) {
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
           // formatter:'{c}%'　　　　//这是关键，在需要的地方加上就行了
               formatter: function(params) {
                   params=params[0];
                   return '任务值:'+params.data.ftask + '</br>' + '实际值：' + params.data.factual + '</br>' + '应达值：' + params.data.ftarget + '<br/>'  + '完成率：' + params.value+'%' ;
               }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
            borderWidth: 0,
            y: 80,
            y2: 60
        },
        xAxis : [
            {
                type : 'category',
                data : jsonNameArr,
                axisTick: {
                    alignWithLabel: true
                },
                show: false
            }
        ],
        yAxis : [
            {
                type : 'value',
                show: false
            }
        ],
        series : [
            {
                name:'完成率',
                type:'bar',
                barWidth :'50%',//柱图宽度
                barGap: '40%',//间隔
                // barWidth: '40%',
                itemStyle: {
                    normal: {
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B',
                                '#C1232B','#B5C334','#fc877d','#e81c50','#27727B'
                            ];
                            return colorList[params.dataIndex]
                        },
                        label: {
                            show: true,
                            position: 'bottom',
                            formatter: '{b}\n{c}%'　　　　//这是关键，在需要的地方加上就行了
                        }
                    }
                },
                data:percentArr,
                markLine : {
                    symbol:"none",

                    /*symbol:"none",               //去掉警戒线最后面的箭头
                    label:{
                        position:"end"   ,       //将警示值放在哪个位置，三个值“start”,"middle","end"  开始  中点 结束


                    },*/
                    data : [{

                        silent:false,             //鼠标悬停事件  true没有，false有
                        lineStyle:{               //警戒线的样式  ，虚实  颜色
                            type:"solid",
                            color:"#3398DB",
                        },
                        label:{
                            position:'end',
                            formatter:"警戒线(60%)"
                        },
                        yAxis:60         // 警戒线的标注值，可以有多个yAxis,多条警示线   或者采用   {type : 'average', name: '平均值'}，type值有  max  min  average，分为最大，最小，平均值

                    },
                        {

                            silent:false,             //鼠标悬停事件  true没有，false有
                            lineStyle:{               //警戒线的样式  ，虚实  颜色
                                type:"solid",
                                color:"#FA3934",

                            },
                            label:{
                                position:'end',
                                formatter:"完成(100%)",
                                fontSize:'12'
                            },
                            yAxis:100          // 警戒线的标注值，可以有多个yAxis,多条警示线   或者采用   {type : 'average', name: '平均值'}，type值有  max  min  average，分为最大，最小，平均值

                        },
                        {

                            silent:false,             //鼠标悬停事件  true没有，false有
                            lineStyle:{               //警戒线的样式  ，虚实  颜色
                                type:"solid",
                                color:"#2efa52",

                            },
                            label:{
                                position:'end',
                                formatter:"优秀(120%)",
                                fontSize:'12'
                            },
                            yAxis:110          // 警戒线的标注值，可以有多个yAxis,多条警示线   或者采用   {type : 'average', name: '平均值'}，type值有  max  min  average，分为最大，最小，平均值

                        }
                    ]
                }
            }
        ],
        dataZoom: {
            show: true, // 为true 滚动条出现
            realtime: true,
            type:'slider', // 有type这个属性，滚动条在最下面，也可以不行，写y：36，这表示距离顶端36px，一般就是在图上面。
            height: 20, // 表示滚动条的高度，也就是粗细
            start: 20, // 表示默认展示20%～80%这一段。
            end: 50,
            top:"5%"
        }
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}



function renderCatlogDate() {
    personCountOption = {
        backgroundColor:'#fff',
        tooltip: {
            trigger: 'item',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            },
            padding: 10,
            formatter: function(params) {
                return params.name + '</br>' + '总&emsp;数：' + params.value + '</br>' + '待处理：' + params.data.cost + '<br/>'  + '已授权：' + params.data.early + '<br/>' + '已拒绝：' + params.data.index + '<br/>' + '回收授权：' + params.data.cur
            }
        },
        xAxis   : [
            {
                type : 'category',
                axisLine:{
                    show:false,
                    lineStyle:{
                        color:'#DDDDDD',
                        width:0
                    }
                },
                splitLine:{
                    show:false
                },
                axisTick:{
                    show:false
                },
                axisLabel:{
                    show:false,
                    textStyle:{
                        color:'#999'
                    }
                },
                data: function () {
                    var list = [];
                    for(var i = 1;i<=12;i++){
                        list.push(i+'月份');
                    }
                    return list;
                }()
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLine:{
                    show:false,
                    lineStyle:{
                        color:'#DDDDDD',
                        width:1
                    }
                },
                splitLine:{
                    show:false
                },
                axisLabel:{
                    show:false,
                    textStyle:{
                        color:'#999'
                    }
                }
            }
        ],
        grid: {
            x: 45,
            x2: 15,
            y: 25,
            y2: 15,
            backgroundColor:'#fff',
            borderWidth: 0
        },
        series: [{
            name: '指标',
            type: 'bar',
            // barWidth: 20,
            itemStyle:{
                normal:{
                    barBorderRadius: 5,
                    color: function(params) {
                        var colorList = [     //柱子颜色
                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                            '#D7504B','#C6E579','#F4E001'
                        ];
                        return colorList[params.dataIndex]
                    }
                },
                emphasis:{
                    barBorderRadius: 5
                }
            },
            data: [{
                value: 20,
                cost: 4,
                early: 6,
                index: 2,
                cur:1
            }, {
                value: 15,
                cost: 4,
                early: 6,
                index: 2,
                cur:1
            }, {
                value: 18,
                cost: 4,
                early: 6,
                index: 2,
                cur:1
            }, {
                value: 10,
                cost: 4,
                early: 6,
                index: 2,
                cur:1
            },{
                value: 13,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 13,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 14,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 13,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 16,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 25,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 21,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            },{
                value: 14,
                cost: 4,
                early: 6,
                index: 2,
                cur:0
            }
            ]
        }]
    };
}

function renderGroupCharts(sonNameArr,percentArr,actualtArr,groupArr) {

    var colorArr =["#DDA0DD","#87CEFA","#CD5C5C","#DDA0DF","#87CEEA","#CD5CFC"];
    var seriesOpt=[];

    for(i=0;i<groupArr.length;i++){
        seriesOpt.push(   {
            name:groupArr[i],
            type:'bar',
            data:actualtArr[i],
            barWidth : '50%',

        });
        seriesOpt.push(    {
            name:groupArr[i],
            type:'line',
            yAxisIndex: 1,    //这里要设置哪个y轴，默认是最左边的是0，然后1，2顺序来。
            data:percentArr[i],
            symbolSize:10,
            itemStyle:{
                normal:{
                    color:colorArr[i%6]
                }

            }
        });
    }



    var myChart = echarts.init(document.getElementById('main'));
   var option = {
        title: {
            left: 'left',
            text: '完成率(实际值/应达值)',
            show:false
        },
        tooltip: {
            trigger: 'axis',
            formatter:'{a}:{c}',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        grid: {
            show:false,
            top:'30',
            bottom:'60',
            right:'60',
            left:'60',
            containLabel:true
        },
        legend: {
            show:true,
            selectedMode:'single',    //设置显示单一图例的图形，点击可切换
            bottom:10,
            left:50,
            textStyle:{
                color:'#666',
                fontSize:12
            },
            itemGap:20,
            data:groupArr,
            inactiveColor:'#ccc'
        },
        xAxis: [
            {
                type: 'category',
                data: sonNameArr,
                axisPointer: {
                    type: 'shadow'

                },

                    axisTick: {
                        show:false,//刻度轴
                            interval: 0
                    },

            }
            ],

     //设置两个y轴，左边显示数量，右边显示概率
        yAxis: [
        {
            type: 'value',
            name: '数量/值',
            show:true,
            interval: 50,
            splitLine:{
                show:false
            },
            scale:true,
            axisLabel:{
                formatter:(value) => {
                    if(value >= 10000){
                        value = (value / 10000) + 'W';
                    }
                    if(value >= 1000){
                        value = (value / 1000) + 'K';
                    }
                    return value;
                }
            },
            show: false
        },
        {
            type: 'value',
            name: '完成率(实际值/应达值)',
            min: 0,
            max: 100,
            interval: 10,
            axisLabel: {
                formatter: '{value} %'
            }
        }
    ],

 //每个设备分数量、概率2个指标，只要让他们的name一致，即可通过，legeng进行统一的切换

        series:seriesOpt
       /* [
        {
            name:'设备一',
            type:'bar',
            data:[900,800,700,680,650,640,600,570,680,650,640,600,570,
                450,400,380,300],
            barWidth : '50%',

        },
        {
            name:'设备一',
            type:'line',
            yAxisIndex: 1,    //这里要设置哪个y轴，默认是最左边的是0，然后1，2顺序来。
            data:[75,65,85,66,45,55,56,42,78,69,70,36,42,50,65,75,80],
            symbolSize:10,
            itemStyle:{
                normal:{
                    color:"#DDA0DD"
                }

            }
        },
        {
            name:'设备二',
            type:'bar',
            data:[700,680,650,640,600,570,680,650,640,600,570,
                450,400,380,300,900,800],
            barWidth : '50%',
        },
        {
            name:'设备二',
            type:'line',
            yAxisIndex: 1,
            data:[75,65,85,66,45,55,56,42,78,69,70,36,42,50,65,75,80],
            symbolSize:10,
            itemStyle:{
                normal:{
                    color:"#87CEFA"
                }

            }
        },
        {
            name:'设备三',
            type:'bar',
            data:[600,570,680,650,640,600,570,
                450,400,380,300,900,800,700,680,650,640,],

            barWidth : '50%',
        },
        {
            name:'设备三',
            type:'line',
            yAxisIndex: 1,
            data:[75,65,85,66,45,55,56,42,78,69,70,36,42,50,65,75,80],
            symbolSize:10,
            itemStyle:{
                normal:{
                    color:"#CD5C5C"
                }

            }
        }
    ]*/
       ,dataZoom: {
               show: true, // 为true 滚动条出现
               realtime: false,
               type:'slider', // 有type这个属性，滚动条在最下面，也可以不行，写y：36，这表示距离顶端36px，一般就是在图上面。
               height: 20, // 表示滚动条的高度，也就是粗细
               start: 20, // 表示默认展示20%～80%这一段。
               end: 60,
               top:"5%"
           }
        };
    myChart.setOption(option);


}


function renderZline(groupArr,jsonNameArr,percentArr,pageArr) {//折线图
    // 获取到这个DOM节点，然后初始化
    var myChart = echarts.init(document.getElementById("main"));
    var options = {
        // 标题
        title: {
            text: '贷款营销任务表',
            subtext: '数据来源：邳州农商行'
        },
        tooltip: {
            trigger: 'axis'
        },
        //图例名
        legend: {
            data:groupArr   //['任务户数','已达户数','任务时点','已达时点','任务日均','已达日均']
        },
        grid: {
            left: '3%',   //图表距边框的距离
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        //工具框，可以选择
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        //x轴信息样式
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: jsonNameArr,  //['支行1','支行2','支行3','支行4','支行5','支行6','支行7','支行8','支行9','支行10','支行11','支行12','支行13','支行14'],
            //坐标轴颜色
            axisLine:{
                lineStyle:{
                    color:'black'
                }
            },
            //x轴文字旋转
            axisLabel:{
                rotate:30,
                interval:0
            },
        },

        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} 人'
                }
            }
        ],
        series: [
            //虚线
            {
                name:'任务户数',
                type:'line',
                symbolSize:4,   //拐点圆的大小
                color:['red'],  //折线条的颜色
                data:[1000, 300, 500, 800, 300, 600,500,800, 300, 500, 800, 300, 600,500],
                smooth:false,   //关键点，为true是不支持虚线的，实线就用true
                itemStyle:{
                    normal:{
                        lineStyle:{
                            width:2,
                            type:'dotted'  //'dotted'虚线 'solid'实线
                        }
                    }
                }
            },
            //实线
            {
                name:'已达户数',
                type:'line',
                symbol:'circle',
                symbolSize:4,
                itemStyle:{
                    normal:{
                        color:'red',
                        borderColor:'red'  //拐点边框颜色
                    }
                },
                data:[220, 182, 191, 234, 290, 330, 310,220, 182, 191, 234, 290, 330, 310]
            },
            {
                name:'任务时点',
                type:'line',
//                stack: '总量',
                symbolSize:4,
                color:['orange'],
                smooth:false,   //关键点，为true是不支持虚线的，实线就用true
                itemStyle:{
                    normal:{
                        lineStyle:{
                            width:2,
                            type:'solid'  //'dotted'虚线 'solid'实线
                        }
                    }
                },
                data:[500, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]
            },
            {
                name:'已达时点',
                type:'line',
                symbolSize:4,
                color:['orange'],
                itemStyle:{
                    normal:{
                        lineStyle:{
                            width:2,
                            type:'dotted'  //'dotted'虚线 'solid'实线
                        }
                    }
                },
                data:[300, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]
            },
            {
                name:'任务日均',
                type:'line',
                color:['green'],
                symbol:'circle',
                symbolSize:4,
                data:[310, 352, 280, 334, 373, 310, 340,300, 350, 280, 350, 340, 370, 310],
                itemStyle:{
                    normal:{
                        color:'green',
                        borderColor:'green'
                    }
                }
            },
            {
                name:'已达日均',
                type:'line',
                color:['green'],
                symbol:'circle',
                symbolSize:4,
                data:[300, 342, 380, 334, 373, 310, 390,300, 320, 380, 450, 370, 33, 320],
                itemStyle:{
                    normal:{
                        lineStyle:{
                            width:2,
                            type:'dotted'  //'dotted'虚线 'solid'实线
                        }
                    },

                }
            }
        ]
    };

    var option={
        timeline:{
            data:pageArr,
            label : {
                formatter : function(s) { return "第"+s+"页"; }
            },
            autoPlay : true,
            playInterval : 2000,
            tooltip:{formatter : function(s) {return "第"+s.value+"页"; }}
        },
        options:[options]

    }
/*

    for(i=1;i<pageArr.length;i++){//去除第一组
        option['options'].push( {
            series : [
                {'data': jsonTrgArr[i]},
                {'data': jsonActArr[i]}
            ],
            xAxis:[{'data':jsonNameArr[i]}]
        })
    }
*/

    var series=[];
    for(i=0;i<percentArr.length;i++){
        series.push(percentArr[i]);
    }

    option['options'].push( {
        series :series  ,
     /*       [
            {'data':  [1000, 300, 500, 800, 300, 600,500,800, 300, 500, 800, 300, 600,500]},
            {'data': [220, 182, 191, 234, 290, 330, 310,220, 182, 191, 234, 290, 330, 310]},
            {'data': [300, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]},
            {'data': [310, 352, 280, 334, 373, 310, 340,300, 350, 280, 350, 340, 370, 310]},
            {'data': [500, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]},
            {'data': [500, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]}
        ],*/
        xAxis:[{'data':jsonNameArr}]
    });

    myChart.setOption(option);

}

/**
 * 画占比图
 * @param {String} container 容器
 * @param {JSON} yData Y轴数据
 * @param {JSON} ratio 占比数据
 * @param {JSON} surplus 剩余数据
 */
function drawPercentage(yData,ratio,surplus){
    var myChart = echarts.init(document.getElementById('main'));
    var option = {
        grid: {
            left: '8%',
            right: '8%',
            bottom: '4%',
            top: 10,
            containLabel: true
        },
        xAxis: {
            show: false
        },
        yAxis: {
            type: 'category',
            inverse: true,
            //城市名称
            data: yData,
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            axisLabel: {
                show: true,
                color: function (value, index) {
                    if(index == 0) {
                        return 'red';
                    }
                    if(index == 1) {
                        return '#ff8447';
                    }
                    if(index == 2) {
                        return '#ffcc00';
                    }
                    return 'rgb(18,205,12)'
                },
                fontSize: 15,
                fontWeight: 'bold'
            },
        },
        series: [
            {
                type: 'bar',
                stack: 'chart',
                z: 3,
                barWidth: '20',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(1,
                            0, 0, 1, [{
                                offset: 0,
                                color: '#2A6BCD'
                            }, {
                                offset: 1,
                                color: '#34F6F8'
                            }])
                    }
                },
                label: {
                    normal: {
                        position: 'right',
                        show: true,
                        color: 'white',
                        formatter: '{c}%'
                    }
                },
                data: ratio,
                markLine : {
                    symbol:"none",
                    data : [
                        {

                            silent:false,             //鼠标悬停事件  true没有，false有
                            lineStyle:{               //警戒线的样式  ，虚实  颜色
                                type:"dotted",//dotted solid dashed
                                color:"#fa9341",
                                width:3//设置线条粗细

                            },
                            label:{
                                position:'end',
                                formatter:"完成(100%)",
                                fontSize:'12'
                            },
                            xAxis:100          // 警戒线的标注值，可以有多个yAxis,多条警示线   或者采用   {type : 'average', name: '平均值'}，type值有  max  min  average，分为最大，最小，平均值

                        }
                    ]
                }
            },
            {
                type: 'bar',
                stack: 'chart',
                barWidth: '20',
                itemStyle: {
                    normal: {
                        color: '#0D2253'
                    }
                },
                data: surplus
            }]
    }
    myChart.setOption(option);
}
