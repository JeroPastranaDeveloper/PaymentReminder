package com.pr.paymentreminder.notifications

/*
class NotificationWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = notificationManager.getNotificationChannel(Constants.NOTIFICATION_CHANNEL_ID)
        if (channel == null) {
            val notificationChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val serviceName = inputData.getString(Constants.SERVICE_NAME)
        val serviceRemember = inputData.getString(Constants.SERVICE_REMEMBER)
        val servicePrice = inputData.getString(Constants.SERVICE_PRICE)

        val activityIntent = Intent(applicationContext, LoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, activityIntent, PendingIntent.FLAG_MUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(if (Integer.parseInt(serviceRemember.orEmpty()) > 1)
                applicationContext.getString(R.string.notification_plural, serviceRemember, serviceName)
            else
                applicationContext.getString(R.string.notification_singular, serviceRemember, serviceName)
            )
            .setContentText("$servicePrice€")
            .setSmallIcon(R.drawable.logo_no_bg)
            .setContentIntent(pendingIntent)
            .build()

        val notificationId = inputData.getInt(Constants.NOTIFICATION_ID, 0)

        notificationManager.run {
            notify(notificationId, notification)
        }

        return Result.success()
    }
}
*/
