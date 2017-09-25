package com.vowme.app.utilities.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.opportunity.OpportunityActivity;

public class MyGcmListenerService extends GcmListenerService {
    public void onMessageReceived(String from, Bundle data) {
        sendNotification(data.getString("alert"), data.getString("id"), data.getString("sound"));
    }

    private void sendNotification(String message, String oppId, String sound) {
        Intent intent = new Intent(this, OpportunityActivity.class);
        intent.addFlags(67108864);
        intent.putExtra("android.intent.extra.TEXT", oppId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(OpportunityActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(Integer.parseInt(oppId), Intent.FILL_IN_ACTION);
        Intent shortlistIntent = new Intent();
        shortlistIntent.setAction("SHORTLIST_ACTION");
        shortlistIntent.putExtra("android.intent.extra.TEXT", oppId);
        PendingIntent piShortlistIntent = PendingIntent.getBroadcast(this, Integer.parseInt(oppId), shortlistIntent, Intent.FILL_IN_ACTION);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(Integer.parseInt(oppId), new Builder(this).setSmallIcon(R.mipmap.ic_push_notification).setContentTitle(getResources().getString(R.string.app_name)).setStyle(new BigTextStyle().bigText("New opportunity for you: " + message)).setPriority(-1).setCategory("recommendation").addAction(R.drawable.ic_star_border_24dp, "Shortlist", piShortlistIntent).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(Integer.parseInt(sound))).setColor(ContextCompat.getColor(this, R.color.colorAccent)).setContentIntent(pendingIntent).build());
    }
}
