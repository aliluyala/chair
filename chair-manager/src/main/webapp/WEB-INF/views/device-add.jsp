<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="deviceContent" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>设备编号:</td>
	            <td><input class="easyui-textbox" type="text" name="deviceNo" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>设备型号:</td>
	            <td><input class="easyui-textbox" type="text" name="deviceModel"  data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>厂家名称:</td>
	             <td><input id="factory" class="easyui-combobox" name="facrotyId" data-options="valueField:'id',textField:'factoryName',url:'/<%=chair%>/manager/queryFactoryList',prompt:'请选择',onSelect:selectFactory" style="width: 280px;"></input></td> 
	        </tr>
	        <tr>
	            <td>代理名称:</td>
	            <td><input id="proxy" class="easyui-combobox" name="proxyId" data-options="valueField:'id',textField:'proxyName',url:'',prompt:'请选择',onSelect:selectProxy" style="width: 280px;"></input></td>
	        </tr>
    	    <tr>
	            <td>店铺名称:</td>
	            <td><input id="shop" class="easyui-combobox" name="shopId" data-options="valueField:'id',textField:'shopName',url:'',prompt:'请选择'" style="width: 280px;"></input></td>
	        </tr>
	       <!--  <tr>
	            <td>店铺位置:</td>
	            <td><input class="easyui-textbox" type="text" name="shopLocation"  data-options="required:true" style="width: 280px;"></input></td>
	        </tr> --> 
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitFormByDevice()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearFormByDevice()">重置</a>
	</div>
</div>
<script type="text/javascript">

function selectFactory(){
	 var fid = $('#factory').combobox('getValue');
     $('#proxy').combobox('reload', '/<%=chair%>/manager/queryProxyList?factoryID='+fid);
}


function selectProxy(){
	 var pid = $('#proxy').combobox('getValue');
     $('#shop').combobox('reload', '/<%=chair%>/manager/queryShopList?proxyID='+pid);
}

	function submitFormByDevice(){
		if(!$('#deviceContent').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/device/save',$("#deviceContent").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增设备成功!');
				$('#deivceAdd').window('close');
				$("#deviceList").datagrid("reload");
				clearFormByDevice();
			}else{
				$.messager.alert('提示','新增设备失败!');
			}
		});
	}
	function clearFormByDevice(){
		$('#deviceContent').form('reset');
	}
	
</script>