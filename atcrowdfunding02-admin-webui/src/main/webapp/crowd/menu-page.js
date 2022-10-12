// 準備生成樹形結構的JSON數據
function generateTree() {
    $.ajax({
        "url": "menu/get/whole/tree.json",
        "type": "POST",
        "dataType": "JSON",
        "success": function (response) {
            var result = response.result;
            if (result === "SUCCESS") {
                // 2.創建JSON對象用於存儲zTree設置；
                var setting = {
                    "view": {
                        "addDiyDom": addDiyDOM,
                        "addHoverDom": addHoverDOM,
                        "removeHoverDom": removeHoverDOM
                    },
                    "data": {
                        "key": {
                            "url": "yuumi"
                        }
                    }
                };
                var zNodes = response.data;
                // 3.初始化樹形結構；
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            } else if (result === "FAILED") {
                layer.msg(response.message);
            }
        }
    });
}

// 修改默認的圖標
function addDiyDOM(treeId, treeNode) {
    console.log("treeId" + treeId);
    console.log(treeNode);
    var spanId = treeNode.tId + "_ico";
    $("#" + spanId).removeClass().addClass(treeNode.icon);
}

// 在鼠標移入節點範圍時添加按鈕組
function addHoverDOM(treeId, treeNode) {
    // 為了在移除按鈕組時能定位到所在的span，需要給span設置有規律的id
    var btnGroupId = treeNode.tId + "_btnGrp";
    // 判斷之前是否已經添加了按鈕組
    if ($("#" + btnGroupId).length > 0) {
        return null;
    }
    // 按鈕組的標籤結構：<span><a><i></i></a><a><i></i></a></span>
    var addBtn = "<a id='" + treeNode.id + "'class='btn btn-info dropdown-toggle btn-xs addBtn' style='margin-left:10px;padding-top:0px;' href='#' title='添加子節點'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg'></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "'class='btn btn-info dropdown-toggle btn-xs removeBtn' style='margin-left:10px;padding-top:0px;' href='#' title='刪除節點'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg'></i></a>";
    var editBtn = "<a id='" + treeNode.id + "'class='btn btn-info dropdown-toggle btn-xs editBtn' style='margin-left:10px;padding-top:0px;' href='#' title='修改節點'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg'></i></a>";
    // 獲取當前節點的級別
    var level = treeNode.level;
    // 聲明變量用於儲存拼接好的代碼
    var btnHTML = "";
    // 判斷當前節點的級別
    if (level == 0) {
        // 級別為0時是根節點，可以添加子節點
        btnHTML = addBtn;
    } else if (level == 1) {
        // 級別為1時是分支節點，可以添加以及修改
        btnHTML = addBtn + " " + editBtn;
        // 獲取當前節點的子節點數量
        var length = treeNode.children.length;
        // 如果沒有子節點，可以刪除
        if (length == 0) {
            btnHTML = btnHTML + " " + removeBtn;
        }
    } else {
        // 其它情況視為葉子節點，可以進行修改和刪除
        btnHTML = editBtn + " " + removeBtn;
    }
    // 找到附著按鈕組的超鏈接(按鈕組出現的位置：節點中treeDemo_n_a超鏈接的後面)
    var anchorId = treeNode.tId + "_a";
    // 執行在超鏈接後面附加<span></span>的操作
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");
}

// 在鼠標離開節點範圍時移除按鈕組
function removeHoverDOM(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";
    $("#" + btnGroupId).remove();
}
