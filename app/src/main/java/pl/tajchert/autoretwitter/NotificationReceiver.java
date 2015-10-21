package pl.tajchert.autoretwitter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by Tajchert on 21.10.2015.
 */
public class NotificationReceiver extends NotificationListenerService {
    SharedPreferences sharedPreferences;
    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        if(!statusBarNotification.isOngoing()) {
            //As we want to ignore ongoing notifications
            sharedPreferences = getSharedPreferences(ActivityMain.KEY_SHARED_PREFS, MODE_PRIVATE);
            extractActions(statusBarNotification);
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

    private void extractActions(StatusBarNotification statusBarNotification) {
        if("com.twitter.android".equals(statusBarNotification.getPackageName())) {
            if(statusBarNotification.getNotification().actions != null &&  statusBarNotification.getNotification().actions.length > 0) {
                Notification.Action[] actions = statusBarNotification.getNotification().actions;
                for(Notification.Action action : actions) {
                    performActions(action);
                }
            }
        }
    }

    private void performActions(Notification.Action action) {
        if(sharedPreferences.getBoolean(ActivityMain.KEY_FAVORITE, false)) {
            if ("Favorite".equals(action.title)) {
                PendingIntent actionIntent = action.actionIntent;
                sendBackAction(actionIntent);
            }
        }
        //Retweet needs user action (Quote or Retweet)
    }

    private void sendBackAction(PendingIntent pendingIntent) {
        try {
            pendingIntent.send(NotificationReceiver.this, 0, new Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
