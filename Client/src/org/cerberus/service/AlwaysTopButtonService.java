package org.cerberus.service;

import org.cerberus.event.collection.MotionCollector;
import org.cerberus.index.CerberusAPI;
import org.cerberus.scenario.NetworkMotionStream;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class AlwaysTopButtonService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		
//		if(STATUS == STATUS_START) {
//			System.out.println("STATUE == STATUS_START");
//			return;
//		}
		System.out.println("------------------------");
		Button widgetBtn = new Button(this);
		widgetBtn.setHeight(50);
//		widgetBtn.setId(index);
		widgetBtn.setWidth(50);
		widgetBtn.setTag("CerberusWidgetBtn");
		widgetBtn.setClickable(true);
		widgetBtn.setFocusable(true);
		widgetBtn.setText("Record...");
		
//		widgetBtn.setOnClickListener(new OnClickListener() {
//			private final int STATUS_RUNNING = 1;
//			private final int STATUS_FINISH = 0;
//			
//			private int status = STATUS_FINISH;
//			
//			@Override
//			public void onClick(View arg0) {
//
//				System.out.println("status = " + status);
//				
//				if(status == STATUS_RUNNING) {
//					status = STATUS_FINISH;
//					
//					//send Network
//					((NetworkMotionStream)MotionCollector.getInstance().getStream()).sendNetworkData();
//					System.out.println("--"   );
//				} else {
//					status = STATUS_RUNNING;
////					Toast.makeText(c, "Start scenario recording...", Toast.LENGTH_LONG).show();
//				}
//				
//			}
//		});
		
		widgetBtn.setOnTouchListener(new android.view.View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                if(motionEvent.getAction() == 1)
                    if(CerberusAPI.status_.intValue() == 1)
                    {
                        CerberusAPI.status_ = Integer.valueOf(0);
                        ((NetworkMotionStream)MotionCollector.getInstance().getStream()).sendNetworkData();
                        System.out.println("--");
                    } else
                    {
                        CerberusAPI.status_ = Integer.valueOf(1);
                        ((NetworkMotionStream)MotionCollector.getInstance().getStream()).getScenarioId(CerberusAPI.apiKey);
                        Toast toast = Toast.makeText(getApplicationContext(), "Start scenario recording...", 1);
                        toast.setGravity(17, 0, 0);
                        toast.show();
                    }
                return false;
            }
        });
		
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				 WindowManager.LayoutParams.WRAP_CONTENT,
		            WindowManager.LayoutParams.WRAP_CONTENT,
		            WindowManager.LayoutParams.TYPE_PHONE,//�׻� �� ����. ��ġ �̺�Ʈ ���� �� ����.
		            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  //��Ŀ���� ������ ����
		            PixelFormat.TRANSLUCENT
				);
		
        params.gravity = Gravity.LEFT | Gravity.BOTTOM; 
		
//		WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,   //�׻� �� ������ �ְ�
//        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,       //��ġ �ν�
//        PixelFormat.TRANSLUCENT
		
		params.gravity = Gravity.LEFT | Gravity.BOTTOM;
		
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		wm.addView(widgetBtn, params);
		
	}

	
	
}
