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
	 <div id="DIV_toolbar" Style="margin:0px; padding:5px">  
        类型： 
        <input class="easyui-combobox" name="facrotyId" 
        data-options="valueField:'id',textField:'factoryName',
        url:'/<%=chair%>/factory/list',prompt:'请选择'" style="width: 280px;"></input>
    </div> 
    <table class="easyui-datagrid" id="statisticsRechargeList" title="充值统计列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/statistics/listForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10],queryParams:{params:123}">
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
<div id="rechargeAdd" class="easyui-window" title="新增充值套餐" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/<%=chair%>/page/recharge-add'" style="width:800px;height:600px;padding:10px;">
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
	var rechargeList = $("#statisticsRechargeList");
	var sels = rechargeList.datagrid("getSelections");
	var ids = [];
	for(var i in sels){
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}

//入参
var queryParams = {
	params:{
		factoryID:1,
		proxyID:2,
		shopID:3
	}
}


//自定义工具栏
var toolbar = [{
    text:'新增',
    iconCls:'icon-add',
    handler:function(){
    	/* $('#rechargeAdd').window('open'); */
    }
},{
    text:'删除',
    iconCls:'icon-remove',
    handler:function(){
    	var rows = $('#statisticsRechargeList').datagrid('getSelections');
    	rows = 0;
    	if(rows){
    		//alert("---rows---"+JSON.stringify(rows))
        	var ids = "";
        	for(var i=0; i<rows.length; i++){
        		ids = rows[i].id + "," + ids;  
        	}
    		$.messager.confirm('Confirm','确定删除此充值套餐吗？',function(r){
    			$.post('/<%=chair%>/recharge/batDel',{ids:ids},function(result){
					if (result){
						$('#rechargeList').datagrid('reload');	// reload the user data
					} else {
						$.messager.alert('提示','删除充值套餐失败!');
					}
				},'json');
    		});
    	}
    }
}];

</script>
</body>
</html>