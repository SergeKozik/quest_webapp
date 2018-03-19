function del_answer_custom(table_id,elem_id) {
    var child = document.getElementById(elem_id);
    var parent = document.getElementById(table_id);
    parent.removeChild(child);
}
