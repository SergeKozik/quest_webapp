function validate_register(div_id,message) {
    var result = true;
    var obj = document.forms.form_register;
    if (obj.user_name.value.trim()=="") {
        result = false;
    }
    if ((obj.passw.value.trim()=="")||(obj.passw_double.value.trim()=="")||(obj.passw.value!==obj.passw_double.value)) {
        result = false;
    }
    if (obj.select_role.selectedIndex==0) {
        result = false;
    }
    if (!result) {
        var error_div = document.getElementById(div_id);
        error_div.innerHTML = message;
    }
    return result;
}