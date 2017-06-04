<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消费套餐管理</title>
</head>
<body>
	<div>
    <table class="easyui-datagrid" id="consumeList" title="消费套餐列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/consume/listForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10]">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'packageName',width:200">套餐名称</th>
	            <th data-options="field:'consumedDuration',width:100">消费时长</th>
	            <th data-options="field:'status',width:100,formatter:formatStatus">套餐状态</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
	</div>
<div id="consumeAdd" class="easyui-window" title="新增消费套餐" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/<%=chair%>/page/consume-add'" style="width:800px;height:600px;padding:10px;">
        The window content.
</div>
<script type="text/javascript">

function formatStatus(val,row){
	return val == 1 ? "有效" : "无效";
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
	var consumeList = $("#consumeList");
	var sels = consumeList.datagrid("getSelections");
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
		$('#consumePackageContent').form('reset');
    	$('#consumeAdd').window('open');
    }
},{
    text:'删除',
    iconCls:'icon-remove',
    handler:function(){
    	var rows = $('#consumeList').datagrid('getSelections');
    	if(rows){
    		//alert("---rows---"+JSON.stringify(rows))
        	var ids = "";
        	for(var i=0; i<rows.length; i++){
        		ids = rows[i].id + "," + ids;  
        	}
    		$.messager.confirm('Confirm','确定删除此消费套餐吗？',function(r){
    			if (r){
	    			$.post('/<%=chair%>/consume/batDel',{ids:ids},function(result){
						if (result){
							$('#consumeList').datagrid('reload');	// reload the user data
						} else {
							$.messager.alert('提示','删除消费套餐失败!');
						}
					},'json');
    			}
    		});
    	}
    }
}];
</script>
</body>
</html>