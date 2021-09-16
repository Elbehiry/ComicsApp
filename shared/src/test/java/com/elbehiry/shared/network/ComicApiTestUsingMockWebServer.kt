/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.elbehiry.shared.network

import com.elbehiry.shared.data.remote.ComicsApi
import com.elbehiry.test_shared.MainCoroutineRule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.Assert
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ComicApiTestUsingMockWebServer {

    @get:Rule
    val mockWebServer = MockWebServer()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val moshi: Moshi by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private lateinit var api: ComicsApi

    @Before
    fun setUp() {
        api = retrofit.create(ComicsApi::class.java)
    }

    @Test
    fun `test get comics should be successful`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(fakeResponse)
                .setResponseCode(200)
        )

        val comicsResult = api.getComic()
        mockWebServer.takeRequest()

        Assert.assertNotNull(comicsResult)
    }

    @After
    fun tearDown() {
        try {
            mockWebServer.shutdown()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}

private const val fakeResponse = "{\n" +
    "    \"month\": \"9\",\n" +
    "    \"num\": 2516,\n" +
    "    \"link\": \"\",\n" +
    "    \"year\": \"2021\",\n" +
    "    \"news\": \"\",\n" +
    "    \"safe_title\": \"Hubble Tension\",\n" +
    "    \"transcript\": \"\",\n" +
    "    \"alt\": \"Oh, wait, I might've had " +
    "it set to kph instead of mph. But that would make the discrepancy even wider!\",\n" +
    "    \"img\": \"https://imgs.xkcd.com/comics/hubble_tension.png\",\n" +
    "    \"title\": \"Hubble Tension\",\n" +
    "    \"day\": \"15\"\n" +
    "}"
