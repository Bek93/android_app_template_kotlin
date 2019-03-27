package net.wepla.campus_planet.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.android.support.AndroidSupportInjection;
import net.wepla.campus_planet.R;
import net.wepla.campus_planet.base.BaseDialog;
import net.wepla.campus_planet.databinding.MessageDialogFragmentBinding;

import java.util.Objects;

/**
 * Created by bek on 01/08/2017.
 */

public class MessageDialogFragment extends BaseDialog {

    private MessageDialogClickListener messageDialogClickListener;
    private String message1;
    private boolean loginButton = false;
    private MessageDialogFragmentBinding binding;

    public boolean isLoginButton() {
        return loginButton;
    }

    public void setLoginButton(boolean loginButton) {
        this.loginButton = loginButton;
    }

    public void setMessageDialogClickListener(MessageDialogClickListener messageDialogClickListener) {
        this.messageDialogClickListener = messageDialogClickListener;
    }

    private String message;

    public static MessageDialogFragment get(int confirm, String message) {
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("confirm", confirm);
        bundle.putString("message", message);
        messageDialogFragment.setArguments(bundle);
        return messageDialogFragment;
    }

    public static MessageDialogFragment get(String message) {
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        messageDialogFragment.setArguments(bundle);
        return messageDialogFragment;
    }

    public static MessageDialogFragment getConfirm(String message) {
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putInt("confirm", 1);
        messageDialogFragment.setArguments(bundle);
        return messageDialogFragment;
    }

    public static MessageDialogFragment getConfirm(String message, String message1) {
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("message1", message1);
        bundle.putInt("confirm", 1);
        messageDialogFragment.setArguments(bundle);
        return messageDialogFragment;
    }

    private int confirm = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confirm = getArguments().getInt("confirm", 0);
        message = getArguments().getString("message");
        message1 = getArguments().getString("message1");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MessageDialogFragmentBinding.inflate(inflater, container, false);
        AndroidSupportInjection.inject(this);
        Objects.requireNonNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.messageText.setText(Function.dataCheck(message));
        if (message1 != null) {
            binding.secondaryMessageText.setText(Function.dataCheck(message1));
        } else {
            binding.secondaryMessageText.setVisibility(View.GONE);
        }

        if (loginButton) {
            binding.confirm.setText("로그인하기");
        }
        if (confirm == 0) {
            binding.cancel.setVisibility(View.GONE);
        }

        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (messageDialogClickListener != null) {
                    messageDialogClickListener.confirmClick();

                }
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (messageDialogClickListener != null) {
                    messageDialogClickListener.cancelClick();

                }
            }
        });
    }



}
