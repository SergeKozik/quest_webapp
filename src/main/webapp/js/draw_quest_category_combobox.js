function draw_quest_category_combobox(select_id,categories) {
    var combobox = document.getElementById(select_id);
    while (combobox.options.length > 0) {
        combobox.remove(0);
    }
    for (i in categories) {
        var opt = document.createElement('option');
        opt.value = categories[i];
        opt.innerHTML = categories[i];
        combobox.appendChild(opt);
    }
}
