<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2020/12/24
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>开始使用layui</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    </head>
</head>
<body>
    <div id="demo"></div>
    <div id="demo2"></div>
    <!-- 你的HTML代码 -->
    <script src="../js/jquery-1.8.3.js"></script>
    <script src="../layui/layui.js"></script>
    <script>
    var layout = [
    { name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: '' },
    {
    name: '操作',
    headerClass: 'value_col',
    colClass: 'value_col',
    style: 'width: 20%',
    render: function(row) {
    return "<a class='layui-btn layui-btn-danger layui-btn-sm' onclick='del(" + row + ")'><i class='layui-icon'>&#xe640;</i> 删除</a>"; //列渲染
    }
    },
    ];

    layui.config({
    base : '../layui/'
    }).extend({
    treetable : 'lay/modules/treetable'
    });

    layui.use(['form', 'treetable', 'layer'], function () {
    var layer = layui.layer, form = layui.form, $ = layui.jquery;

    var tree1 = layui.treetable({
    elem: '#demo', //传入元素选择器
    checkbox: true,
    nodes: [{
    "id": "1",
    "name": "父节点1",
    "children": [{
    "id": "11",
    "name": "子节点11"
    },
    {
    "id": "12",
    "name": "子节点12"
    }
    ]
    },
    {
    "id": "2",
    "name": "父节点2",
    "children": [{
    "id": "21",
    "name": "子节点21",
    "children": [{
    "id": "211",
    "name": "子节点211"
    }]
    }]
    }
    ],
    layout: layout
    });

    var tree2 = layui.treeGird({
    elem: '#demo2', //传入元素选择器
    spreadable: true, //设置是否全展开，默认不展开
    checkbox: false,
    nodes: [{
    "id": "d1",
    "name": "父节点1",
    "children": [{
    "id": "d11",
    "name": "子节点11",
    "checked": true
    }
    ]
    },
    {
    "id": "d2",
    "name": "父节点2",
    "children": [{
    "id": "d21",
    "name": "子节点21",
    "children": [{
    "id": "d211",
    "name": "子节点211"
    }]
    }]
    }
    ],
    layout: layout
    });



    form.render();
    });

    function getData(){
    var data = [];
    $.ajax({
    url: "../json/data1.json", //后台数据请求地址
    type: "get",
    async:false,
    dataType:"json",
    success: function(resut){
    console.log(resut);
    data = resut;
    }
    });
    return data;
    }
    </script>
    </body>
</html>
