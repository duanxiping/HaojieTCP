package recever;

import com.haojie.act.BaseActivity;

import util.NetWorkUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class NetWorkStatusReceiver extends BroadcastReceiver {

	public NetWorkStatusReceiver() {
		 
	  }
	 
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    String action = intent.getAction();
//	    if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//	      BaseActivity.isNetWorkConnected = NetWorkUtils.netType == 1;
//	    }
	  }
}
