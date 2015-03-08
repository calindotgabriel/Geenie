package ro.geenie.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.InjectView;
import ro.geenie.R;

/**
 * Created by loopiezlol on 04.03.2015.
 */
public class NewAssignmentDialog extends DialogFragment {

    assignmentActivityListener mCallback;

    MaterialEditText editAssignmentName;
    String assignmentName;
    private final MaterialDialog.ButtonCallback mButtonCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog materialDialog) {
            assignmentName = editAssignmentName.getText().toString();
            if (!assignmentName.isEmpty()) {
                mCallback.createAssignment(assignmentName);
                materialDialog.dismiss();
            } else {
                Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNegative(MaterialDialog materialDialog) {

            materialDialog.dismiss();
        }
    };
    @InjectView(R.id.datebutton)
    Button datePicker;
    @InjectView(R.id.hourbutton)
    Button hourPicker;

    public NewAssignmentDialog() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (assignmentActivityListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Create new event")
                .customView(R.layout.dialog_new_assignment, true)
                .positiveText("Ok")
                .negativeText("Cancel")
                .autoDismiss(false)
                .callback(mButtonCallback)
                .build();

        editAssignmentName = (MaterialEditText) dialog.getCustomView().findViewById(R.id.assignment_title);

        return dialog;
    }


    public void show(Activity context) {
        show(context.getFragmentManager(), "Assignment");
    }


    public interface assignmentActivityListener {

        public void createAssignment(String assignmentTitle);

    }


}
