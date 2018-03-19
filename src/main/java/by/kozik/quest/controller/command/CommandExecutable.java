package by.kozik.quest.controller.command;

/**
 * Created by Roldo on 24.11.16.
 */
public interface CommandExecutable {
    public String execute();
    public String executeExceptionPage(String message);
    public String executeReport(String message);
    public String executeConfirm(String message,String returnPage, String proceedPage);
}
