<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代理管理</title>
</head>
<body>
	<div>
    <table class="easyui-datagrid" id="proxyList" title="代理列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/proxy/listForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10]">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'proxyName',width:200">代理名称</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
	</div>
<div id="proxyAdd" class="easyui-window" title="新增代理" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/<%=chair%>/page/proxy-add'" style="width:800px;height:600px;padding:10px;">
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
    	$('#proxyAdd').window('open');
    }
},{
    text:'删除',
    iconCls:'icon-remove',
    handler:function(){
    	var rows = $('#proxyList').datagrid('getSelections');
    	if(rows){
        	var ids = "";
        	for(var i=0; i<rows.length; i++){
        		ids = rows[i].id + "," + ids;  
        	}
    		$.messager.confirm('Confirm','确定删除这些代理吗？',function(r){
    			$.post('/<%=chair%>/proxy/batDel',{ids:ids},function(result){
					if (result){
						$('#proxyList').datagrid('reload');	// reload the user data
					} else {
						alert("---删除代理失败---")
						/* $.messager.show({	// show error message
							title: 'Error',
							msg: result.errorMsg
						}); */
					}
				},'json');
    		});
    	}
    }
}];


</script>
</body>
</html>