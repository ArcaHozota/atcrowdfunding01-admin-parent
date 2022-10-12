// 聲明專門的函數顯示確認模態框
function showConfirmModal(adminArray) {
    // 打開模態框
    $("#confirmAdminModal").modal("show");
    // 清除舊的數據；
    $("#adminNameSpan").empty();
    // 定義存放adminId的全局變量
    window.adminIdArray = [];
    // 遍歷adminArray數組
    for (var i = 0; i < adminArray.length; i++) {
        var admin = adminArray[i];
        var adminName = admin.name;
        $("#adminNameSpan").append(adminName + "<br/>");
        var adminId = admin.id;
        window.adminIdArray.push(adminId);
    }
}

// 執行分頁操作，生成頁面，任何時候調用此函數都會重新加載頁面
function generateAdminPages() {
    // 1.獲取分頁數據
    var pageInfo = getPageInfoRemote();
    // 2.填充表格
    fillTableBody(pageInfo);
}

// 遠程訪問服務器獲取pageInfo數據
function getPageInfoRemote() {
    // 調用$.ajax()函數發送請求並接受$.ajax()函數的返回值
    var ajaxResult = $.ajax({
        "url": "admin/get/page/info.json",
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
    if (statusCode !== 200) {
        layer.msg("Failed! & Code = " + statusCode + " Message: " + ajaxResult.statusText);
        return null;
    }
    // 如果響應狀態碼是200，說明請求成功，獲取pageInfo對象
    var resultEntity = ajaxResult.responseJSON;
    // 從resultEntity中獲取result屬性
    var result = resultEntity.result;
    // 判斷result是否成功
    if (result === "FAILED") {
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
    $("#adminPageBody").empty();
    // 這裡清空是為了讓無檢索結果時不顯示導航條
    $("#Pagination").empty();
    // 判斷pageInfo對象是否有效
    if (pageInfo == null || pageInfo.list == null || pageInfo.list.length === 0) {
        $("#adminPageBody").append("<tr><td colspan='4' align='center'>抱歉，沒有検索到您所需的數據！</td></tr>");
        return null;
    }
    // 使用pageInfo的list屬性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var admin = pageInfo.list[i];
        var adminId = admin.id;
        var adminAcct = admin.loginAccount;
        var adminName = admin.userName;
        var adminEmail = admin.email;
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + adminId + "' class='itemBox' type='checkbox'></td>";
        var adminAcctTd = "<td>" + adminAcct + "</td>";
        var adminNameTd = "<td>" + adminName + "</td>";
        var emailTd = "<td>" + adminEmail + "</td>";
        var checkBtn = "<a href='assign/to/assignment/role/page.html?adminId=" + adminId + "&pageNum=" + pageInfo.pageNum + "&keyword=" + param.keyword + "' class='btn btn-success btn-xs'><i class='glyphicon glyphicon-check'></i></a>";
        var pencilBtn = "<a href='admin/to/edit/page.html?adminId=" + adminId + "&pageNum=" + pageInfo.pageNum + "&keyword=" + param.keyword + "' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-check'></i></a>";
        var removeBtn = "<button id='" + adminId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + adminAcctTd + adminNameTd + emailTd + buttonTd + "</tr>";
        $("#adminPageBody").append(tr);
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
    generateAdminPages();
    // 取消頁碼的超鏈接行為
    return false;
}