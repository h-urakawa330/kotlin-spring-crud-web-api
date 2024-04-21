package com.example.kotlinspringcrudwebapi

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

/**
 * Customer test
 *
 * 「@SpringBootTest」: SpringBootの機能を用いる処理のテストを行う場合に付与するアノテーション
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KotlinSpringCrudWebApiApplicationTests(@Autowired val mockMvc: MockMvc) {
	@Test
	@DisplayName("get /customers のテスト")
	fun read() {
		// [事前条件]

		// [テスト実施]
		// リクエスト
		val request = MockMvcRequestBuilders
			.get("/customers")
			.contentType(MediaType.APPLICATION_JSON)

		// リクエスト実行
		val response = mockMvc.perform(request).andReturn().response

		// [事後条件]
		// 1) 処理正常コードが返される
		// 2) 戻り値と期待値が一致する
		val expectedResponseBody =
			"""
               {
                 "customers": [
                   {
                     "id": 1,
                     "firstName": "Alice",
                     "lastName": "Sample1"
                   },
                   {
                     "id": 2,
                     "firstName": "Bob",
                     "lastName": "Sample2"
                   }
                 ]
               }
            """.trimIndent()
		assertThat(response.status).isEqualTo(HttpStatus.OK.value())
		JSONAssert.assertEquals(
			expectedResponseBody,
			response.contentAsString,
			JSONCompareMode.NON_EXTENSIBLE
		)
	}
}
