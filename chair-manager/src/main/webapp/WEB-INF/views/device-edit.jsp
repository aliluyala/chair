<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="deviceContent" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>设备编号:<input type="hidden" name="id" style="width: 280px;"></input>	</td>
	            <td><input class="easyui-textbox" type="text" name="deviceNo" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>设备型号:</td>
	            <td><input class="easyui-textbox" type="text" name="deviceModel"  data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>厂家名称:</td>
	             <td><input id="factoryEdit" class="easyui-combobox" name="facrotyId" data-options="valueField:'id',textField:'factoryName',url:'/<%=chair%>/manager/queryFactoryList', prompt:'请选择',onSelect:selectFactoryEdit" style="width: 280px;"></input></td> 
	        </tr>
	        <tr>
	            <td>代理名称:</td>
	            <td><input id="proxyEdit" class="easyui-combobox" name="proxyId" data-options="valueField:'id',textField:'proxyName',url:'',prompt:'请选择',onSelect:selectProxyEdit" style="width: 280px;"></input></td>
	        </tr>
    	    <tr>
	            <td>店铺名称:</td>
	            <td><input id="shopEdit" class="easyui-combobox" name="shopId" data-options="valueField:'id',textField:'shopName',url:'',prompt:'请选择'" style="width: 280px;"></input></td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitFormByDeviceEdit()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearFormByDeviceEdit()">重置</a>
	</div>
</div>
<script type="text/javascript">

function selectFactoryEdit(){
	 var fid = $('#factoryEdit').combobox('getValue');
    $('#proxyEdit').combobox('reload', '/<%=chair%>/manager/queryProxyList?factoryID='+fid);
}


function selectProxyEdit(){
	 var pid = $('#proxyEdit').combobox('getValue');
    $('#shopEdit').combobox('reload', '/<%=chair%>/manager/queryShopList?proxyID='+pid);
}

	function submitFormByDeviceEdit(){
		if(!$('#deviceContent').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/device/edit',$("#deviceContent").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','编辑设备成功!');
				$('#deivceEdit').window('close');
				$("#deviceList").datagrid("reload");
				clearFormByDeviceEdit();
			}else{
				$.messager.alert('提示','编辑设备失败!');
			}
		});
	}
	function clearFormByDeviceEdit(){
		$('#deviceContent').form('reset');
	}
	
</script>