<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="shopContent" method="post">
	    <table cellpadding="5">
    	<input type="hidden" name="type" value="3"></input> <!-- 添加代理的默认类型 -->
	        <tr>
		        <td>代理名称:</td>
		        <td><input class="easyui-combobox" name="proxyId" data-options="valueField:'id',textField:'proxyName',url:'/<%=chair%>/manager/queryProxyList?factoryID=0',prompt:'请选择'" style="width: 280px;"></input></td>
		    </tr>
	        <tr>
	            <td>商家名称:</td>
	            <td><input class="easyui-textbox" type="text" name="shopName" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
		        <tr>
	            <td>商家地址:</td>
	            <td><input class="easyui-textbox" type="text" name="shopLocation" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
		        <tr>
		        <td>商家联系:</td>
		        <td><input class="easyui-textbox" type="text" name="shopContact" data-options="required:true" style="width: 280px;"></input></td>
		    </tr>
			    <tr>
			    <td>手机号:</td>
			    <td><input class="easyui-textbox" type="text" name="shopContactPhone" data-options="required:true" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>提成百分比:</td>
				<td><input class="easyui-textbox" type="text" name="shopPercent" data-options="required:true" style="width: 280px;"></input></td>
			</tr>
	        <tr>
	            <td>管理员账号:</td>
	            <td><input class="easyui-textbox" type="text" name="user" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>管理员密码:</td>
	            <td><input class="easyui-textbox" type="text" name="password" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitFormByShop()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearFormByShop()">重置</a>
	</div>
</div>
<script type="text/javascript">
	function submitFormByShop(){
		if(!$('#shopContent').form('validate')){
			$.messager.alert('提示','商家表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/manager/add',$("#shopContent").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增商家成功!');
				$('#shopAdd').window('close');
				$("#shopList").datagrid("reload");
				clearFormByShop();
			}else{
				$.messager.alert('提示','新增商家失败!');
			}
		});
	}
	function clearFormByShop(){
		$('#content').form('reset');
	}
	
</script>