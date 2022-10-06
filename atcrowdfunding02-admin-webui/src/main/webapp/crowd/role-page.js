// 聲明專門的函數顯示確認模態框
function showConfirmModal(roleArray) {
    // 打開模態框
    $("#confirmRoleModal").modal("show");
    // 清除舊的數據；
    $("#roleNameSpan").empty();
    // 定義存放roleId的全局變量
    window.roleIdArray = [];
    // 遍歷roleArray數組
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.name;
        $("#roleNameSpan").append(roleName + "<br/>");
        var roleId = role.id;
        window.roleIdArray.push(roleId);
    }
}

// 聲明生成權限樹形結構的函數
function fillAuthTree() {
    var ajaxReturn = $.ajax({
        "url": "assign/get/all/auth.json",
        "type": "POST",
        "dataType": "JSON",
        "async": false
    });
    if (ajaxReturn.status != 200) {
        layer.msg("請求出錯！" + ajaxReturn.statusText);
        return null;
    }
    var authList = ajaxReturn.responseJSON.data;
    var setting = {
        "data": {
            "simpleData": {
                "enable": true,
                "pIdKey": "categoryId"
            },
            "key": {
                "name": "title"
            }
        },
        "check": {
            "enable": true
        }
    };
    //生成樹形結構
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);
    //獲取zTreeObj對象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    // 調用zTreeObj對象的方法，把節點展開
    zTreeObj.expandAll(true);
    //查詢已分配權限的id組成的數組
    ajaxReturn = $.ajax({
        "url": "assign/get/assigned/auth/id/by/role/id.json",
        "type": "POST",
        "data": {
            "roleId": window.roleId
        },
        "dataType": "JSON",
        "async": false
    });
    if (ajaxReturn.status != 200) {
        layer.msg("請求出錯！" + ajaxReturn.statusText);
        return null;
    } else {
        var authIdArray = ajaxReturn.responseJSON.data;
        for (var i = 0; i < authIdArray.length; i++) {
            var authId = authIdArray[i];
            var treeNode = zTreeObj.getNodeByParam("id", authId);
            var checked = true;
            var checkTypeFlag = false;
            zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
        }
    }
}

// 執行分頁操作，生成頁面，任何時候調用此函數都會重新加載頁面
function generatePage() {
    // 1.獲取分頁數據
    var pageInfo = getPageInfoRemote();
    // 2.填充表格
    fillTableBody(pageInfo);
}

// 遠程訪問服務器獲取pageInfo數據
function getPageInfoRemote() {
    // 调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    });
    console.log(ajaxResult);
    // 判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;
    // 如果当前响应状态码不是200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return null;
    }
    // 如果响应状态码是200，说明请求处理成功，获取pageInfo
    var resultEntity = ajaxResult.responseJSON;
    // 从resultEntity 中获取result 属性
    var result = resultEntity.result;
    // 判断result 是否成功
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    // 确认result 为成功后获取pageInfo
    var pageInfo = resultEntity.data;
    // 返回pageInfo
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 清除tbody 中的旧的内容
    $("#rolePageBody").empty();
    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();
    // 判断pageInfo 对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉，沒有檢索到您所需的數據！</td></tr>");
        return null;
    }
    // 使用pageInfo 的list 属性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);
    }
    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;
    // 声明相关属性
    var properties = {
        "num_edge_entries": 1,
        "num_display_entries": 3,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1
    };
    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻頁時的回調函數
function paginationCallBack(pageIndex, jQuery) {
    // 修改window對象的pageNum屬性
    window.pageNum = pageIndex + 1;
    // 調用分頁函數
    generatePage();
    // 取消頁碼的超鏈接行為
    return false;
}