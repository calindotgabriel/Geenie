package ro.geenie.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appyvet.rangebar.RangeBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import ro.geenie.R;

public class NewEventDialog extends DialogFragment implements View.OnClickListener {

    scheduleActivityListener mCallback;
    MaterialEditText editEventName;
    String eventName;
    RangeBar rangeBar;
    int startHour = 0;
    int endHour = 24;
    GridLayout list;
    Integer colorIndex;
    int dayOfWeek;

    private final MaterialDialog.ButtonCallback mButtonCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog materialDialog) {

            if (getArguments() != null)
                mCallback.deleteEvent(getArguments().getString("eventName"));

            eventName = editEventName.getText().toString();
            if (!eventName.isEmpty()) {
                if (colorIndex != null) {
                    mCallback.createEvent(eventName, startHour, endHour, colorIndex, dayOfWeek);
                    materialDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "You should give your event a color!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "You should give your event a name!", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onNegative(MaterialDialog materialDialog) {
            if (getArguments() != null) {

                //Toast.makeText(getActivity(), getArguments().getInt("position"), Toast.LENGTH_SHORT).show();

                mCallback.deleteEvent(getArguments().getString("eventName"));
                materialDialog.dismiss();
            } else {
                materialDialog.dismiss();
            }

        }
    };
    Spinner spinner;
    private int[] mColors;


    public NewEventDialog() {

    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            colorIndex = (Integer) v.getTag();
            for (int i = 0; i < list.getChildCount(); i++) {
                FrameLayout child = (FrameLayout) list.getChildAt(i);
                child.getChildAt(0).setVisibility(View.GONE);
            }
            ((ViewGroup) v).getChildAt(0).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (scheduleActivityListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Create new event")
                .customView(R.layout.dialog_new_schedule, true)
                .positiveText("Ok")
                .negativeText("Delete")
                .autoDismiss(false)
                .callback(mButtonCallback)
                .build();

        //Event Name
        editEventName = (MaterialEditText) dialog.getCustomView().findViewById(R.id.event_name);

        //RangeBar for start and end hours

        rangeBar = (RangeBar) dialog.getCustomView().findViewById(R.id.rangebar);
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int start, int end, String textStart, String textEnd) {
                startHour = start;
                endHour = end;
            }
        });

        //colors

        final TypedArray ta = getActivity().getResources().obtainTypedArray(R.array.colors);
        mColors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++)
            mColors[i] = ta.getColor(i, 0);
        ta.recycle();
        list = (GridLayout) dialog.getCustomView().findViewById(R.id.grid);
        for (int i = 0; i < list.getChildCount(); i++) {
            FrameLayout child = (FrameLayout) list.getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(this);
            child.getChildAt(0).setVisibility(View.GONE);
            Drawable selector = createSelector(mColors[i]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int[][] states = new int[][]{
                        new int[]{-android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_pressed}
                };
                int[] colors = new int[]{
                        shiftColor(mColors[i]),
                        mColors[i]
                };
                ColorStateList rippleColors = new ColorStateList(states, colors);
                setBackgroundCompat(child, new RippleDrawable(rippleColors, selector, null));
            } else {
                setBackgroundCompat(child, selector);
            }
        }

        //dayofweek

        spinner = (Spinner) dialog.getCustomView().findViewById(R.id.days_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weekdays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayOfWeek = position + 1;
                if (getArguments() != null) {
                    rangeBar.setRangePinsByIndices(getArguments().getInt("startHour"), getArguments().getInt("endHour"));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayOfWeek = 1;

            }
        });

        spinner.setSelection(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);


        //Populate every element if editable

        if (getArguments() != null) {
            Bundle bundle = getArguments();

            //dialog title
            dialog.setTitle("Edit your event");

            //name
            editEventName.setText(bundle.getString("eventName"));

            //color
            FrameLayout child = (FrameLayout) list.getChildAt(bundle.getInt("color"));
            child.performClick();

            //rangebarb
            rangeBar.setRangePinsByIndices(bundle.getInt("startHour"), bundle.getInt("endHour"));

            //spinner
            spinner.setSelection(bundle.getInt("dayOfWeek") - 1);

        }



        return dialog;
    }

    private Drawable createSelector(int color) {
        ShapeDrawable coloredCircle = new ShapeDrawable(new OvalShape());
        coloredCircle.getPaint().setColor(color);
        ShapeDrawable darkerCircle = new ShapeDrawable(new OvalShape());
        darkerCircle.getPaint().setColor(shiftColor(color));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, coloredCircle);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, darkerCircle);
        return stateListDrawable;
    }

    private int shiftColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.9f; // value component
        return Color.HSVToColor(hsv);
    }

    private void setBackgroundCompat(View view, Drawable d) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(d);
        else view.setBackgroundDrawable(d);
    }

    public void show(Activity context) {
        show(context.getFragmentManager(), "NEW EVENT");
    }

    public interface scheduleActivityListener {

        public void createEvent(String eventName, int startHour, int endHour, int color, int dayOfWeek);

        public void deleteEvent(String eventName);
    }


}
