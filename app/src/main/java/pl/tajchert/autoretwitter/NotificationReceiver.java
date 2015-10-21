package pl.tajchert.autoretwitter;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;

import java.util.List;

/**
 * Created by Tajchert on 21.10.2015.
 */
public class NotificationReceiver extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        if(!statusBarNotification.isOngoing()) {
            //As we want to ignore ongoing notifications
            extractWearNotification(statusBarNotification);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        //TODO remove notification from stack in Activity
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
    }

    @Override
    public void onListenerHintsChanged(int hints) {
        super.onListenerHintsChanged(hints);
    }

    private void extractWearNotification(StatusBarNotification statusBarNotification) {
        if("".equals(statusBarNotification.getPackageName())) {
            NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(statusBarNotification.getNotification());
            List<NotificationCompat.Action> actions = wearableExtender.getActions();
            for (NotificationCompat.Action act : actions) {

            }
            //notificationWear.pendingIntent = statusBarNotification.getNotification().contentIntent;
        }
    }
}
