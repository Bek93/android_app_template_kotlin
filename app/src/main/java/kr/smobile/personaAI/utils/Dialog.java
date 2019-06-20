package kr.smobile.personaAI.utils;

import android.content.Context;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;


/**
 * Created by Bek on 14/12/2016.
 */

public class Dialog {
    public static MessageDialogFragment showMessage(FragmentManager fragmentManager, final String message) {

        MessageDialogFragment messageDialogFragment = MessageDialogFragment.get(message);
        messageDialogFragment.show(fragmentManager, "ViewAccountsDialog");
        return messageDialogFragment;
    }

    public static MessageDialogFragment showMessageConfirm(FragmentManager fragmentManager, final String message) {

        MessageDialogFragment messageDialogFragment = MessageDialogFragment.getConfirm(message);
        messageDialogFragment.show(fragmentManager, "ViewAccountsDialog");
        return messageDialogFragment;
    }

    public static MessageDialogFragment showMessageConfirm(FragmentManager fragmentManager, final String message, final String message2) {

        MessageDialogFragment messageDialogFragment = MessageDialogFragment.getConfirm(message, message2);
        messageDialogFragment.show(fragmentManager, "ViewAccountsDialog");
        return messageDialogFragment;
    }



    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static MessageDialogFragment showLoginRequiredDialogMessage(FragmentManager supportFragmentManager, String message) {
        MessageDialogFragment messageDialogFragment = MessageDialogFragment.get(message);
        messageDialogFragment.setLoginButton(true);
        messageDialogFragment.show(supportFragmentManager, "LoginViewAccountsDialog");
        return messageDialogFragment;
    }

}
