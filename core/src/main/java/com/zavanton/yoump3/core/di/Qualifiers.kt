package com.zavanton.yoump3.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoThreadScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainThreadScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HighNotificationChannel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NormalNotificationChannel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LowNotificationChannel