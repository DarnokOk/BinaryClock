package dzik.binaryclock.clock.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

public class MessageDialog extends DialogFragment {
    private final static String MESSAGE_KEY = "message";
    private final static String POSITIVE_KEY = "positive";
    private final static String NEGATIVE_KEY = "negative";
    private final static String LISTENER_KEY = "listener";

    public static MessageDialog newInstance(String message, String positive, String negative, DialogListener listener) {
        MessageDialog dialog = new MessageDialog();
        Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        args.putString(POSITIVE_KEY, positive);
        args.putString(NEGATIVE_KEY, negative);
        args.putParcelable(LISTENER_KEY, listener);
        dialog.setArguments(args);
        return dialog;
    }

    public interface DialogListener extends Parcelable {
        void onPositiveClick();
        void onNegativeClick();
        void onCancel();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MESSAGE_KEY);
        String positive = getArguments().getString(POSITIVE_KEY);
        String negative = getArguments().getString(NEGATIVE_KEY);
        final Parcelable parcelable = getArguments().getParcelable(LISTENER_KEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
            .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(parcelable instanceof DialogListener) {
                        ((DialogListener) parcelable).onPositiveClick();
                    }
                }
            }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(parcelable instanceof DialogListener) {
                        ((DialogListener) parcelable).onNegativeClick();
                    }
                }
            }).setOnKeyListener(new Dialog.OnKeyListener() {
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if(parcelable instanceof DialogListener) {
                            ((DialogListener) parcelable).onCancel();
                        }
                        dismiss();
                    }
                    return true;
                }
            });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
