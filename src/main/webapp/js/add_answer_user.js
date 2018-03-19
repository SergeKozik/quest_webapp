function add_answer_user(table_id,caption_title,caption_answer,caption_del) {
    var parent = document.getElementById(table_id);
    var new_row = document.createElement('tr');
    var td1 = document.createElement('td');
    var td2 = document.createElement('td');
    var td0 = document.createElement('td');
    var curId = id_generator();
    new_row.id = 'rowId'+curId;
    td0.innerHTML = '<input class="user-answer-create" name="answer_text" type="text" value="'+caption_title+'"/>';
    new_row.appendChild(td0);
    td1.innerHTML = '<input class="user-answer-create" name="text_var" disabled type="text" value="'+caption_answer+'"/>';
    new_row.appendChild(td1);
    var tmpString = "'rowId"+curId+"'";
    var tmpString2 = "'"+table_id+"'";
    td2.innerHTML = '<input type="button" class="button-answer-js del" onclick="del_answer_custom('+tmpString2+','+tmpString+')" value="'+caption_del+'"/>';
    new_row.appendChild(td2);
    parent.appendChild(new_row);
}