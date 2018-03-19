function validate_login(error_div_id, message) {
    var name=document.forms.form_login.user_name.value;
    var passw=document.forms.form_login.passw.value;
    var result = true;
    var nick_regexp = new RegExp("(^[A-Z]\\w{4,}$)");
    var passw_regexp = new RegExp("(?=^.{6,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*");
    if (nick_regexp.test(name)==false) {
        result = false;
    }
    if (passw_regexp.test(passw)==false) {
        result = false;
    }
    if (result == false) {
        outDiv = document.getElementById(error_div_id);
        outDiv.innerHTML = message;
    }
    return result;
}
