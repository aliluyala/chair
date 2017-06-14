<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备管理</title>
</head>
<body>
	<div>
		<div data-options="region:'north',split:false,border:false,title:'查询条件',collapsed:false,iconCls:'icon-search'" >  
       设备编号： <input class="easyui-textbox" name="deviceNo"  style="width: 180px;"></input>
       &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearchDeviceLog()">查询</a>  
    </div>
    <table class="easyui-datagrid" id="deviceStatusLogList" title="设备日志列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/device/listDeviceLogForPage',method:'post',pageSize:100,toolbar:toolbar,pageList:[30,100,200],queryParams:{deviceNo:''}">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'deviceNo',width:200">设备编号</th>
	            <th data-options="field:'deviceStatus',width:100">设备状态</th>
	            <th data-options="field:'deviceStatusDesc',width:100">设备状态描述</th>
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
var toolbar = [];

//条件查询
function doSearchDeviceLog(){
	var deviceNo = $("[name='deviceNo']").val();
	console.log("---deviceNo--->>>"+deviceNo);
	var queryParameter = $('#deviceStatusLogList').datagrid("options").queryParams;  
    queryParameter.deviceNo = deviceNo;  
	$("#deviceStatusLogList").datagrid("reload");  
}

</script>
</body>
</html>