function add_answer_mark(table_id,caption_del,caption_answer) {
    var parent = document.getElementById(table_id);
    var new_row = document.createElement('tr');
    var td1 = document.createElement('td');
    var td2 = document.createElement('td');
    var td3 = document.createElement('td');
    var curId = id_generator();
    new_row.id = 'rowId'+curId;
    td1.innerHTML = '<input name="answer_var" type="text" value="'+caption_answer+'"/>';
    new_row.appendChild(td1);
    td3.innerHTML = '<input name="answer_mark" type="text" value="0"/>';
    new_row.appendChild(td3);
    var tmpString = "'rowId"+curId+"'";
    var tmpString2 = "'"+table_id+"'";
    td2.innerHTML = '<input type="button" class="button-answer-js del" onclick="del_answer_custom('+tmpString2+','+tmpString+')" value="'+caption_del+'"/>';
    new_row.appendChild(td2);
    parent.appendChild(new_row);
}
