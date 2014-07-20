package ie.jgriffin.androidutils.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by JGriffin on 20/07/2014.
 */
public class DatePickerYesNoFragment extends DialogFragment {

    private MyDatePickerDialog.OnDateSetListener mListener;

    public DatePickerYesNoFragment() {
    }

    public static DatePickerYesNoFragment newInstance(Calendar cal){

        DatePickerYesNoFragment frag = new DatePickerYesNoFragment();
        Bundle args = new Bundle();
        args.putInt(Integer.toString(Calendar.YEAR), cal.get(Calendar.YEAR));
        args.putInt(Integer.toString(Calendar.MONTH), cal.get(Calendar.MONTH));
        args.putInt(Integer.toString(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mListener = (MyDatePickerDialog.OnDateSetListener) getTargetFragment();
    }

    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = getArguments();
        int y = b.getInt(Integer.toString(Calendar.YEAR));
        int m = b.getInt(Integer.toString(Calendar.MONTH));
        int d = b.getInt(Integer.toString(Calendar.DAY_OF_MONTH));
        return new MyDatePickerDialog(getActivity(), mListener, y, m, d);
    }

    @Override
    public void onPause() {
        super.onPause();
        //getFragmentManager().putFragment();
    }
}