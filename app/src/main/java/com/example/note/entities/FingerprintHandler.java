package com.example.note.entities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.note.R;
import com.example.note.activities.MainActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private static final int ERROR = 1;
    private static final int HELP = 2;
    private static final int FAILED = 3;
    private static final int SUCCESS = 4;

    public FingerprintHandler(Context mContext){
        context = mContext;
    }
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update(ERROR, false);
        Log.d("FingerprintLog",  errMsgId+": "+errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update(HELP, false);
        Log.d("FingerprintLog", helpMsgId+": "+helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update(FAILED, false);
        Log.d("FingerprintLog", "");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update(SUCCESS, true);
        Log.d("FingerprintLog", String.valueOf(result));
    }

    public void update(final int i, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setTextColor(ContextCompat.getColor(context,R.color.colorDelete));
        textView.setVisibility(View.VISIBLE);
        if(i == SUCCESS){
            textView.setText("Mở khóa thành công.");
        } else if (i == ERROR) {
            textView.setText("Xảy ra lỗi");
        } else if (i == FAILED) {
            textView.setText("Mở khóa không thành công");
        } else if (i == HELP) {
            textView.setText("Vui lòng quét vân tay");
        }
    }
}
