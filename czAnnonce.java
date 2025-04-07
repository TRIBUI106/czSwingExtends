/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtendsLib;

import javax.swing.JOptionPane;
import raven.toast.Notifications;
import javax.swing.*;

// Dùng cho Java Swing và Toast Notifications của Raven

/**
 *
 * @author Chezis P
 */
public class czAnnonce {

    public static String showInputPassword(String s) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('\u2022'); // Đặt ký tự hiển thị khi nhập

        int option = JOptionPane.showConfirmDialog(
                null,
                passwordField,
                s,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            char[] password = passwordField.getPassword();
            return new String(password);
        } else {
            return "";
        }
    }

    public static String showInput(String s) {
        return JOptionPane.showInputDialog(s);
    }

    public static void showErr(String s) {
        JOptionPane.showMessageDialog(null, s, "Error Message", JOptionPane.ERROR_MESSAGE);
        return;
    }

    public static void showWarning(String s) {
        JOptionPane.showMessageDialog(null, s, "Warning Message", JOptionPane.WARNING_MESSAGE);
        return;
    }

    public static void showInfo(String s) {
        JOptionPane.showMessageDialog(null, s, "Infomation Message", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    public static int showConfirm(String s) {
        return JOptionPane.showConfirmDialog(null, s, "Asking Client", JOptionPane.YES_NO_OPTION);
    }

    public static void toastInfoTime(String s, long l) {
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_RIGHT, l, s);
    }

    public static void toastInfo(String s) {
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_RIGHT, s);
    }

    public static void toastSuccess(String s) {
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, s);
    }

    public static void toastWarning(String s) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, s);
    }

    public static void toastError(String s) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, s);
    }

}
