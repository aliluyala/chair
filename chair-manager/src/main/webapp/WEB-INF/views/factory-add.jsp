<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="factoryContent" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>厂家名称:</td>
	            <td><input class="easyui-textbox" type="text" name="factoryName" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	function submitForm(){
		if(!$('#factoryContent').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/factory/save',$("#factoryContent").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增厂家成功!');
				$('#factoryAdd').window('close');
				$("#factoryList").datagrid("reload");
				clearForm();
			}else{
				$.messager.alert('提示','新增厂家失败!');
			}
		});
	}
	function clearForm(){
		$('#factoryContent').form('reset');
	}
	
</script>