package ie.jgriffin.androidutils.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by JGriffin on 20/07/2014.
 */
public class TimePickerYesNoFragment extends DialogFragment {

    private MyTimePickerDialog.OnTimeSetListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mListener = (MyTimePickerDialog.OnTimeSetListener) getTargetFragment();
    }

    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }

    public static TimePickerYesNoFragment newInstance(Calendar cal){
        TimePickerYesNoFragment frag = new TimePickerYesNoFragment();
        Bundle args = new Bundle();
        args.putInt(Integer.toString(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY));
        args.putInt(Integer.toString(Calendar.MINUTE), cal.get(Calendar.MINUTE));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = getArguments();
        int hour = b.getInt(Integer.toString(Calendar.HOUR_OF_DAY));
        int min = b.getInt(Integer.toString(Calendar.MINUTE));
        return new MyTimePickerDialog(getActivity(), mListener, hour, min, true);
    }
}
