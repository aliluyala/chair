<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代理收益统计</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
</head>
<body>

	<div>
	        总收益：    <input id="totalIncome" class="easyui-textbox" style="width: 80px;" data-options="readonly:true"></input>
		商铺数：    <input id="totalShop" class="easyui-textbox" style="width: 80px;" data-options="readonly:true"></input>
		设备数：    <input id="totalDevice" class="easyui-textbox" style="width: 80px;" data-options="readonly:true"></input>  
		今日收益： <input id="dayIncome" class="easyui-textbox" style="width: 80px;" data-options="readonly:true"></input>  
     	<div>
			 开始时间: <input id="fromDate" class="easyui-datebox" style="width:150px">
			 结束时间: <input id="toDate" class="easyui-datebox" style="width:150px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
    </div>
	
    <table class="easyui-datagrid" id="statiscticsProxyIncomeList" title="收益统计列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/statisctics/listProxyIncomeForPage',method:'post',pageSize:100,toolbar:toolbar,pageList:[30,100,200],queryParams:{from:'',to:''}">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'shopName',width:100">商铺</th>
	            <th data-options="field:'shopLocation',width:150">地址</th>
	            <th data-options="field:'shopPhone',width:100">电话</th>
	            <th data-options="field:'shopContact',width:100">联系人</th>
	            <th data-options="field:'totalDevice',width:100">设备数</th>
	            <th data-options="field:'totalDuration',width:100">消费时长(分钟)</th>
	            <th data-options="field:'income',width:100">收益(分钟)</th>
	        </tr>
	    </thead>
	</table>
	</div>
<script type="text/javascript">

(function(){

	//基础统计
	$.ajax({
		type:'post',
		url:'/<%=chair%>/statisctics/queryProxyBaseInfo/',
		success:function(data){
			//alert(JSON.stringify(data));
			var o = data.data;
			//alert(JSON.stringify(o))
			$("#totalIncome").textbox("setValue", o.totalIncome);
			$("#totalShop").textbox("setValue", o.totalShop);
			$("#totalDevice").textbox("setValue", o.totalDevice);
		}
	});
	
	//今日收益
	$.ajax({
		type:'post',
		url:'/<%=chair%>/statisctics/queryDayIncomeForProxy/',
		success:function(data){
			//alert(JSON.stringify(data));
			var o = data.data;
			//alert(JSON.stringify(o))
			$("#dayIncome").textbox("setValue", o.dayIncome);
		}
	});
})();


//查询
function doSearch(){
	var from = $("#fromDate").datebox('getValue');
	var to = $("#toDate").datebox('getValue'); 
	var queryParameter = $('#statiscticsProxyIncomeList').datagrid("options").queryParams;
	queryParameter.from = from;
	queryParameter.to = to;
	$("#statiscticsProxyIncomeList").datagrid("reload");
	
}


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