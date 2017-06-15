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
      <!--  手机号： <input class="easyui-numberbox" name="phoneNumber"  style="width: 180px;"></input> -->
       		 开始时间: <input id="fromDate" class="easyui-datebox" style="width:150px">
			 结束时间: <input id="toDate" class="easyui-datebox" style="width:150px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchRecord()">查询</a>
    </div>
	
    <table class="easyui-datagrid" id="statisticsRechargeList" title="充值统计列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/statisctics/listRechargeRecordForPage',method:'post',pageSize:100,toolbar:toolbar,pageList:[30,100,200],queryParams:{from:'',to:''}">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'batchNo',width:200">充值批次号</th>
	            <th data-options="field:'phoneNumber',width:100">手机号</th>
	            <th data-options="field:'rechargeAmount',width:150">充值金额（元）</th>
	            <th data-options="field:'rechargeDuration',width:150">充值时长（分钟）</th>
	            <th data-options="field:'payStatus',width:100,formatter:formatPayStatus">支付状态</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
	</div>
<script type="text/javascript">

function formatPayStatus(val, row){
	return val == 1 ?  "未支付" : "已支付" ;
}

function formatDate(val,row){
	var now = new Date(val);
	return now.format("yyyy-MM-dd hh:mm:ss");
}

//自定义工具栏
var toolbar = [];

//条件查询
function doSearchRecord(){
	var from = $("#fromDate").datebox('getValue');
	var to = $("#toDate").datebox('getValue'); 
	var queryParameter = $('#statisticsRechargeList').datagrid("options").queryParams;
	queryParameter.from = from;
	queryParameter.to = to;
	$("#statisticsRechargeList").datagrid("reload");
	
}

</script>
</body>
</html>