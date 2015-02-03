package lbt05.com.pingpong;

import android.app.Application;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;

/**
 * Created by lbt05 on 2/2/15.
 */
public class MyApplication extends Application {
  public void onCreate() {
    AVOSCloud.initialize(this, "ltk9pvd1cp0xb8h4bjszs7fkd70klvx92v7kfllpc54nrr91",
        "b50clrr4dx19juewq0z4dtvenlfgh539fw6tyoglrl1knj08");

      AVInstallation.getCurrentInstallation().saveInBackground();
      PushService.setDefaultPushCallback(this,LoginActivity.class);
  }
}
