package com.mastercard.ats.i2b.rx

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.ObservableOnSubscribe

object RxBroadCastReceiver {

    fun create(context: Context, intentFilter: IntentFilter): Observable<Intent> {

        lateinit var receiver: BroadcastReceiver

        return create(ObservableOnSubscribe<Intent> { emitter ->
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    intent?.apply {
                        emitter.onNext(this)
                        emitter.onComplete()
                    }

                }
            }
            context.registerReceiver(receiver, intentFilter)
        }).doOnComplete {
            context.unregisterReceiver(receiver)
        }
    }

}