package com.zavanton.yoump3.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceScope