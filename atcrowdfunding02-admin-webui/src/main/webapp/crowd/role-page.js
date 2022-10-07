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
    // 調用$.ajax()函數發送請求並接受$.ajax()函數的返回值
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
    // 判斷當前響應狀態碼是否為200
    var statusCode = ajaxResult.status;
    // 如果當前響應狀態碼不是200，說明發生了錯誤或其他意外情况，顯示提示消息，讓當前函數停止執行
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return null;
    }
    // 如果響應狀態碼是200，說明請求成功，獲取pageInfo對象
    var resultEntity = ajaxResult.responseJSON;
    // 從resultEntity中獲取result屬性
    var result = resultEntity.result;
    // 判斷result是否成功
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    // 確認result成功後獲取pageInfo對象
    var pageInfo = resultEntity.data;
    // 返回pageInfo
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 清除tbody中的舊內容
    $("#rolePageBody").empty();
    // 這裡清空是為了讓無檢索結果時不顯示導航條
    $("#Pagination").empty();
    // 判斷pageInfo對象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉，沒有檢索到您所需的數據！</td></tr>");
        return null;
    }
    // 使用pageInfo的list屬性填充tbody
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
    // 生成分頁導航條
    generateNavigator(pageInfo);
}

// 生成分頁頁碼導航條
function generateNavigator(pageInfo) {
    // 獲取總記錄數
    var totalRecord = pageInfo.total;
    // 聲明相關屬性
    var properties = {
        "num_edge_entries": 1,
        "num_display_entries": 3,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1
    };
    // 調用pagination()函數
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