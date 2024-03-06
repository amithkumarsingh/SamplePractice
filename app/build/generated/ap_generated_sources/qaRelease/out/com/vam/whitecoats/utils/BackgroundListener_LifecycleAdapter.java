package com.vam.whitecoats.utils;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;
import java.lang.Override;

public class BackgroundListener_LifecycleAdapter implements GeneratedAdapter {
  final BackgroundListener mReceiver;

  BackgroundListener_LifecycleAdapter(BackgroundListener receiver) {
    this.mReceiver = receiver;
  }

  @Override
  public void callMethods(LifecycleOwner owner, Lifecycle.Event event, boolean onAny,
      MethodCallsLogger logger) {
    boolean hasLogger = logger != null;
    if (onAny) {
      return;
    }
    if (event == Lifecycle.Event.ON_PAUSE) {
      if (!hasLogger || logger.approveCall("onBackground", 1)) {
        mReceiver.onBackground();
      }
      return;
    }
  }
}
