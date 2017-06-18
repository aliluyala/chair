<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="proxyContent" method="post">
	    <table cellpadding="5">
	    	<input type="hidden" name="type" value="2"></input> <!-- 添加代理的默认类型 -->
		    <tr>
		        <td>所属工厂:</td>
		        <td><input class="easyui-combobox" name="factoryId" data-options="valueField:'id',textField:'factoryName',url:'/<%=chair%>/manager/queryFactoryList',prompt:'请选择'" style="width: 280px;"></input></td>
		    </tr>
	        <tr>
	            <td>代理名称:</td>
	            <td><input class="easyui-textbox" type="text" name="proxyName" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>手机号:</td>
	            <td><input class="easyui-textbox" type="text" name="proxyPhone" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>提成百分比:</td>
	            <td><input class="easyui-textbox" type="text" name="proxyPercent" data-options="required:true" style="width: 280px;"></input></td>
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
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitFormByProxy()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearFormByProxy()">重置</a>
	</div>
</div>
<script type="text/javascript">
	function submitFormByProxy(){
		if(!$('#proxyContent').form('validate')){
			$.messager.alert('提示','新增代理表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/manager/add',$("#proxyContent").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增代理成功!');
				$('#proxyAdd').window('close');
				$("#proxyList").datagrid("reload");
				clearFormByProxy();
			}else{
				$.messager.alert('提示','新增代理失败!');
			}
		});
	}
	function clearFormByProxy(){
		$('#proxyContent').form('reset');
	}
	
</script>