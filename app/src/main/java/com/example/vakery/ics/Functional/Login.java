package com.example.vakery.ics.Functional;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vakery.ics.R;

public class Login extends DialogFragment  {
    private View form = null;


    /***
     * Содание диалога для регистрации
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form= getActivity().getLayoutInflater()
                .inflate(R.layout.login, null);

        final EditText enterName=(EditText)form.findViewById(R.id.enterName);
        final EditText enterSurname=(EditText)form.findViewById(R.id.enterSurname);
        final EditText enterGroup=(EditText)form.findViewById(R.id.enterGroup);

        Button positiveButton=(Button)form.findViewById(R.id.pozitivButton);
        Button negativeButton=(Button)form.findViewById(R.id.negativeButton);

        //при нажатии ОК
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //проверка на заполненность полей
                if( ((enterName.getText().length()) == 0) || ((enterSurname.getText().length()) == 0)
                        || ((enterGroup.getText().length()) == 0) ){
                    Toast.makeText(getContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                }else {
                    LocalSettingsFile.setUserInfo(enterName.getText().toString(), enterSurname.getText().toString(), enterGroup.getText().toString());
                    new DownloadInfo().updateInfo();
                    //закрываем диалог
                    getDialog().dismiss();
                }
            }
        });

        //при нажатии ОТМЕНА
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //закрываем диалог
                getDialog().dismiss();
            }
        });

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return(builder.setTitle("Авторизация").setView(form)
                .create());
    }


    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }


    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }
}