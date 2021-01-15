<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="site-text" style="margin: 5%; display: none" id="window">
    <form class="layui-form" id="importExcelData" method="post" lay-filter="example">
        <div class="layui-form-item">
            <label class="layui-form-label">导入方式</label>
            <div class="layui-input-block">
                <input type="radio" name="isapend" value="0" title="清空数据" checked="">
                <input type="radio" name="isapend" value="1" title="追加导入">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">数据校验</label>
            <div class="layui-input-block">
                <input type="radio" name="checkData" value="0" title="直接导入" >
                <input type="radio" name="checkData" value="1" title="校验后导入" checked="">
            </div>
        </div>
        <button type="button" class="layui-btn layui-btn-sm" style="display: none" id="importExcel"><i class="layui-icon"></i>导入文件</button>
        <!--  <div class="layui-form-item">
           <div class="layui-input-block">
             <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
           </div>
         </div> -->
    </form>
</div>

<div class="site-text" style="margin: 5%; display: none" id="exportWin" >
    <form class="layui-form" id="exportExcelData" method="post" lay-filter="example">
        <div class="layui-form-item">
            <label class="layui-form-label">全部导出</label>
            <div class="layui-input-block">
                <input type="radio" name="isAll" value="0" title="否" >
                <input type="radio" name="isAll" value="1" title="是" checked="">
            </div>
        </div>
    </form>
</div>