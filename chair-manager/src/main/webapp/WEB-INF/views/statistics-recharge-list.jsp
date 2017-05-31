<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值统计</title>
</head>
<body>
	<div>
	<div data-options="region:'north',split:false,border:false,title:'查询条件',collapsed:false,iconCls:'icon-search'" >  
       手机号： <input class="easyui-numberbox" name="phoneNumber"  style="width: 180px;"></input>
       &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearch()">查询</a>  
    </div>
	
    <table class="easyui-datagrid" id="statisticsRechargeList" title="充值统计列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/statistics/listRecordForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10],queryParams:{phoneNumber:0}">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'batchNo',width:200">充值批次号</th>
	            <th data-options="field:'phoneNumber',width:100">手机号</th>
	            <th data-options="field:'rechargeAmount',width:100">充值金额（单位：元）</th>
	            <th data-options="field:'rechargeDuration',width:100">充值时长（单位：分钟）</th>
	            <th data-options="field:'payStatus',width:100">支付状态</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
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
	var rechargeList = $("#statisticsRechargeList");
	var sels = rechargeList.datagrid("getSelections");
	var ids = [];
	for(var i in sels){
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}

//自定义工具栏
var toolbar = [];

//条件查询
function doSearch(){
	var phoneNumber = $("[name='phoneNumber']").val();
	console.log("---phoneNumber--->>>"+phoneNumber);
	var queryParameter = $('#statisticsRechargeList').datagrid("options").queryParams;  
    queryParameter.phoneNumber = phoneNumber;  
	$("#statisticsRechargeList").datagrid("reload");  
}

</script>
</body>
</html>