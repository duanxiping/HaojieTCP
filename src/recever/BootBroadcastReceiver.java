package recever;

import java.util.Timer;
import java.util.TimerTask;
import com.haojie.act.MainAct;
import com.tcpip.model.TCPService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootBroadcastReceiver extends BroadcastReceiver{

	 static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
	            Intent mainActivityIntent = new Intent(context, MainAct.class);  // 要启动的Activity
	            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(mainActivityIntent);
	            Toast.makeText(context, "丰泽预警系统已启动！", Toast.LENGTH_LONG).show();
	            
//	            startService(context);
			}
	    }
	    
	    public static void startService(final Context context){
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					Intent intent_service = new Intent(context,TCPService.class);
					context.startService(intent_service);
				}
			};
			timer.schedule(task, 5000);
		}
}
