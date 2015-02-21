package ro.geenie.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import ro.geenie.R;

/**
 * Created by loopiezlol on 21.02.2015.
 */
public class NewEventDialog extends DialogFragment {

    private final MaterialDialog.ButtonCallback mButtonCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog materialDialog) {
            materialDialog.dismiss();
        }

        @Override
        public void onNegative(MaterialDialog materialDialog) {
            materialDialog.dismiss();
        }
    };
    public MaterialEditText eventName;

    public NewEventDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Create new event")
                .customView(R.layout.schedule_new_dialog, true)
                .positiveText("Ok")
                .autoDismiss(false)
                .callback(mButtonCallback)
                .build();

        return dialog;
    }

    public void show(Activity context) {
        show(context.getFragmentManager(), "NEW EVENT");
    }


}
