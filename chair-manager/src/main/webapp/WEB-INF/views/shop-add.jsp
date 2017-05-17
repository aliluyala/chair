<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="shopContent" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>商家名称:</td>
	            <td><input class="easyui-textbox" type="text" name="shopName" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>商家地址:</td>
	            <td><input class="easyui-textbox" type="text" name="shopLocation" data-options="required:true" style="width: 280px;"></input></td>
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
		if(!$('#shopContent').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/shop/save',$("#content").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增商家成功!');
				$('#shopAdd').window('close');
				$("#shopList").datagrid("reload");
				clearForm();
			}else{
				$.messager.alert('提示','新增商家失败!');
			}
		});
	}
	function clearForm(){
		$('#content').form('reset');
	}
	
</script>