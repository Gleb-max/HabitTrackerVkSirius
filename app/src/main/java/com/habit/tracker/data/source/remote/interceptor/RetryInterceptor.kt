package com.habit.tracker.data.source.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RetryInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var retryCount = 0

        //todo: добавить проверку на статус и возможно не стоит ретраить 400 ошибки

        while (!response.isSuccessful && retryCount < RETRY_COUNT) {
            response.close()
            response = chain.proceed(request)
            retryCount++
        }
        return response
    }

    companion object {

        private const val RETRY_COUNT = 2
    }
}