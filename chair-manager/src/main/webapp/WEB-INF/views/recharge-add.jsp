<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String chair= "ch"; %>
<div style="padding:10px 10px 10px 10px">
	<form id="rechargePackageContent" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>套餐名称:</td>
	            <td><input class="easyui-textbox" type="text" name="packageName" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>充值金额:</td>
	            <td><input class="easyui-textbox" type="text" name="rechargeAmoun" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>充值时长:</td>
	            <td><input class="easyui-textbox" type="text" name="rechargeDuration"  data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
            <td>是否有效:</td>
            <td><input class="easyui-combobox" name="status" data-options="valueField:'status',textField:'rechargePeackageStatus',data:[{status:1,rechargePeackageStatus:'有效'},{status:2,rechargePeackageStatus:'无效'}],prompt:'请选择'" style="width: 280px;"></input></td>
        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitFormByRecharge()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearFormByRecharge()">重置</a>
	</div>
</div>
<script type="text/javascript">
	function submitFormByRecharge(){
		if(!$('#rechargePackageContent').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post('/<%=chair%>/recharge/save',$("#rechargePackageContent").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增套餐成功!');
				$('#rechargeAdd').window('close');
				$("#rechargeList").datagrid("reload");
				clearForm();
			}else{
				$.messager.alert('提示','新增套餐失败!');
			}
		});
	}
	function clearFormByRecharge(){
		$('#rechargePackageContent').form('reset');
	}
</script>