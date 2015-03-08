package ro.geenie.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import ro.geenie.R;

/**
 * Created by loopiezlol on 04.03.2015.
 */
public class NewAssignmentDialog extends DialogFragment {

    assignmentActivityListener mCallback;
    MaterialEditText editAssignmentName;
    String assignmentName;
    Calendar assignmentCalendar = Calendar.getInstance();

    private final MaterialDialog.ButtonCallback mButtonCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog materialDialog) {
            assignmentName = editAssignmentName.getText().toString();
            if (!assignmentName.isEmpty()) {
                mCallback.createAssignment(assignmentName, assignmentCalendar);
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

    Button datePicker;
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

        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        final int year = mcurrentTime.get(Calendar.YEAR);
        final int month = mcurrentTime.get(Calendar.MONTH);
        final int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);


        datePicker = (Button) dialog.getCustomView().findViewById(R.id.datebutton);
        hourPicker = (Button) dialog.getCustomView().findViewById(R.id.hourbutton);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        assignmentCalendar.set(Calendar.YEAR, year);
                        assignmentCalendar.set(Calendar.MONTH, monthOfYear);
                        assignmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    }
                }, year, month, day);
                mDatePicker.setTitle("Choose a date:");
                mDatePicker.show();
            }

        });
        hourPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutee) {
                        assignmentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        assignmentCalendar.set(Calendar.MINUTE, minutee);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time:");
                mTimePicker.show();
            }
        });





        return dialog;
    }


    public void show(Activity context) {

        show(context.getFragmentManager(), "Assignment");
    }


    public interface assignmentActivityListener {

        public void createAssignment(String assignmentTitle, Calendar calendar);

    }


}
