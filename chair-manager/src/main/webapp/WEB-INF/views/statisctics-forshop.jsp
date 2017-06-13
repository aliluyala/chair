<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家收益统计</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
</head>
<body>

	<div>
	        我的总收益：    <input id="totalIncome" class="easyui-textbox" style="width: 80px;" data-options="readonly:true"></input>
		今日收益：    <input id="dayIncome" class="easyui-textbox" style="width: 80px;" data-options="readonly:true"></input>
     	<div>
			 开始时间: <input id="fromDate" class="easyui-datebox" style="width:150px">
			 结束时间: <input id="toDate" class="easyui-datebox" style="width:150px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
    </div>
	
    <table class="easyui-datagrid" id="statiscticsShopIncomeList" title="收益统计列表" 
	       data-options="readonly:true,singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/statisctics/listShopIncomeForPage',method:'post',pageSize:100,toolbar:toolbar,pageList:[30,100,200],queryParams:{from:'',to:''}">
	    <thead>
	        <tr>
	        	<!-- <th data-options="field:'ck',checkbox:true"></th> -->
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'deviceNo',width:200">设备编号</th>
	            <th data-options="field:'totalDuration',width:150">消费(分钟)</th>
	            <th data-options="field:'totalIncome',width:100">收益(分钟)</th>
	        </tr>
	    </thead>
	</table>
	</div>
<script type="text/javascript">

(function(){

	//测试接口
	$.ajax({
		type:'post',
		url:'/<%=chair%>/statisctics/queryShopBaseInfo/',
		success:function(data){
			//alert(JSON.stringify(data));
			var o = data.data;
			//alert(JSON.stringify(o))
			$("#totalIncome").textbox("setValue", o.totalIncome);
			$("#dayIncome").textbox("setValue", o.dayIncome);
			
		}
	}) 
})();


//查询
function doSearch(){
	var from = $("#fromDate").datebox('getValue');
	var to = $("#toDate").datebox('getValue'); 
	var queryParameter = $('#statiscticsShopIncomeList').datagrid("options").queryParams;
	queryParameter.from = from;
	queryParameter.to = to;
	$("#statiscticsShopIncomeList").datagrid("reload");
	
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


</script>
</body>
</html>