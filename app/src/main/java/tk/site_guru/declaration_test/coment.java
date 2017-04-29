package tk.site_guru.declaration_test;

/**
 * Created by Alex on 20.03.2017.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.EditText;


public class coment extends DialogFragment implements View.OnClickListener {
    EditText editText;

    public coment() {
        super();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



            getDialog().setTitle(R.string.coment);
            View v = inflater.inflate(R.layout.coment, null);
            v.findViewById(R.id.com_button).setOnClickListener(this);
        editText = (EditText) v.findViewById(R.id.edit);



            return v;

    }



    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Log.d("Alex", "Dialog : onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("Alex", "Dialog : onCancel");
    }

    @Override
    public void onClick(View v) {
        String comment = editText.getText().toString();
        Bundle bundle = this.getArguments();
        bundle.putString("comment",comment);
        Add_select add = new Add_select(getContext());
        add.execute(bundle);
        this.dismiss();


    }

    public interface NoticeDialogListener {
        public  void onDialogPositiveClick(DialogFragment dialog, String comentar);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


}
