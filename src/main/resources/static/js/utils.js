var dataFormat = function (columnName, value) {
    var type = value;
    if (columnName == "type") {
        if (value == "1") {
            type = "字典项";
        } else if (value == "2") {
            type = "字典值";
        }
    } else if (columnName == "status") {
        if (value == "1") {
            type = "启用";
        } else if (value == "0") {
            type = "禁用";
        }
    }
    return type;
};