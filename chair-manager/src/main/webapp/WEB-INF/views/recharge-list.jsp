<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员管理系统</title>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4/themes/icon.css" />
<script type="text/javascript" src="/js/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
	<div>
    <table class="easyui-datagrid" id="rechargeList" title="会员列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/recharge/list',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10]">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'packageName',width:200">套餐名称</th>
	            <th data-options="field:'rechargeAmoun',width:100">充值金额</th>
	            <th data-options="field:'rechargeDuration',width:100">充值时长</th>
	            <th data-options="field:'status',width:100">设备状态</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
	</div>
<div id="userAdd" class="easyui-window" title="新增会员" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/user/add'" style="width:800px;height:600px;padding:10px;">
        The window content.
</div>
<div id="userEdit" class="easyui-window" title="编辑会员" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/user/edit?ids=520'" style="width:800px;height:600px;padding:10px;">
        The window content.
</div>
<script type="text/javascript">
function formatDate(val,row){
	var now = new Date(val);
	return now.format("yyyy-MM-dd hh:mm:ss");
}
function formatBirthday(val,row){
	var now = new Date(val);
	return now.format("yyyy-MM-dd");
}
function getSelectionsIds(){
	var rechargeList = $("#rechargeList");
	var sels = rechargeList.datagrid("getSelections");
	var ids = [];
	for(var i in sels){
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}
var toolbar = [{
    text:'新增',
    iconCls:'icon-add',
    handler:function(){
    	$('#userAdd').window('open');
    }
}];
</script>
</body>
</html>