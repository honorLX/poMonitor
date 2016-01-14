      var jsonobj, status, message;
        $("button")
                .click(
                        function() {
                            $.ajax({
                                url : "./servlet/HotWordsServlet",
                                type : "POST",
                                data:"start_time=document.getElementById(date1)&end_time=document.getElementById(date2)",
                                contentType : "json",
                                dataType : "json",
                                success : function(data) {
                                    alert('success');
                                    status = data.status;
                                    message = data.message;
                                    jsonobj = data.results;
                                    console.log(jsonobj);
                                    //var option.series=jsonobj.series;

                                },
                                error : function() {
                                    alert('没有进入');
                                }
                            });

                            require.config({
                                paths : {
                                    echarts : './lib/echarts-2.2.7/build/dist'
                                }
                            });
                            require(
                                    //少一个
                                    [ 'echarts', 'echarts/chart/force' // 使用柱状图就加载bar模块，按需加载
                                    ],

                                    function(ec) {//少一个
                                        // 基于准备好的dom，初始化echarts图表
                                        var myChart = ec.init(document
                                                .getElementById('main'));

                                        var option = {//少一个
                                            title : {
                                                text : '热词统计',
                                                x : 'right',
                                                y : 'bottom'
                                            },
                                            tooltip : {
                                                trigger : 'item',
                                                formatter : '{a} : {b}'
                                            },
                                            toolbox : {
                                                show : true,
                                                feature : {
                                                    restore : {
                                                        show : true
                                                    },
                                                    magicType : {
                                                        show : true,
                                                        type : [ 'force',
                                                                'chord' ]
                                                    },
                                                    saveAsImage : {
                                                        show : true
                                                    }
                                                }
                                            },
                                            legend : {
                                                x : 'left',
                                                data : [ '主要', '次要', '一般' ]
                                            },
                                            series : [ {
                                                type : 'force',
                                                name : "热词统计",
                                                ribbonType : false,
                                                categories : [ {
                                                    name : '主要'
                                                }, {
                                                    name : '次要'
                                                }, {
                                                    name : '一般'
                                                } ],
                                                itemStyle : {
                                                    normal : {
                                                        label : {
                                                            show : true,
                                                            textStyle : {
                                                                color : '#333'
                                                            }
                                                        },
                                                        nodeStyle : {
                                                            brushType : 'both',
                                                            borderColor : 'rgba(255,215,0,0.4)',
                                                            borderWidth : 1
                                                        },
                                                        linkStyle : {
                                                            type : 'curve'
                                                        }
                                                    },
                                                    emphasis : {
                                                        label : {
                                                            show : false
                                                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                                                        },
                                                        nodeStyle : {
                                                        //r: 30
                                                        },
                                                        linkStyle : {}
                                                    }
                                                },
                                                useWorker : false,
                                                minRadius : 15,
                                                maxRadius : 25,
                                                gravity : 1.1,
                                                scaling : 1.1,
                                                roam : 'move',

                                                nodes : (function() {
                                                    var length1 = jsonobj.nodes.length;
                                                    //var now = new Date();
                                                    function fun(category,name,value,label,index) {
                                                        this.category = category;
                                                        this.name = name;
                                                        this.value = value;
                                                        this.index=index;
                                                        if (label==1)
                                                        	this.label=this.name+"\n"+"敏感词";
                                                    }
                                                    //var res = new Object();
                                                    var res=[];
                                                    for (var i = 0; i < length1; i++) {
                                                        //console.log(jsonobj.nodes[i].category);
                                                        res[i]=new fun(jsonobj.nodes[i].category,jsonobj.nodes[i].name,jsonobj.nodes[i].value,jsonobj.nodes[i].label,jsonobj.nodes[i].index);
                                                        
                                                    }
                                                    return res;
                                                })(),
                                                links : (function() {
                                                    var length1 = jsonobj.links.length;
                                                    function fun(source,target,weight) {
                                                        this.source = source;
                                                        this.target = target;
                                                        this.weight = weight;
                                                    
                                                    }
                                                    //var res = new Object();
                                                    var res=[];
                                                    for (var i = 0; i < length1; i++) {
                                                        res[i]=new fun(jsonobj.links[i].source,jsonobj.links[i].target,jsonobj.links[i].weight);
                                                        //console.log(res[i]);
                                                    }
                                                    return res;
                                                })()
                                            } ]
                                        };
                                        // 为echarts对象加载数据 
                                        myChart.setOption(option);

                                    });
                        });