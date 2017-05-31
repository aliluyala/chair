<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消费统计</title>
</head>
<body>
	<div>
	<div data-options="region:'north',split:false,border:false,title:'查询条件',collapsed:false,iconCls:'icon-search'" >  
       工厂： <input class="easyui-combobox" name="facrotyId" data-options="valueField:'id',textField:'factoryName',url:'/<%=chair%>/factory/list',prompt:'请选择'" style="width: 80px;"></input>
	代理：<input class="easyui-combobox" name="proxyId" data-options="valueField:'id',textField:'proxyName',url:'/<%=chair%>/proxy/list',prompt:'请选择'" style="width: 80px;"></input>
	商铺：<input class="easyui-combobox" name="shopId" data-options="valueField:'id',textField:'shopName',url:'/<%=chair%>/shop/list',prompt:'请选择'" style="width: 80px;"></input>  
       &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearchConsume()">查询</a>  
    </div>
	
    <table class="easyui-datagrid" id="statisticsConsumeList" title="消费统计列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/statistics/listConsumeForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10],queryParams:{factoryID:0,proxyID:0,shopID:0}">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'phoneNumber',width:100">手机号</th>
	            <th data-options="field:'consumedDuration',width:150">消费时长（单位：分钟）</th>
	            <th data-options="field:'factoryName',width:100">厂家名称</th>
	            <th data-options="field:'proxyName',width:100">代理名称</th>
	            <th data-options="field:'shopName',width:100">店铺名称</th>
	            <th data-options="field:'shopLocation',width:100">店铺位置</th>
	            <th data-options="field:'deviceId',width:100">设备ID</th>
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
	var rechargeList = $("#statisticsConsumeList");
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
function doSearchConsume(){
	var factoryID = $("[name='facrotyId']").val();
	var proxyID = $("[name='proxyId']").val();
	var shopID = $("[name='shopId']").val();
	var queryParameter = $('#statisticsConsumeList').datagrid("options").queryParams;  
    queryParameter.factoryID = factoryID ? factoryID : 0;  
    queryParameter.proxyID = proxyID ? proxyID : 0;  
    queryParameter.shopID = shopID ? shopID : 0;  
    console.log("查询消费明细---factoryID--->>>"+queryParameter.factoryID+" \n proxyID--->>>"+proxyID+"\n shopID--->>>"+shopID);
	$("#statisticsConsumeList").datagrid("reload");  
}

</script>
</body>
</html>