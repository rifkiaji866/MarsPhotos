/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// url dasar untuk memaggil mars
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

/**
 * membangun Moshi object yang akan digunakan Retrofit, pastiakn Kotlin adapter compitable
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * gunakan Retrofit builder untuk membangun retrofit object menggunakan Moshi converter dengan Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * interface publik yang mengexpose method [getPhotos]
 */
interface MarsApiService {
    /**
     * mengembalikan [List] dari [MarsPhoto] dan method yang di panggil dari Coroutine.
     * @GET "photos" akan direquested dengan method GET HTTP
     */
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

/**
 * public Api object yang mengexpose lazy-initialized Retrofit service
 */
object MarsApi {
    val retrofitService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}
