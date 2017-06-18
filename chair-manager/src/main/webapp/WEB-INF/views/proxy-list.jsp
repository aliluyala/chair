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
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/manager/listProxyForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10]">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'factoryName',width:200">所属厂家名称</th>
	            <th data-options="field:'proxyName',width:200">代理名称</th>
	            <th data-options="field:'proxyPhone',width:200">手机号</th>
	            <th data-options="field:'proxyPercent',width:200,formatter:formatPercent">提成百分比</th>
	            <th data-options="field:'user',width:200">管理员账号</th>
	            <th data-options="field:'password',width:200">管理员密码</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
	</div>
	
<div id="proxyAdd" class="easyui-window" title="新增代理" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/<%=chair%>/page/proxy-add'" style="width:800px;height:600px;padding:10px;">
        The window content.
</div>

<div id="proxyEdit" class="easyui-window" title="编辑代理" data-options="modal:true,closed:true,iconCls:'icon-edit',href:'/<%=chair%>/page/proxy-edit'" style="width:800px;height:600px;padding:10px;">
        The window content.
</div>
        
<script type="text/javascript">

function formatPercent(val, row){
	return (val == "null" || val == null || typeof val == "undefined")  ? "" : val +"%";
}


function formatDate(val,row){
	var now = new Date(val);
	return now.format("yyyy-MM-dd hh:mm:ss");
}
function formatBirthday(val,row){
	var now = new Date(val);
	return now.format("yyyy-MM-dd");
}
function getSelectionsIdsByProxy(){
	var proxyList = $("#proxyList");
	var sels = proxyList.datagrid("getSelections");
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
    text:'编辑',
    iconCls:'icon-edit',
    handler:function(){
    	var ids = getSelectionsIdsByProxy();
    	if(ids.length == 0){
    		$.messager.alert('提示','必须选择一个代理');
    		return ;
    	}
    	if(ids.indexOf(',') > 0){
    		$.messager.alert('提示','只能选择一个代理!');
    		return ;
    	}
    	$("#proxyEdit").window({
    		onLoad :function(){
    			//回显数据
    			var data = $("#proxyList").datagrid("getSelections")[0];
    			$("#proxyEdit").form("load", data);
    		}
    	}).window("open");
    }
},{
    text:'删除',
    iconCls:'icon-remove',
    handler:function(){
    	var ids = getSelectionsIdsByProxy();
    	if(ids.length == 0){
    		$.messager.alert('提示','必须选择一个代理!');
    		return ;
    	}
    	
    	$.messager.confirm('Confirm','确定删除这些代理吗？',function(r){
			if (r){
    			$.post('/<%=chair%>/manager/delete',{ids:ids,type:2},function(result){
					if (result){
						$('#proxyList').datagrid('reload');	// reload the user data
					} else {
						$.messager.alert('提示','删除代理失败!');
					}
				},'json');
			}
		});
    }
}];


</script>
</body>
</html>