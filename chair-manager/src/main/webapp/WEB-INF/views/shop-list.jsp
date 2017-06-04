<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String chair= "ch"; %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家管理</title>
</head>
<body>
	<div>
    <table class="easyui-datagrid" id="shopList" title="商家列表" 
	       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/<%=chair%>/manager/listShopForPage',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10]">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',width:60">ID</th>
	            <th data-options="field:'proxyName',width:200">所属代理名称</th>
	            <th data-options="field:'shopName',width:200">商家名称</th>
	            <th data-options="field:'shopLocation',width:200">商家地址</th>
	            <th data-options="field:'createTime',width:130,align:'center',formatter:formatDate">创建日期</th>
	            <th data-options="field:'lastUpdate',width:130,align:'center',formatter:formatDate">更新日期</th>
	        </tr>
	    </thead>
	</table>
	</div>
	
<div id="shopAdd" class="easyui-window" title="新增商家" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/<%=chair%>/page/shop-add'" style="width:800px;height:600px;padding:10px;">
        The window content.
</div>
        
<div id="shopEdit" class="easyui-window" title="编辑商家" data-options="modal:true,closed:true,iconCls:'icon-edit',href:'/<%=chair%>/page/shop-edit'" style="width:800px;height:600px;padding:10px;">
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
function getSelectionsIdsByShop(){
	var shopList = $("#shopList");
	var sels = shopList.datagrid("getSelections");
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
    	$('#shopAdd').window('open');
    }
},{
    text:'编辑',
    iconCls:'icon-edit',
    handler:function(){
    	var ids = getSelectionsIdsByShop();
    	if(ids.length == 0){
    		$.messager.alert('提示','必须选择一个商家');
    		return ;
    	}
    	if(ids.indexOf(',') > 0){
    		$.messager.alert('提示','只能选择一个商家!');
    		return ;
    	}
    	$("#shopEdit").window({
    		onLoad :function(){
    			//回显数据
    			var data = $("#shopList").datagrid("getSelections")[0];
    			$("#shopEdit").form("load", data);
    		}
    	}).window("open");
    }
},{
    text:'删除',
    iconCls:'icon-remove',
    handler:function(){
		var ids = getSelectionsIdsByShop();
		if(ids.length == 0){
			$.messager.alert('提示','必须选择一个商家!');
			return ;
		}
	
		$.messager.confirm('Confirm','确定删除这些商家吗？',function(r){
			if (r){
    			$.post('/<%=chair%>/manager/delete',{ids:ids,type:3},function(result){
					if (result){
						$('#shopList').datagrid('reload');	// reload the user data
					} else {
						$.messager.alert('提示','删除商家失败!');
					}
				},'json');
			}
		});
    }
}];


</script>
</body>
</html>